package com.yfmacit.cryptocurrencypricetracker.ui.dashboard.coindetail

import com.yfmacit.cryptocurrencypricetracker.data.DataManager
import com.yfmacit.cryptocurrencypricetracker.utils.rx.SchedulerProvider
import dagger.Module
import dagger.Provides
import dagger.android.ContributesAndroidInjector

@Module
abstract class CoinDetailProvider {

    @ContributesAndroidInjector(modules = [CoinDetailModule::class])
    internal abstract fun provideCoinDetailFragmentFactory() : CoinDetailFragment

}