package com.yfmacit.cryptocurrencypricetracker.ui.login

import androidx.lifecycle.ViewModelProvider
import com.yfmacit.cryptocurrencypricetracker.ui.splash.SplashViewModel
import com.yfmacit.cryptocurrencypricetracker.utils.ViewModelProviderFactory
import dagger.Module
import dagger.Provides

@Module
class LoginActivityModule {
    @Provides
    internal fun provideLoginViewModel(): LoginViewModel {
        return LoginViewModel()
    }

    @Provides
    internal fun viewModelProvider(viewModel: LoginViewModel) : ViewModelProvider.Factory {
        return ViewModelProviderFactory(viewModel)
    }
}