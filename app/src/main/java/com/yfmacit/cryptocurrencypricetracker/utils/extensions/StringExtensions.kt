package com.yfmacit.cryptocurrencypricetracker.utils.extensions

import android.util.Patterns

fun String?.validateEmailAddress() : Boolean {
    return when (this) {
        null -> false
        "" -> false
        else -> Patterns.EMAIL_ADDRESS.matcher(this).matches()
    }
}

fun String?.validatePassword() : Boolean {
    return when (this) {
        null -> false
        "" -> false
        else -> true
    }
}