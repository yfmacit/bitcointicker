package com.yfmacit.cryptocurrencypricetracker.ui.splash

import com.yfmacit.cryptocurrencypricetracker.data.DataManager
import com.yfmacit.cryptocurrencypricetracker.ui.base.BaseViewModel
import com.yfmacit.cryptocurrencypricetracker.utils.rx.SchedulerProvider

class SplashViewModel(dataManager: DataManager,
                      schedulerProvider: SchedulerProvider
) : BaseViewModel<SplashNavigator>(dataManager, schedulerProvider) {

    fun checkUserIsLoggedIn() {
        if (dataManager.userIsLoggedIn()) {
            dataManager.getFavouriteCoins(dataManager.userId!!,
                successHandler = { coinList ->
                    dataManager.favoriteCoinList.value = coinList
                    navigator.openDashboard(false)
                },
                failHandler = {
                    navigator.openDashboard(false)
                }
            )
        } else {
            navigator.openDashboard(true)
        }
    }
}