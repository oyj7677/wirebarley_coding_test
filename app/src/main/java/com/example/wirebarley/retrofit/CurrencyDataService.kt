package com.example.wirebarley.retrofit

import com.example.wirebarley.db.Currency
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface CurrencyDataService {

    @GET("live")
    fun getCurrencyRate(@Query("source") source: String, @Query("currencies") currencies: String): Single<Currency>
}
