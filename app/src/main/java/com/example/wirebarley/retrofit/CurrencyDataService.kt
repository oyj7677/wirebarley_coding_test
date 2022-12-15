package com.example.wirebarley.retrofit

import com.example.wirebarley.db.Currency
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface CurrencyDataService {

    @GET("live")
    fun getCurrencyRate(@Query("source") source: String, @Query("currencies") currencies: String): Call<Currency>
}
