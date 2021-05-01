package com.yfmacit.cryptocurrencypricetracker.di.builder

import com.yfmacit.cryptocurrencypricetracker.ui.dashboard.DashboardActivity
import com.yfmacit.cryptocurrencypricetracker.ui.dashboard.DashboardActivityModule
import com.yfmacit.cryptocurrencypricetracker.ui.dashboard.coindetail.CoinDetailProvider
import com.yfmacit.cryptocurrencypricetracker.ui.dashboard.coinlist.CoinListProvider
import com.yfmacit.cryptocurrencypricetracker.ui.dashboard.favourite.FavouriteProvider
import com.yfmacit.cryptocurrencypricetracker.ui.login.LoginActivity
import com.yfmacit.cryptocurrencypricetracker.ui.login.LoginActivityModule
import com.yfmacit.cryptocurrencypricetracker.ui.splash.SplashActivity
import com.yfmacit.cryptocurrencypricetracker.ui.splash.SplashActivityModule
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.android.support.AndroidSupportInjectionModule

@Module
abstract class ActivityBuilder {

    @ContributesAndroidInjector(modules =
    [AndroidSupportInjectionModule::class,
        SplashActivityModule::class])
    internal abstract fun bindSplashActivity() : SplashActivity

    @ContributesAndroidInjector(modules =
    [AndroidSupportInjectionModule::class,
            LoginActivityModule::class])
    internal abstract fun bindLoginActivity() : LoginActivity

    @ContributesAndroidInjector(modules =
    [AndroidSupportInjectionModule::class,
        DashboardActivityModule::class,
        CoinListProvider::class,
        FavouriteProvider::class,
        CoinDetailProvider::class])
    internal abstract fun bindDashboardActivity() : DashboardActivity
}