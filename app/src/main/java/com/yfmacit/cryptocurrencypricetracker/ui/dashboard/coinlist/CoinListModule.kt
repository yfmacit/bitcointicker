package com.yfmacit.cryptocurrencypricetracker.ui.dashboard.coinlist

import com.yfmacit.cryptocurrencypricetracker.data.DataManager
import com.yfmacit.cryptocurrencypricetracker.utils.rx.SchedulerProvider
import dagger.Module
import dagger.Provides

@Module
class CoinListModule {

    @Provides
    internal fun provideCoinListViewModel(dataManager: DataManager,
                                          schedulerProvider: SchedulerProvider
    ) : CoinListViewModel {
        return CoinListViewModel(dataManager, schedulerProvider)
    }
}