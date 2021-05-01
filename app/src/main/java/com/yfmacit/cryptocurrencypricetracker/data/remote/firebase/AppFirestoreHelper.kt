package com.yfmacit.cryptocurrencypricetracker.data.remote.firebase

import android.content.Context
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.yfmacit.cryptocurrencypricetracker.data.model.api.list.CoinListItem
import com.yfmacit.cryptocurrencypricetracker.ui.splash.SplashActivity
import com.yfmacit.cryptocurrencypricetracker.utils.AppLogger
import javax.inject.Inject

class AppFirestoreHelper @Inject
constructor(context: Context) : FirestoreHelper {

    companion object {
        const val FS_USER_COLLECTION_NAME = "users"
        const val FS_USER_FAV_COIN_COLLECTION_NAME = "favCoinList"
    }

    private val db = Firebase.firestore

    override fun addFavouriteCoin(
        userId: String,
        coin: CoinListItem,
        successHandler: () -> Unit,
        failHandler: () -> Unit
    ) {
        db.collection(FS_USER_COLLECTION_NAME)
            .document(userId)
            .collection(FS_USER_FAV_COIN_COLLECTION_NAME)
            .document(coin.id!!)
            .set(hashMapOf(
                "name" to coin.name,
                "symbol" to coin.symbol
            ))
            .addOnSuccessListener {
                successHandler.invoke()
            }
            .addOnFailureListener {
                failHandler.invoke()
            }
    }

    override fun removeFavouriteCoin(
        userId: String,
        coin: CoinListItem,
        successHandler: () -> Unit,
        failHandler: () -> Unit
    ) {
        db.collection(FS_USER_COLLECTION_NAME)
            .document(userId)
            .collection(FS_USER_FAV_COIN_COLLECTION_NAME)
            .document(coin.id!!)
            .delete()
            .addOnSuccessListener {
                successHandler.invoke()
            }
            .addOnFailureListener {
                failHandler.invoke()
            }
    }

    override fun getFavouriteCoins(
        userId: String,
        successHandler: (MutableList<CoinListItem>) -> Unit,
        failHandler: () -> Unit
    ){
        val coinList : MutableList<CoinListItem> = mutableListOf()
        db.collection(FS_USER_COLLECTION_NAME)
            .document(userId)
            .collection(FS_USER_FAV_COIN_COLLECTION_NAME)
            .get()
            .addOnSuccessListener { coinCollection ->
                coinCollection.documents.forEach { coinDocument ->
                    coinList.add(CoinListItem(
                        coinDocument.id,
                        coinDocument["name"].toString(),
                        coinDocument["symbol"].toString()
                    ))
                }
                successHandler(coinList)
            }
            .addOnFailureListener {
                failHandler.invoke()
            }
    }
}