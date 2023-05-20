package com.example.currencyconvertor.data.model.response


import com.google.gson.annotations.SerializedName

data class ListRatesModel(
    @SerializedName("quotes")
    val quotes: HashMap<String,Double>,
    @SerializedName("source")
    val source: String,
    @SerializedName("success")
    val success: Boolean,
    @SerializedName("timestamp")
    val timestamp: Int
)