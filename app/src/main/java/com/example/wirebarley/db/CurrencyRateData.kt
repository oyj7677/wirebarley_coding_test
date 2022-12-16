package com.example.wirebarley.db

import android.content.Context

class CurrencyRateData {

    private val RATE_USD_JPY = "pref_usd_jpy"
    fun getRateUsdJpy(context: Context): Float {
        return PreferenceUtil(context).getPreferenceFloat(RATE_USD_JPY, 0f)
    }

    fun setRateUsdJpy(context: Context, value: Float): Boolean {
        return PreferenceUtil(context).setPreferenceFloat(RATE_USD_JPY, value)
    }

    private val RATE_USD_KRW = "pref_usd_krw"
    fun getRateUsdKrw(context: Context): Float {
        return PreferenceUtil(context).getPreferenceFloat(RATE_USD_KRW, 0f)
    }

    fun setRateUsdKrw(context: Context, value: Float): Boolean {
        return PreferenceUtil(context).setPreferenceFloat(RATE_USD_KRW, value)
    }

    private val RATE_USD_PHP = "pref_usd_php"
    fun getRateUsdPhp(context: Context): Float {
        return PreferenceUtil(context).getPreferenceFloat(RATE_USD_PHP, 0f)
    }

    fun setRateUsdPhp(context: Context, value: Float): Boolean {
        return PreferenceUtil(context).setPreferenceFloat(RATE_USD_PHP, value)
    }
}