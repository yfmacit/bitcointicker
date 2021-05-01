package com.yfmacit.cryptocurrencypricetracker.data

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.yfmacit.cryptocurrencypricetracker.data.local.PreferencesHelper
import com.yfmacit.cryptocurrencypricetracker.data.model.api.list.CoinListItem
import com.yfmacit.cryptocurrencypricetracker.data.remote.coingecko.CoinGeckoApiHelper
import com.yfmacit.cryptocurrencypricetracker.data.remote.firebase.FirebaseAuthHelper
import com.yfmacit.cryptocurrencypricetracker.data.remote.firebase.FirestoreHelper

interface DataManager : PreferencesHelper, CoinGeckoApiHelper, FirebaseAuthHelper, FirestoreHelper {
    var favoriteCoinList: MutableLiveData<MutableList<CoinListItem>>
    fun getContext(): Context
    fun getString(id: Int, language: String?) : String
}