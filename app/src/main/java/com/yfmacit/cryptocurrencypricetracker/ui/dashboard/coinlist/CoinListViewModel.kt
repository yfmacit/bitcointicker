package com.yfmacit.cryptocurrencypricetracker.ui.dashboard.coinlist

import androidx.lifecycle.MutableLiveData
import com.yfmacit.cryptocurrencypricetracker.data.DataManager
import com.yfmacit.cryptocurrencypricetracker.data.model.api.list.CoinListItem
import com.yfmacit.cryptocurrencypricetracker.data.model.app.enums.AppEnums
import com.yfmacit.cryptocurrencypricetracker.ui.base.BaseViewModel
import com.yfmacit.cryptocurrencypricetracker.utils.rx.SchedulerProvider
import io.reactivex.observers.DisposableObserver

class CoinListViewModel(dataManager: DataManager, schedulerProvider: SchedulerProvider
) : BaseViewModel<CoinListNavigator>(dataManager, schedulerProvider) {
    var coinList: MutableLiveData<ArrayList<CoinListItem>> = MutableLiveData()
    var filteredCoinList: MutableLiveData<MutableList<CoinListItem>> = MutableLiveData()

    fun fetchCoinList(swipeHandler: (() -> Unit)?) {
        if (swipeHandler == null)
            navigator.showLoading()

        compositeDisposable.add(
            dataManager.getCoinListImpl()
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .subscribeWith(object : DisposableObserver<ArrayList<CoinListItem>>() {
                    override fun onNext(t: ArrayList<CoinListItem>) {
                        navigator.hideLoading()
                        swipeHandler?.invoke()
                        coinList.value = t
                    }

                    override fun onError(e: Throwable) {
                        navigator.hideLoading()
                        swipeHandler?.invoke()
                        navigator.showMessage(AppEnums.MessageStatus.ERROR,
                            "Failed", "Failed operation, please try again later.",
                            null, null
                        )
                    }

                    override fun onComplete() {
                    }
                })
        )
    }
}