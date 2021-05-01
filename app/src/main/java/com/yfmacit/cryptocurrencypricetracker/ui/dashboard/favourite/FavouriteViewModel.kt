package com.yfmacit.cryptocurrencypricetracker.ui.dashboard.favourite

import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import com.yfmacit.cryptocurrencypricetracker.data.DataManager
import com.yfmacit.cryptocurrencypricetracker.data.model.api.list.CoinListItem
import com.yfmacit.cryptocurrencypricetracker.data.model.app.constants.AppConstants
import com.yfmacit.cryptocurrencypricetracker.data.model.app.enums.AppEnums
import com.yfmacit.cryptocurrencypricetracker.ui.base.BaseViewModel
import com.yfmacit.cryptocurrencypricetracker.utils.rx.SchedulerProvider

class FavouriteViewModel(dataManager: DataManager, schedulerProvider: SchedulerProvider
) : BaseViewModel<FavouriteNavigator>(dataManager, schedulerProvider){
    var isEmptyList : ObservableField<Boolean> = ObservableField(false)

    fun fetchFavouriteCoins(swipeHandler: (() -> Unit)?) {
        if (swipeHandler == null)
            navigator.showLoading()

        dataManager.getFavouriteCoins(dataManager.userId!!,
            successHandler = { coinList ->
                navigator.hideLoading()
                swipeHandler?.invoke()
                dataManager.favoriteCoinList.value = coinList
            },
            failHandler = {
                navigator.hideLoading()
                swipeHandler?.invoke()
                navigator.showMessage(AppEnums.MessageStatus.ERROR, "Failed",
                    "Failed operation, please try again later.",
                null, null)
            }
        )
    }

    fun onLogoutClicked() {
        navigator.showMessage(AppEnums.MessageStatus.LOGOUT, "EXIT", "Are you sure?",
            positiveHandler = {
                dataManager.clearAllData()
                navigator.logout()
            },
            negativeHandler = {

            }
        )
    }
}