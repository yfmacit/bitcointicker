package com.yfmacit.cryptocurrencypricetracker.data.model.app.firestore

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class LoginResponse(
    var isSuccessful: Boolean = false,
    var userId: String? = null,
    var email: String? = null
) : Parcelable