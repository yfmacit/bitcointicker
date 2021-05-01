package com.yfmacit.cryptocurrencypricetracker.data.remote.coingecko

import com.yfmacit.cryptocurrencypricetracker.data.model.api.list.CoinListItem
import com.yfmacit.cryptocurrencypricetracker.data.model.api.detail.GetCoinDetailResponse
import io.reactivex.Observable
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class AppCoinGeckoApiHelper @Inject constructor(private val coinGeckoApi: CoinGeckoApi) : CoinGeckoApiHelper {
    override fun getCoinListImpl(): Observable<ArrayList<CoinListItem>> {
        return coinGeckoApi.getCoinList(CoinGeckoApiEndPoint.CONTENT_TYPE)
    }

    override fun getCoinDetailImpl(coinId: String): Observable<GetCoinDetailResponse> {
        return coinGeckoApi.getCoinDetail(coinId, CoinGeckoApiEndPoint.CONTENT_TYPE)
    }
}