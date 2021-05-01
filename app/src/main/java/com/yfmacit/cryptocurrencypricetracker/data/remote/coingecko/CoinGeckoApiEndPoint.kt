package com.yfmacit.cryptocurrencypricetracker.data.remote.coingecko

sealed class CoinGeckoApiEndPoint {
    companion object {
        const val CONTENT_TYPE = "application/json;charset=UTF-8"
        const val ENDPOINT_COIN_LIST = "/coins/list"
        const val ENDPOINT_COIN_DETAIL = "/coins/huobi-polkadot?localization=true&tickers=false&market_data=false&community_data=false&developer_data=false&sparkline=false"
    }
}