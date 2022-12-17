package com.example.wirebarley.utils

import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.*

open class Util {

    companion object{
        fun addComma(price: Float): String {
            val formatter = DecimalFormat("#,###,###.##")
            return formatter.format(price)
        }

        fun timeStampToDate(timestamp: Long) : String {
            val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.KOREA)
            return simpleDateFormat.format(timestamp * 1000L)
        }
    }
}