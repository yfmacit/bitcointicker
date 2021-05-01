package com.yfmacit.cryptocurrencypricetracker.data.model.api.detail

import com.google.gson.annotations.SerializedName

data class MarketData(
    @SerializedName("price_change_percentage_24h")
    var priceChangePercentage24h: Double? = null,
    @SerializedName("price_change_percentage_24h_in_currency")
    var priceChangePercentage24hInCurrency: PriceChange? = null
)