package com.example.wirebarley.db

import android.content.Context
import android.content.SharedPreferences

class PreferenceUtil(context: Context) {
    private val prefs: SharedPreferences =
        context.getSharedPreferences("prefCurrencyRateData", Context.MODE_PRIVATE)

    fun setPreferenceFloat(key: String, value: Float): Boolean {
        val edit =  prefs.edit()
        edit.putFloat(key, value)
        return edit.commit()
    }

    fun getPreferenceFloat(key: String, defaultValue: Float): Float {
        return prefs.getFloat(key, defaultValue)
    }
}