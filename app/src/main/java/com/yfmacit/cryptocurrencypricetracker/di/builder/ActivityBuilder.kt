package com.yfmacit.cryptocurrencypricetracker.di.builder

import com.yfmacit.cryptocurrencypricetracker.ui.login.LoginActivity
import com.yfmacit.cryptocurrencypricetracker.ui.login.LoginActivityModule
import com.yfmacit.cryptocurrencypricetracker.ui.splash.SplashActivity
import com.yfmacit.cryptocurrencypricetracker.ui.splash.SplashActivityModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBuilder {
    @ContributesAndroidInjector(modules = [SplashActivityModule::class])
    internal abstract fun bindSplashActivity() : SplashActivity

    @ContributesAndroidInjector(modules = [LoginActivityModule::class])
    internal abstract fun bindLoginActivity() : LoginActivity
}