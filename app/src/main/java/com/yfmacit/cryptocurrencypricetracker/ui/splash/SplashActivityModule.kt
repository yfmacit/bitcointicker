package com.yfmacit.cryptocurrencypricetracker.ui.splash

import androidx.lifecycle.ViewModelProvider
import com.yfmacit.cryptocurrencypricetracker.data.DataManager
import com.yfmacit.cryptocurrencypricetracker.utils.ViewModelProviderFactory
import com.yfmacit.cryptocurrencypricetracker.utils.rx.SchedulerProvider
import dagger.Module
import dagger.Provides

@Module
class SplashActivityModule {

    @Provides
    internal fun provideSplashViewModel(dataManager: DataManager, schedulerProvider: SchedulerProvider): SplashViewModel {
        return SplashViewModel(dataManager, schedulerProvider)
    }

    @Provides
    internal fun viewModelProvider(viewModel: SplashViewModel): ViewModelProvider.Factory {
        return ViewModelProviderFactory(viewModel)
    }
}
