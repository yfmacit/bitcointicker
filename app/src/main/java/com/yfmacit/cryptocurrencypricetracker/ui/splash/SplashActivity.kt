package com.yfmacit.cryptocurrencypricetracker.ui.splash

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

import com.yfmacit.cryptocurrencypricetracker.R
import com.yfmacit.cryptocurrencypricetracker.databinding.ActivitySplashBinding
import com.yfmacit.cryptocurrencypricetracker.ui.base.BaseActivity
import com.yfmacit.cryptocurrencypricetracker.BR
import com.yfmacit.cryptocurrencypricetracker.ui.base.BaseDelegate
import com.yfmacit.cryptocurrencypricetracker.ui.dashboard.DashboardActivity
import com.yfmacit.cryptocurrencypricetracker.ui.login.LoginActivity
import com.yfmacit.cryptocurrencypricetracker.utils.AppLogger
import javax.inject.Inject

class SplashActivity : BaseActivity<ActivitySplashBinding, SplashViewModel>(), BaseDelegate, SplashNavigator{

    @Inject
    lateinit var mViewModelFactory: ViewModelProvider.Factory

    override val viewModel: SplashViewModel
        get() {
            return ViewModelProviders.of(this, mViewModelFactory).get(SplashViewModel::class.java)
        }

    override val bindingVariable: Int
        get() = BR.viewModel

    override val layoutId: Int
        get() = R.layout.activity_splash

    companion object {
        fun newIntent(context: Context): Intent {
            return Intent(context, SplashActivity::class.java)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.navigator = this
        viewModel.checkUserIsLoggedIn()
    }

    override fun showLoading() {
    }

    override fun hideLoading() {
    }

    override fun openDashboard(withDelay: Boolean) {
        if (withDelay) {
            Handler(Looper.getMainLooper()).postDelayed({
                startActivity(Intent(DashboardActivity.newIntent(this)))
                finish()
            }, 2000)
        } else {
            startActivity(Intent(DashboardActivity.newIntent(this)))
            finish()
        }
     }
}