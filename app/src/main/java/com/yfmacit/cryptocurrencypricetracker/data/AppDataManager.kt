package com.yfmacit.cryptocurrencypricetracker.data

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.google.gson.JsonObject
import com.yfmacit.cryptocurrencypricetracker.data.local.AppPreferencesHelper
import com.yfmacit.cryptocurrencypricetracker.data.model.api.list.CoinListItem
import com.yfmacit.cryptocurrencypricetracker.data.model.api.detail.GetCoinDetailResponse
import com.yfmacit.cryptocurrencypricetracker.data.model.app.firestore.LoginResponse
import com.yfmacit.cryptocurrencypricetracker.data.remote.coingecko.CoinGeckoApiHelper
import com.yfmacit.cryptocurrencypricetracker.data.remote.firebase.FirebaseAuthHelper
import com.yfmacit.cryptocurrencypricetracker.data.remote.firebase.FirestoreHelper
import io.reactivex.Observable
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AppDataManager
    @Inject
    constructor(private val mContext: Context,
                private val mAppPreferencesHelper: AppPreferencesHelper,
                private val mCoinGeckoApiHelper: CoinGeckoApiHelper,
                private val mFirebaseAuthHelper: FirebaseAuthHelper,
                private val mFirestoreHelper: FirestoreHelper
    ) : DataManager {

    override var favoriteCoinList: MutableLiveData<MutableList<CoinListItem>> = MutableLiveData()

    override fun getContext(): Context {
        return mContext
    }

    override fun getString(id: Int, language: String?): String {
        return mContext.getString(id)
    }

    // SharedPref
    override var userId: String?
        get() = mAppPreferencesHelper.userId
        set(userId) { mAppPreferencesHelper.userId = userId }

    override var email: String?
        get() = mAppPreferencesHelper.email
        set(email) { mAppPreferencesHelper.email = email }

    override fun clearAllData() {
        mAppPreferencesHelper.clearAllData()
        favoriteCoinList.value?.clear()
    }

    // ServiceImpl
    override fun getCoinListImpl(): Observable<ArrayList<CoinListItem>> {
        return mCoinGeckoApiHelper.getCoinListImpl()
    }

    override fun getCoinDetailImpl(coinId: String): Observable<GetCoinDetailResponse> {
        return mCoinGeckoApiHelper.getCoinDetailImpl(coinId)
    }

    override fun getCoinSimplePrice(coinId: String): Observable<JsonObject> {
        return mCoinGeckoApiHelper.getCoinSimplePrice(coinId)
    }

    // Firebase Auth
    override fun userIsLoggedIn(): Boolean {
        return mFirebaseAuthHelper.userIsLoggedIn() && userId != null
    }

    override fun authUser(
        email: String,
        password: String,
        handler: (LoginResponse) -> Unit
    ) {
        mFirebaseAuthHelper.authUser(email, password, handler)
    }

    // Firestore
    override fun addFavouriteCoin(userId: String, coin: CoinListItem,
                                  successHandler: () -> Unit, failHandler: () -> Unit) {
        mFirestoreHelper.addFavouriteCoin(userId, coin, successHandler, failHandler)
    }

    override fun removeFavouriteCoin(userId: String, coin: CoinListItem,
                                     successHandler: () -> Unit, failHandler: () -> Unit) {
        return mFirestoreHelper.removeFavouriteCoin(userId, coin, successHandler, failHandler)
    }

    override fun getFavouriteCoins(
        userId: String,
        successHandler: (MutableList<CoinListItem>) -> Unit,
        failHandler: () -> Unit
    ) {
        return mFirestoreHelper.getFavouriteCoins(userId, successHandler, failHandler)
    }


}