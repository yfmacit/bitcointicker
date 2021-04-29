package com.yfmacit.cryptocurrencypricetracker.ui.splash

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.yfmacit.cryptocurrencypricetracker.R
import com.yfmacit.cryptocurrencypricetracker.databinding.ActivitySplashBinding
import com.yfmacit.cryptocurrencypricetracker.ui.base.BaseActivity
import com.yfmacit.cryptocurrencypricetracker.BR
import javax.inject.Inject

class SplashActivity : BaseActivity<ActivitySplashBinding, SplashViewModel>(), SplashNavigator{
    private var mSplashViewModel: SplashViewModel? = null
    @Inject
    override lateinit var viewModel: SplashViewModel
        internal set
    override val bindingVariable: Int
        get() = BR.viewModel
    override val layoutId: Int
        get() = R.layout.activity_splash

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mSplashViewModel?.navigator = this
    }

    companion object {
        fun newIntent(context: Context): Intent {
            return Intent(context, SplashActivity::class.java)
        }
    }
}