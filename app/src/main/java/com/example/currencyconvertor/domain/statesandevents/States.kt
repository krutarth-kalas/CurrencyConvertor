package com.example.currencyconvertor.domain.statesandevents

import com.example.currencyconvertor.data.model.response.ConversionResponseModel

sealed class States<T> {
    data class API_LOADING<T>(val state : Boolean) : States<T>()
    data class SUCCESS<T>(val payload: T?=null,val message : String? = null) : States<T>()
    data class FAILURE<T>(val payload:T? = null,val message: String? = null) : States<T>()
}