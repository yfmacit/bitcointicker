package com.yfmacit.cryptocurrencypricetracker.utils

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import java.lang.IllegalArgumentException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ViewModelProviderFactory<V : Any>(private val viewModel: V) : ViewModelProvider.Factory  {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(viewModel.javaClass))
            return viewModel as T
        throw IllegalArgumentException("unknown class name")
    }
}