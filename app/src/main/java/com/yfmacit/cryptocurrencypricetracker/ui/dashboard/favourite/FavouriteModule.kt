package com.yfmacit.cryptocurrencypricetracker.ui.dashboard.favourite

import com.yfmacit.cryptocurrencypricetracker.data.DataManager
import com.yfmacit.cryptocurrencypricetracker.utils.rx.SchedulerProvider
import dagger.Module
import dagger.Provides

@Module
class FavouriteModule {
    @Provides
    internal fun provideFavouriteViewModel(dataManager: DataManager,
                                          schedulerProvider: SchedulerProvider
    ) : FavouriteViewModel {
        return FavouriteViewModel(dataManager, schedulerProvider)
    }
}