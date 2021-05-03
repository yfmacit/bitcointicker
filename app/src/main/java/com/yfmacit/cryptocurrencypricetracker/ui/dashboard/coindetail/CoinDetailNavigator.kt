package com.yfmacit.cryptocurrencypricetracker.ui.dashboard.coindetail

import com.yfmacit.cryptocurrencypricetracker.ui.base.BaseNavigator

interface CoinDetailNavigator : BaseNavigator{
    fun setSimplePriceRefreshInterval(value: Int)
    fun refreshPrice()
    fun openLogin()
}