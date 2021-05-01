package com.yfmacit.cryptocurrencypricetracker.data.remote.firebase

import com.yfmacit.cryptocurrencypricetracker.data.model.api.list.CoinListItem

interface FirestoreHelper {

    fun addFavouriteCoin(userId: String, coin: CoinListItem, successHandler: () -> Unit,
                         failHandler: () -> Unit)

    fun removeFavouriteCoin(userId: String, coin: CoinListItem, successHandler: () -> Unit,
                            failHandler: () -> Unit)

    fun getFavouriteCoins(userId: String, successHandler: (MutableList<CoinListItem>) -> Unit,
                          failHandler: () -> Unit)
}