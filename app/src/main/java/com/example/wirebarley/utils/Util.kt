package com.example.tordertask.utils

import java.security.DigestException
import java.security.MessageDigest
import java.security.Timestamp
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.*

open class Util {

    companion object{
        fun hashSHA256(msg: String): String {
            val hash: ByteArray
            try {
                val md = MessageDigest.getInstance("SHA-256")
                md.update(msg.toByteArray())
                hash = md.digest()
            } catch (e: CloneNotSupportedException) {
                throw DigestException("couldn't make digest of partial content");
            }

            return bytesToHex(hash)
        }

        private val digits = "0123456789ABCDEF"

        private fun bytesToHex(byteArray: ByteArray): String {
            val hexChars = CharArray(byteArray.size * 2)
            for (i in byteArray.indices) {
                val v = byteArray[i].toInt() and 0xff
                hexChars[i * 2] = digits[v shr 4]
                hexChars[i * 2 + 1] = digits[v and 0xf]
            }
            return String(hexChars)
        }

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