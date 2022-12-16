package com.example.wirebarley.db

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences

class PreferenceUtil(context: Context) {
    private val prefs: SharedPreferences =
        context.getSharedPreferences("prefCurrencyRateData", Context.MODE_PRIVATE)


    @SuppressLint("CommitPrefEdits")
    fun setPreferenceString(key: String, value: String): Boolean {
        val edit =  prefs.edit()
        edit.putString(key, value)
        return edit.commit()
    }

    fun getPreferenceString(key: String, defaultValue: String): String? {
        return prefs.getString(key, defaultValue)
    }

    fun setPreferenceFloat(key: String, value: Float): Boolean {
        val edit =  prefs.edit()
        edit.putFloat(key, value)
        return edit.commit()
    }

    fun getPreferenceFloat(key: String, defaultValue: Float): Float {
        return prefs.getFloat(key, defaultValue)
    }
}