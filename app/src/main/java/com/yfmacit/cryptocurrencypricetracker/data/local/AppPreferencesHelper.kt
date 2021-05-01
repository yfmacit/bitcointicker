package com.yfmacit.cryptocurrencypricetracker.data.local

import android.content.Context
import android.content.SharedPreferences
import com.yfmacit.cryptocurrencypricetracker.di.PreferenceInfo
import javax.inject.Inject

class AppPreferencesHelper @Inject
constructor(context: Context, @PreferenceInfo prefFileName: String) : PreferencesHelper {

    private var mPrefs: SharedPreferences = context.getSharedPreferences(prefFileName, Context.MODE_PRIVATE)

    companion object {
        private const val PREF_KEY_USER_ID = "PREF_KEY_USER_ID"
        private const val PREF_KEY_USER_EMAIL = "PREF_KEY_USER_EMAIL"
    }

    override var userId: String?
        get() = mPrefs.getString(PREF_KEY_USER_ID, null)
        set(userId) = mPrefs.edit().putString(PREF_KEY_USER_ID, userId).apply()

    override var email: String?
        get() = mPrefs.getString(PREF_KEY_USER_EMAIL, null)
        set(email) = mPrefs.edit().putString(PREF_KEY_USER_EMAIL, email).apply()

    override fun clearAllData() {
        mPrefs.edit().clear().apply()
    }
}