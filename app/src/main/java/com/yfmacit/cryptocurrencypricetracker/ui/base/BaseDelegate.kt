package com.yfmacit.cryptocurrencypricetracker.ui.base

import com.yfmacit.cryptocurrencypricetracker.data.model.app.enums.AppEnums

interface BaseDelegate {
    fun onBackPressed()
    fun showLoading()
    fun hideLoading()
    fun showMessage(type: AppEnums.MessageStatus,
                    title: String,
                    message: String,
                    positiveHandler: (() -> Unit)?,
                    negativeHandler: (() -> Unit)?)
    fun logout()
}