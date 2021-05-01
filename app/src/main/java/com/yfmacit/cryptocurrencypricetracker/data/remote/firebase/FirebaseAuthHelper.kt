package com.yfmacit.cryptocurrencypricetracker.data.remote.firebase

import com.yfmacit.cryptocurrencypricetracker.data.model.app.firestore.LoginResponse


interface FirebaseAuthHelper {
    fun userIsLoggedIn() : Boolean
    fun authUser(email: String, password: String, handler: (LoginResponse) -> Unit)
}