package com.example.currencyconvertor.data.network

import com.example.currencyconvertor.data.model.response.ConversionResponseModel
import com.example.currencyconvertor.data.model.response.ListCurrencyModel
import com.example.currencyconvertor.data.model.response.ListRatesModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface Network {

    @GET("currency_data/list")
    fun list() : Call<ListCurrencyModel>

    @GET("currency_data/live")
    fun live(@Query("source") source : String = "INR", @Query("currencies") currencies : String? = null) : Call<ListRatesModel>

    @GET("currency_data/convert")
    fun convert(@Query("to") to : String = "USD",@Query("from") from : String = "INR",@Query("amount") amount : Float = 0.0f) : Call<ConversionResponseModel>
}