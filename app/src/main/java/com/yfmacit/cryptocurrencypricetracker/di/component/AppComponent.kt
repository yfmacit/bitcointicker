package com.yfmacit.cryptocurrencypricetracker.di.component

import android.app.Application
import com.yfmacit.cryptocurrencypricetracker.MainApp
import com.yfmacit.cryptocurrencypricetracker.di.builder.ActivityBuilder
import com.yfmacit.cryptocurrencypricetracker.di.module.ApiModule
import com.yfmacit.cryptocurrencypricetracker.di.module.AppModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(modules = [AndroidInjectionModule::class, AndroidSupportInjectionModule::class,
    AppModule::class, ActivityBuilder::class, ApiModule::class])
interface AppComponent {

    fun inject(app: MainApp)

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }
}