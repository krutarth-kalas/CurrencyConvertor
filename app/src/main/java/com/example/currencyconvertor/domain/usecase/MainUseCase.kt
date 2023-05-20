package com.example.currencyconvertor.domain.usecase

import com.example.currencyconvertor.data.model.CurrencyNameValue
import com.example.currencyconvertor.domain.repo.RemoteRepo
import com.example.currencyconvertor.domain.statesandevents.States
import kotlinx.coroutines.flow.*

class MainUseCase(private val repo: RemoteRepo) {


    suspend fun listCurrencyRates() : Flow<States<List<CurrencyNameValue>>> {
        val curr =  repo.listCurrencies()
        val rates = repo.listRates()

        return curr.zip(rates){t1,t2 ->

            when{
                t1 is States.SUCCESS && t2 is States.SUCCESS ->
                {
                    val temp = mutableListOf<CurrencyNameValue>()
                t1.payload?.forEach{
                    temp.add(CurrencyNameValue(it.value,it.key,t2.payload?.get("INR"+it.key) ?: 0.0))
                }
                    return@zip States.SUCCESS(payload = temp)
                }
                else ->{
                    return@zip States.FAILURE(message = "Failure")
                }
            }

        }

    }

    suspend fun convert(to : String,from : String,amount : Float): Flow<States<Float>> {
        return repo.convert(to,from,amount)
    }

}