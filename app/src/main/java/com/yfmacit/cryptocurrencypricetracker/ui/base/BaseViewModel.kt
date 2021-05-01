package com.yfmacit.cryptocurrencypricetracker.ui.base

import androidx.databinding.ObservableBoolean
import androidx.lifecycle.ViewModel
import com.yfmacit.cryptocurrencypricetracker.data.DataManager
import com.yfmacit.cryptocurrencypricetracker.ui.common.dialogs.CommonDialog
import com.yfmacit.cryptocurrencypricetracker.utils.rx.SchedulerProvider
import io.reactivex.disposables.CompositeDisposable
import java.lang.ref.WeakReference

abstract class BaseViewModel<N>(val dataManager: DataManager,
                                val schedulerProvider: SchedulerProvider) : ViewModel() {

    private var isLoading = ObservableBoolean(false)

    val compositeDisposable: CompositeDisposable = CompositeDisposable()
    override fun onCleared() {
        compositeDisposable.dispose()
        super.onCleared()
    }

    var loaderState: ObservableBoolean
        get() = this.isLoading
        set(loading) {
            this.isLoading = loading
        }

    var mNavigator : WeakReference<N>? = null

    var navigator : N
        get() = mNavigator!!.get()!!
        set(navigator) {
            this.mNavigator = WeakReference(navigator)
        }

    var safeNavigator : N?
        get() = mNavigator?.get()
        set(navigator) {
            this.mNavigator = WeakReference(navigator!!)
        }
}