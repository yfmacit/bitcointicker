package com.yfmacit.cryptocurrencypricetracker.ui.dashboard.favourite

import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FavouriteProvider {
    @ContributesAndroidInjector(modules = [FavouriteModule::class])
    internal abstract fun provideFavouriteFragmentFactory() : FavouriteFragment
}