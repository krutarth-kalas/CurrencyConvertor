package com.example.currencyconvertor.data.model.response


import com.google.gson.annotations.SerializedName

data class ListCurrencyModel(
    @SerializedName("currencies")
    val currencies: HashMap<String,String>,
    @SerializedName("success")
    val success: Boolean
)