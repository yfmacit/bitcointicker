package com.yfmacit.cryptocurrencypricetracker.data.remote.firebase

import android.content.Context
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.yfmacit.cryptocurrencypricetracker.data.model.app.firestore.LoginResponse
import javax.inject.Inject

class AppFirebaseAuthHelper @Inject
constructor(context: Context) : FirebaseAuthHelper{

    private val auth = Firebase.auth

    override fun userIsLoggedIn(): Boolean {
        return auth.currentUser != null
    }

    override fun authUser(email: String, password: String, handler: (LoginResponse) -> Unit) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { result ->

                val loginResponse = if (result.isSuccessful) {
                    LoginResponse(
                        result.isSuccessful,
                        result.result?.user?.uid,
                        result.result?.user?.email)
                } else {
                    LoginResponse(false)
                }

                handler(loginResponse)
            }
    }
}