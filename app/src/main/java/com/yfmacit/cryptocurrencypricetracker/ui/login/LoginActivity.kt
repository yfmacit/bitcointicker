package com.yfmacit.cryptocurrencypricetracker.ui.login

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.yfmacit.cryptocurrencypricetracker.BR
import com.yfmacit.cryptocurrencypricetracker.R
import com.yfmacit.cryptocurrencypricetracker.data.model.app.constants.AppConstants
import com.yfmacit.cryptocurrencypricetracker.databinding.ActivityLoginBinding
import com.yfmacit.cryptocurrencypricetracker.ui.base.BaseActivity
import com.yfmacit.cryptocurrencypricetracker.ui.base.BaseDelegate
import javax.inject.Inject

class LoginActivity : BaseActivity<ActivityLoginBinding, LoginViewModel>(), BaseDelegate, LoginNavigator {
    @Inject
    lateinit var mViewModelFactory: ViewModelProvider.Factory
    override val viewModel: LoginViewModel
        get() {
            return ViewModelProviders.of(this, mViewModelFactory).get(LoginViewModel::class.java)
        }
    override val bindingVariable: Int
        get() = BR.viewModel
    override val layoutId: Int
        get() = R.layout.activity_login

    companion object {
        fun newIntent(context: Context): Intent {
            return Intent(context, LoginActivity::class.java)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.navigator = this
    }

    override fun onBackPressed() {
        setResult(AppConstants.LOGIN_FAILED_RESULT_CODE)
        super.onBackPressed()
    }

    override fun showLoading() {
        viewModel.loaderState.set(true)
        dataBinding?.loader?.visibility = View.VISIBLE
    }

    override fun hideLoading() {
        viewModel.loaderState.set(false)
        dataBinding?.loader?.visibility = View.GONE
    }

    override fun onSuccessLogin() {
        setResult(AppConstants.LOGIN_SUCCESS_RESULT_CODE)
        finish()
    }
}