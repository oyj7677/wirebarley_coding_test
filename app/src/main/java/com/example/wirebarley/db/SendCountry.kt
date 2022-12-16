package com.example.wirebarley.db

enum class SendCountry(val code: Int) {
    KRW(0),
    JPY(1),
    PHP(2);

    companion object {
        private val map = values().associateBy(SendCountry::code) { it }
        operator fun get(code: Int) = map[code] ?: KRW
    }
}
