package com.yfmacit.cryptocurrencypricetracker.ui.login

import androidx.lifecycle.ViewModelProvider
import com.yfmacit.cryptocurrencypricetracker.data.DataManager
import com.yfmacit.cryptocurrencypricetracker.ui.splash.SplashViewModel
import com.yfmacit.cryptocurrencypricetracker.utils.ViewModelProviderFactory
import com.yfmacit.cryptocurrencypricetracker.utils.rx.SchedulerProvider
import dagger.Module
import dagger.Provides

@Module
class LoginActivityModule {
    @Provides
    internal fun provideLoginViewModel(dataManager: DataManager,
                                       schedulerProvider: SchedulerProvider): LoginViewModel {
        return LoginViewModel(dataManager, schedulerProvider)
    }

    @Provides
    internal fun viewModelProvider(viewModel: LoginViewModel) : ViewModelProvider.Factory {
        return ViewModelProviderFactory(viewModel)
    }
}