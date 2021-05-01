package com.yfmacit.cryptocurrencypricetracker.ui.dashboard.coindetail

import androidx.databinding.ObservableField
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
    var last24HoursPercentageRiseUp: ObservableField<Boolean> = ObservableField(false)

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
                        if (t.marketData?.priceChangePercentage24h ?: 0.0 > 0.0)
                            last24HoursPercentageRiseUp.set(true)

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