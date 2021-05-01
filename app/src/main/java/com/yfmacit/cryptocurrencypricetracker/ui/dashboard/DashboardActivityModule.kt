package com.yfmacit.cryptocurrencypricetracker.ui.dashboard

import androidx.lifecycle.ViewModelProvider
import com.yfmacit.cryptocurrencypricetracker.data.DataManager
import com.yfmacit.cryptocurrencypricetracker.ui.splash.SplashViewModel
import com.yfmacit.cryptocurrencypricetracker.utils.ViewModelProviderFactory
import com.yfmacit.cryptocurrencypricetracker.utils.rx.SchedulerProvider
import dagger.Module
import dagger.Provides

@Module
class DashboardActivityModule {
    @Provides
    internal fun provideLoginViewModel(dataManager: DataManager,
                                       schedulerProvider: SchedulerProvider): DashboardViewModel {
        return DashboardViewModel(dataManager, schedulerProvider)
    }

    @Provides
    internal fun viewModelProvider(viewModel: DashboardViewModel) : ViewModelProvider.Factory {
        return ViewModelProviderFactory(viewModel)
    }
}