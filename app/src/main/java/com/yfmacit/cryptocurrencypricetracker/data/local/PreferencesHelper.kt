package com.yfmacit.cryptocurrencypricetracker.data.local

interface PreferencesHelper {
    var userId: String?
    var email: String?
    fun clearAllData()
}