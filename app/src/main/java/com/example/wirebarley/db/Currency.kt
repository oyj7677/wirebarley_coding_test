package com.example.wirebarley.db

import com.google.gson.annotations.SerializedName

data class Currency(
    @SerializedName("source")
    val source: String,
    @SerializedName("success")
    val success: Boolean,
    @SerializedName("timestamp")
    val timestamp: Long,
    @SerializedName("quotes")
    val quotes: Quotes
)

data class Quotes(
    @SerializedName("USDJPY")
    val usdJpy : Float,

    @SerializedName("USDKRW")
    val usdKrw : Float,

    @SerializedName("USDPHP")
    val usdPhp : Float
)

