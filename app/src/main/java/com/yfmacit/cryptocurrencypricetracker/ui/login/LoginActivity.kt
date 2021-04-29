package com.yfmacit.cryptocurrencypricetracker.ui.login

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.yfmacit.cryptocurrencypricetracker.databinding.ActivityLoginBinding
import com.yfmacit.cryptocurrencypricetracker.ui.base.BaseActivity
import com.yfmacit.cryptocurrencypricetracker.BR
import com.yfmacit.cryptocurrencypricetracker.R
import javax.inject.Inject

class LoginActivity : BaseActivity<ActivityLoginBinding, LoginViewModel>(), LoginNavigator {
    private var mLoginViewModel: LoginViewModel? = null
    @Inject
    override lateinit var viewModel: LoginViewModel
        internal set
    override val bindingVariable: Int
        get() = BR.viewModel
    override val layoutId: Int
        get() = R.layout.activity_login

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mLoginViewModel?.navigator = this
    }

    companion object {
        fun newIntent(context: Context): Intent {
            return Intent(context, LoginActivity::class.java)
        }
    }
}