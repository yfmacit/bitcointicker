package com.yfmacit.cryptocurrencypricetracker.ui.dashboard.coindetail

import com.yfmacit.cryptocurrencypricetracker.data.DataManager
import com.yfmacit.cryptocurrencypricetracker.utils.rx.SchedulerProvider
import dagger.Module
import dagger.Provides

@Module
class CoinDetailModule {

    @Provides
    internal fun provideCoinDetailViewModel(dataManager: DataManager,
                                            schedulerProvider: SchedulerProvider
    ) : CoinDetailViewModel {
        return CoinDetailViewModel(dataManager, schedulerProvider)
    }
}