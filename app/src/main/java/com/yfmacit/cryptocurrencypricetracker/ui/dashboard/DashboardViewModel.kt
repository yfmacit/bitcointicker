package com.yfmacit.cryptocurrencypricetracker.ui.dashboard

import com.yfmacit.cryptocurrencypricetracker.data.DataManager
import com.yfmacit.cryptocurrencypricetracker.ui.base.BaseViewModel
import com.yfmacit.cryptocurrencypricetracker.utils.rx.SchedulerProvider

class DashboardViewModel(dataManager: DataManager,
                         schedulerProvider: SchedulerProvider
) : BaseViewModel<DashboardNavigator>(dataManager, schedulerProvider) {
}