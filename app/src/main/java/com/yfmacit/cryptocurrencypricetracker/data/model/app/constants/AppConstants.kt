package com.yfmacit.cryptocurrencypricetracker.data.model.app.constants

import com.ncapdevi.fragnav.FragNavController

class AppConstants {
    companion object {
        const val PREF_NAME = "crypto_currency_list_pref"

        // BottomMenu
        const val TAB_DASHBOARD = FragNavController.TAB1
        const val TAB_FAVOURITE = FragNavController.TAB2

        // ACTIVITY RESULT REQUEST CODE requestCode: Int, resultCode
        const val LOGIN_REQUEST_CODE_IN_DASHBOARD = 1000
        const val LOGIN_REQUEST_CODE_IN_DETAIL = 1001
        const val LOGIN_SUCCESS_RESULT_CODE = 1000
        const val LOGIN_FAILED_RESULT_CODE = 1001
    }
}