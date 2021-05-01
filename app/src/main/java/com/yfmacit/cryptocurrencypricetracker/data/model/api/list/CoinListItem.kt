package com.yfmacit.cryptocurrencypricetracker.data.model.api.list

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CoinListItem(
    @Expose @SerializedName("id")
    var id: String? = null,
    @Expose @SerializedName("symbol")
    var symbol: String? = null,
    @Expose @SerializedName("name")
    var name: String? = null) : Parcelable