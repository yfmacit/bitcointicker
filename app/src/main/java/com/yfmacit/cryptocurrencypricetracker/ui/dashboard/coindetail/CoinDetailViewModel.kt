package com.yfmacit.cryptocurrencypricetracker.ui.dashboard.coindetail

import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import com.google.gson.JsonObject
import com.yfmacit.cryptocurrencypricetracker.data.DataManager
import com.yfmacit.cryptocurrencypricetracker.data.model.api.detail.GetCoinDetailResponse
import com.yfmacit.cryptocurrencypricetracker.data.model.api.list.CoinListItem
import com.yfmacit.cryptocurrencypricetracker.data.model.app.enums.AppEnums
import com.yfmacit.cryptocurrencypricetracker.ui.base.BaseViewModel
import com.yfmacit.cryptocurrencypricetracker.utils.rx.SchedulerProvider
import io.reactivex.observers.DisposableObserver

class CoinDetailViewModel(dataManager: DataManager, schedulerProvider: SchedulerProvider
) : BaseViewModel<CoinDetailNavigator>(dataManager, schedulerProvider) {
    var isAddedFavourite : ObservableField<Boolean> = ObservableField(false)
    var coinDetail : ObservableField<GetCoinDetailResponse> = ObservableField()
    var coinSimplePrice: ObservableField<String> = ObservableField("-")
    var coinSimplePriceRefreshIntervalSecond: ObservableField<Int> = ObservableField(30)
    var last24HoursPercentageRiseUp: ObservableField<Boolean> = ObservableField(false)

    fun setSimplePriceRefreshIntervalClicked() {
        navigator.setSimplePriceRefreshInterval(coinSimplePriceRefreshIntervalSecond.get()?:30)
    }

    fun fetchCoinDetail(coinId: String) {
        navigator.showLoading()
        compositeDisposable.add(
            dataManager.getCoinDetailImpl(coinId)
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .subscribeWith(object : DisposableObserver<GetCoinDetailResponse>() {
                    override fun onNext(t: GetCoinDetailResponse) {
                        coinDetail.set(t)
                        dataManager.favoriteCoinList.value?.find { coin ->
                            coin.id == coinId
                        }.apply {
                            if (this != null)
                                isAddedFavourite.set(true)
                        }

                        if (t.marketData?.currentPrice?.usd != null)
                            coinSimplePrice.set(t.marketData?.currentPrice?.usd?.toString())

                        if (t.marketData?.priceChangePercentage24h ?: 0.0 > 0.0)
                            last24HoursPercentageRiseUp.set(true)

                        navigator.refreshPrice()
                        navigator.hideLoading()
                    }

                    override fun onError(e: Throwable) {
                        navigator.hideLoading()
                        navigator.showMessage(AppEnums.MessageStatus.ERROR, "Failed",
                            "Failed operation, please try again later.",
                            null, null)
                    }

                    override fun onComplete() {
                    }
                })
        )
    }

    fun fetchCoinSimplePrice() {
        val coinId = coinDetail.get()?.id?:""
        compositeDisposable.add(
            dataManager.getCoinSimplePrice(coinId)
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .subscribeWith(object : DisposableObserver<JsonObject>() {
                    override fun onNext(t: JsonObject) {
                        if (t[coinId] != null && t[coinId].asJsonObject["usd"] != null){
                            coinSimplePrice.set(t[coinId].asJsonObject["usd"].toString())
                        }
                    }

                    override fun onError(e: Throwable) {
                    }

                    override fun onComplete() {
                    }
                })
        )
    }

    fun addOrRemoveFavouriteCoinClicked() {
        if (!dataManager.   userIsLoggedIn()) {
            navigator.openLogin()
        } else {
            val coin = CoinListItem(
                coinDetail.get()?.id,
                coinDetail.get()?.name,
                coinDetail.get()?.symbol
            )

            navigator.showLoading()
            if (isAddedFavourite.get()!!) {
                dataManager.removeFavouriteCoin(dataManager.userId!!, coin,
                    successHandler = {
                        navigator.hideLoading()
                        isAddedFavourite.set(false)
                        dataManager.favoriteCoinList.value?.remove(coin)
                        navigator.showMessage(AppEnums.MessageStatus.SUCCESS,
                            "Success", "Removed successfully",
                            null, null)
                    },
                    failHandler = {
                        navigator.hideLoading()
                        navigator.showMessage(AppEnums.MessageStatus.ERROR, "Failed",
                            "Failed operation, please try again later.",
                            null, null)
                    }
                )
            } else {
                dataManager.addFavouriteCoin(dataManager.userId!!, coin,
                    successHandler = {
                        navigator.hideLoading()
                        isAddedFavourite.set(true)
                        dataManager.favoriteCoinList.value?.add(coin)
                        navigator.showMessage(AppEnums.MessageStatus.SUCCESS,
                            "Success", "Added successfully",
                            null, null)
                    },
                    failHandler = {
                        navigator.hideLoading()
                        navigator.showMessage(AppEnums.MessageStatus.ERROR, "Failed",
                            "Failed operation, please try again later.",
                            null, null)
                    }
                )
            }
        }
    }
}