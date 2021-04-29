package com.yfmacit.cryptocurrencypricetracker.ui.splash

import androidx.lifecycle.ViewModelProvider
import com.yfmacit.cryptocurrencypricetracker.utils.ViewModelProviderFactory
import dagger.Module
import dagger.Provides

@Module
class SplashActivityModule {

    @Provides
    internal fun provideSplashViewModel(): SplashViewModel {
        return SplashViewModel()
    }

    @Provides
    internal fun viewModelProvider(viewModel: SplashViewModel) : ViewModelProvider.Factory {
        return ViewModelProviderFactory(viewModel)
    }
}
