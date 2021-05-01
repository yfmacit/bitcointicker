package com.yfmacit.cryptocurrencypricetracker.ui.login

import androidx.databinding.ObservableField
import com.yfmacit.cryptocurrencypricetracker.data.DataManager
import com.yfmacit.cryptocurrencypricetracker.data.model.app.enums.AppEnums
import com.yfmacit.cryptocurrencypricetracker.ui.base.BaseViewModel
import com.yfmacit.cryptocurrencypricetracker.utils.extensions.validateEmailAddress
import com.yfmacit.cryptocurrencypricetracker.utils.extensions.validatePassword
import com.yfmacit.cryptocurrencypricetracker.utils.rx.SchedulerProvider

class LoginViewModel(dataManager: DataManager,
                     schedulerProvider: SchedulerProvider
) : BaseViewModel<LoginNavigator>(dataManager, schedulerProvider) {
    var email: ObservableField<String> = ObservableField()
    var password: ObservableField<String> = ObservableField()

    fun onLoginClicked() {
        if (!email.get().validateEmailAddress()) {
            navigator.showMessage(AppEnums.MessageStatus.ERROR, "Hata", "Geçerli mail adresi giriniz", null, null)
        } else if (!password.get().validatePassword()) {
            navigator.showMessage(AppEnums.MessageStatus.ERROR, "başlık", "Geçerli bir şifre giriniz", null, null)
        } else {
            navigator.showLoading()
            dataManager.authUser(
                email = email.get()!!,
                password = password.get()!!,
                handler = { response ->
                    navigator.hideLoading()

                    if (response.isSuccessful) {
                        dataManager.userId = response.userId
                        dataManager.email = response.email
                        navigator.onSuccessLogin()
                    } else {
                        navigator.showMessage(AppEnums.MessageStatus.ERROR, "Error",
                            "Authentication failed.", null, null)
                    }
                }
            )
        }
    }
}