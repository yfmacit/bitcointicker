package com.yfmacit.cryptocurrencypricetracker.data.model.api.detail


import com.google.gson.Gson
import com.google.gson.JsonObject
import com.google.gson.annotations.SerializedName

data class GetCoinDetailResponse(
    @SerializedName("id")
    var id: String? = null,
    @SerializedName("symbol")
    var symbol: String? = null,
    @SerializedName("name")
    var name: String? = null,
    @SerializedName("asset_platform_id")
    var assetPlatformId: String? = null,
    @SerializedName("platforms")
    var platforms: JsonObject? = null,
    @SerializedName("image")
    var image: JsonObject? = null,
    @SerializedName("block_time_in_minutes")
    var blockTimeInMinutes: Int? = null,
    @SerializedName("hashing_algorithm")
    var hashingAlgorithm: String? = null,
    @SerializedName("additional_notices")
    var additional_notices: List<String>? = null,
    @SerializedName("description")
    var description: JsonObject? = null,
    @SerializedName("market_data")
    var marketData: MarketData? = null
) {
    fun getDescriptionString() : String? {
        val descriptions = Gson().fromJson(description, HashMap::class.java) as HashMap<String, String?>?
        return descriptions?.get("en")
    }

    fun getImageUrl(): String {
        return image?.get("large")?.asString?:""
    }

    fun getLast24HourPer() : String {
        return when(marketData?.priceChangePercentage24h) {
            null -> "NaN"
            else -> {
                var value = ""
                value = if (marketData?.priceChangePercentage24h ?: 0.0 > 0)
                    "+"
                else
                    ""
                value += marketData?.priceChangePercentage24h.toString()
                value
            }
        }
    }
}