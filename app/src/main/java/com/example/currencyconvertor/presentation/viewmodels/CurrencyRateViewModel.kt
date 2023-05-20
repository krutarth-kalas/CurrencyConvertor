package com.example.currencyconvertor.presentation.viewmodels

import android.app.Application
import android.content.Context
import android.view.View
import androidx.lifecycle.*
import com.example.currencyconvertor.data.model.CurrencyNameValue
import com.example.currencyconvertor.domain.statesandevents.Events
import com.example.currencyconvertor.domain.statesandevents.States
import com.example.currencyconvertor.domain.usecase.MainUseCase
import com.example.currencyconvertor.presentation.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class CurrencyRateViewModel @Inject constructor(private val app : Application,
    private val mainUseCase: MainUseCase) : BaseViewModel(app),DefaultLifecycleObserver {
    val adapterList = mutableListOf<CurrencyNameValue>()
    var notifyCallback = {}
    var symbolsCallback = {}
    val symbols = mutableListOf<String>()
    private val _amountPb = MutableStateFlow(View.GONE)
    val amountPb = _amountPb.asStateFlow()
    private val  _conversionText = MutableStateFlow("")
    val conversionText = _conversionText.asStateFlow()
    var errorCallback : (error : String) -> Unit = {}
    private val _loaderState = MutableStateFlow(View.VISIBLE)
    private val _errorVisible = MutableStateFlow(View.GONE)
    val errorVisible = _errorVisible.asStateFlow()
    val loaderState = _loaderState.asStateFlow()
    private val _rvVisible = MutableStateFlow(View.VISIBLE)
    val rvVisible = _rvVisible.asStateFlow()
    private val _fromCurrency = MutableStateFlow("USD")
    private val _toCurrency = MutableStateFlow("INR")
    val fromCurrency = _fromCurrency.asStateFlow()
    val toCurrency = _toCurrency.asStateFlow()
    val amount = MutableStateFlow(0.0f)

    var job : Job? = null

    private val items : MutableStateFlow<List<CurrencyNameValue>> = MutableStateFlow(
        mutableListOf()
    )

    private fun getNewJob() : Job{
        return viewModelScope.launch(Dispatchers.IO) {
            delay(1000)
            _amountPb.value = View.VISIBLE
            mainUseCase.convert(toCurrency.value,fromCurrency.value,amount.value).collect{
                when(it){
                    is States.API_LOADING -> TODO()
                    is States.FAILURE -> {
                        _conversionText.value =  0f.toString()
                        _amountPb.value = View.GONE
                    }
                    is States.SUCCESS -> {
                        _conversionText.value = (it.payload ?: 0f).toString()
                        _amountPb.value = View.GONE
                    }
                }
            }
        }
    }

    fun events(event : Events){
        when(event){
            is Events.CONVERT -> {
               if(job == null)
               {
                   job = getNewJob()
               }
                else {
                    if(job?.isActive == true)
                    {
                        job?.cancel()
                        job = getNewJob()
                    }
                   else{
                       job = getNewJob()
                    }
               }
                job?.start()
            }
            is Events.FROM_SELECTED -> {
                _fromCurrency.value = event.selected
                if(amount.value > 0.0f)
                {
                    events(Events.CONVERT)
                }
            }
            is Events.TO_SELECTED -> {
                _toCurrency.value = event.selected
                if(amount.value > 0.0f)
                {
                    events(Events.CONVERT)
                }
            }
            is Events.AMOUNT_ENTER -> {

                amount.value = if(event.amount.isNullOrEmpty()) 0.0f else event.amount.toFloat()
                events(Events.CONVERT)
            }
        }
    }
    private suspend fun listCurrencyRates(): Flow<States<List<CurrencyNameValue>>> {
           return flow{
               emit(States.API_LOADING(true))
               mainUseCase.listCurrencyRates().collect {
                   emit(it)
                   emit(States.API_LOADING(false))
               }
           }
        }

    fun startObserve(owner: LifecycleOwner)
    {
        _loaderState.value = View.VISIBLE
        _rvVisible.value = View.VISIBLE
        _errorVisible.value = View.GONE
        viewModelScope.launch(Dispatchers.IO) {
            items.flowWithLifecycle(owner.lifecycle).collect {
                if(it.size > 1) {
                    adapterList.clear()
                    adapterList.addAll(it.sortedBy { it.currencyName })
                    withContext(Dispatchers.Main) {
                        notifyCallback()
                        getCurrencySymbols()
                    }
                }
            }
        }
       viewModelScope.launch(Dispatchers.Default) {
            listCurrencyRates().collect{
                when (it){
                    is States.API_LOADING -> {
                        _loaderState.value = if(it.state) View.VISIBLE else View.GONE
                    }
                    is States.FAILURE -> {
                        _errorVisible.value = View.VISIBLE
                        _rvVisible.value = View.GONE
                    }
                    is States.SUCCESS -> {
                        _errorVisible.value = View.GONE
                        _rvVisible.value = View.VISIBLE
                        items.value = it.payload ?: listOf()
                    }
                }
            }
        }
    }

   private fun getCurrencySymbols(){
       val items =  adapterList.map {
            return@map it.currencySymbol
        }.sorted()
        symbols.clear()
        symbols.addAll(items)
        symbolsCallback()
    }

}