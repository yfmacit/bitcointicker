package com.yfmacit.cryptocurrencypricetracker.data.remote.coingecko

import com.yfmacit.cryptocurrencypricetracker.data.model.api.list.CoinListItem
import com.yfmacit.cryptocurrencypricetracker.data.model.api.detail.GetCoinDetailResponse
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

interface CoinGeckoApi {
    @GET("coins/list")
    fun getCoinList(@Header("Content-Type") contentType: String) : Observable<ArrayList<CoinListItem>>

    @GET("coins/{coinId}?localization=true&tickers=false&market_data=true&community_data=false&developer_data=false&sparkline=false")
    fun getCoinDetail(@Path("coinId") coinId: String, @Header("Content-Type") contentType: String) : Observable<GetCoinDetailResponse>
}