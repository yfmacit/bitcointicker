package com.yfmacit.cryptocurrencypricetracker.ui.dashboard.coinlist

import com.yfmacit.cryptocurrencypricetracker.ui.base.BaseNavigator

interface CoinListNavigator : BaseNavigator {
    fun openCoinDetail(coinId: String)
}