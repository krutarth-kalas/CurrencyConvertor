package com.example.currencyconvertor.data.model.response


import com.google.gson.annotations.SerializedName

data class ConversionResponseModel(
    @SerializedName("info")
    val info: Info,
    @SerializedName("query")
    val query: Query,
    @SerializedName("result")
    val result: Double,
    @SerializedName("success")
    val success: Boolean
) {
    data class Info(
        @SerializedName("quote")
        val quote: Double,
        @SerializedName("timestamp")
        val timestamp: Int
    )

    data class Query(
        @SerializedName("amount")
        val amount: Int,
        @SerializedName("from")
        val from: String,
        @SerializedName("to")
        val to: String
    )
}