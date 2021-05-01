package com.yfmacit.cryptocurrencypricetracker.di.module

import android.app.Application
import android.content.Context
import com.yfmacit.cryptocurrencypricetracker.data.AppDataManager
import com.yfmacit.cryptocurrencypricetracker.data.DataManager
import com.yfmacit.cryptocurrencypricetracker.data.local.AppPreferencesHelper
import com.yfmacit.cryptocurrencypricetracker.data.local.PreferencesHelper
import com.yfmacit.cryptocurrencypricetracker.data.model.app.constants.AppConstants
import com.yfmacit.cryptocurrencypricetracker.data.remote.coingecko.CoinGeckoApiHelper
import com.yfmacit.cryptocurrencypricetracker.data.remote.coingecko.AppCoinGeckoApiHelper
import com.yfmacit.cryptocurrencypricetracker.data.remote.firebase.AppFirebaseAuthHelper
import com.yfmacit.cryptocurrencypricetracker.data.remote.firebase.AppFirestoreHelper
import com.yfmacit.cryptocurrencypricetracker.data.remote.firebase.FirebaseAuthHelper
import com.yfmacit.cryptocurrencypricetracker.data.remote.firebase.FirestoreHelper
import com.yfmacit.cryptocurrencypricetracker.di.PreferenceInfo
import com.yfmacit.cryptocurrencypricetracker.utils.rx.AppSchedulerProvider
import com.yfmacit.cryptocurrencypricetracker.utils.rx.SchedulerProvider
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule {

    @Provides
    @Singleton
    internal fun provideContext(application: Application): Context {
        return application
    }

    @Provides
    @Singleton
    internal fun provideDataManager(appDataManager: AppDataManager): DataManager {
        return appDataManager
    }

    @Provides
    @Singleton
    internal fun provideApiHelper(appCoinGeckoApiHelper: AppCoinGeckoApiHelper): CoinGeckoApiHelper {
        return appCoinGeckoApiHelper
    }

    @Singleton
    @Provides
    internal fun provideFirebaseFirebaseAuth(appFirebaseAuthHelper: AppFirebaseAuthHelper): FirebaseAuthHelper {
        return appFirebaseAuthHelper
    }

    @Singleton
    @Provides
    internal fun provideFirebaseFirestore(appFirestoreHelper: AppFirestoreHelper): FirestoreHelper {
        return appFirestoreHelper
    }

    @Provides
    internal fun provideSchedulerProvider(): SchedulerProvider {
        return AppSchedulerProvider()
    }

    @Provides
    @PreferenceInfo
    internal fun providePreferencesName(): String {
        return AppConstants.PREF_NAME
    }

    @Provides
    @Singleton
    internal fun providePreferencesHelper(appPreferencesHelper: AppPreferencesHelper): PreferencesHelper {
        return appPreferencesHelper
    }
}