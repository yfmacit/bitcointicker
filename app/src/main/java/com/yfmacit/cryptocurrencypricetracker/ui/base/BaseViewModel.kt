package com.yfmacit.cryptocurrencypricetracker.ui.base

import androidx.databinding.ObservableBoolean
import androidx.lifecycle.ViewModel
import java.lang.ref.WeakReference

abstract class BaseViewModel<N> : ViewModel() {
    private var isLoading = ObservableBoolean(false)
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