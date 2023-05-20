package com.example.currencyconvertor.domain.repo

import com.example.currencyconvertor.data.model.CurrencyNameValue
import com.example.currencyconvertor.data.network.Network
import com.example.currencyconvertor.domain.statesandevents.States
import kotlinx.coroutines.flow.Flow

interface RemoteRepo {


   suspend fun listCurrencies() : Flow<States<Map<String,String>>>

    suspend fun listRates() : Flow<States<Map<String,Double>>>

    suspend fun convert(to : String,from : String,amount : Float) : Flow<States<Float>>

}