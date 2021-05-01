package com.yfmacit.cryptocurrencypricetracker.data.model.api.detail

import com.google.gson.annotations.SerializedName

data class PriceChange(
    @SerializedName("eur")
    var eur: Double? = null,
    @SerializedName("usd")
    var usd: Double? = null
)