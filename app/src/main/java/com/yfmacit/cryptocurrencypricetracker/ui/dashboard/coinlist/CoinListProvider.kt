package com.yfmacit.cryptocurrencypricetracker.ui.dashboard.coinlist

import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class CoinListProvider {

    @ContributesAndroidInjector(modules = [CoinListModule::class])
    internal abstract fun provideCoinListFragmentFactory() : CoinListFragment

}