package com.yfmacit.cryptocurrencypricetracker.di.builder

import com.yfmacit.cryptocurrencypricetracker.ui.splash.SplashActivity
import com.yfmacit.cryptocurrencypricetracker.ui.splash.SplashActivityModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBuilder {
    @ContributesAndroidInjector(modules = [SplashActivityModule::class])
    internal abstract fun bindSplashActivity() : SplashActivity
}