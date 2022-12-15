package com.example.wirebarley.main

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import com.example.tordertask.utils.Util
import com.example.wirebarley.db.Currency
import com.example.wirebarley.retrofit.CurrencyDataService
import com.example.wirebarley.retrofit.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*

class MainPresenter(private val context: Context, private val view: MainFragment) : MainContract.Presenter
{
    init {
        view.setPresenter(this)
    }

    override fun start() {

    }

    override fun calculateExchangeRate(sendCountry: String, sendPayment: Int) {
        val client = RetrofitClient.getInstance()
        val service = client.create(CurrencyDataService::class.java)
        service.getCurrencyRate("USD",sendCountry).enqueue(object : Callback<Currency>{
            @SuppressLint("SimpleDateFormat")
            override fun onResponse(call: Call<Currency>, response: Response<Currency>) {
                Log.d(TAG, "onResponse: $response")
                val timeStamp = response.body()!!.timestamp
                val date = Util.timeStampToDate(timeStamp)
                // 조회 시간
                view.setInquiryDate(date)
                // 환율
                var exchangeRate = 0f
                when(sendCountry) {
                    "KRW" -> {
                       exchangeRate = response.body()!!.quotes.usdKrw
                    }
                    "JPY" ->{
                        exchangeRate = response.body()!!.quotes.usdJpy
                    }
                    "PHP" ->{
                        exchangeRate = response.body()!!.quotes.usdPhp
                    }
                }
                view.setExchangeRate(Util.addComma(exchangeRate), sendCountry)

                // 수취 금액
                val exchangedPayment = exchangeRate * sendPayment
                val receivePayment = Util.addComma(exchangedPayment)
                view.setMsgReceivePayment(receivePayment, sendCountry)
            }

            override fun onFailure(call: Call<Currency>, t: Throwable) {
                t.printStackTrace()
            }
        })
    }



    companion object {
        private const val TAG = "MainPresenter"
    }
}