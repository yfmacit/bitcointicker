package com.yfmacit.cryptocurrencypricetracker.ui.dashboard

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.ncapdevi.fragnav.FragNavController
import com.ncapdevi.fragnav.FragNavLogger
import com.ncapdevi.fragnav.FragNavSwitchController
import com.ncapdevi.fragnav.FragNavTransactionOptions
import com.ncapdevi.fragnav.tabhistory.UnlimitedTabHistoryStrategy
import com.yfmacit.cryptocurrencypricetracker.BR
import com.yfmacit.cryptocurrencypricetracker.R
import com.yfmacit.cryptocurrencypricetracker.data.model.app.constants.AppConstants.Companion.LOGIN_FAILED_RESULT_CODE
import com.yfmacit.cryptocurrencypricetracker.data.model.app.constants.AppConstants.Companion.TAB_DASHBOARD
import com.yfmacit.cryptocurrencypricetracker.data.model.app.constants.AppConstants.Companion.TAB_FAVOURITE
import com.yfmacit.cryptocurrencypricetracker.data.model.app.constants.AppConstants.Companion.LOGIN_REQUEST_CODE_IN_DASHBOARD
import com.yfmacit.cryptocurrencypricetracker.data.model.app.constants.AppConstants.Companion.LOGIN_SUCCESS_RESULT_CODE
import com.yfmacit.cryptocurrencypricetracker.databinding.ActivityDashboardBinding
import com.yfmacit.cryptocurrencypricetracker.ui.base.BaseActivity
import com.yfmacit.cryptocurrencypricetracker.ui.base.BaseDelegate
import com.yfmacit.cryptocurrencypricetracker.ui.base.BaseFragment
import com.yfmacit.cryptocurrencypricetracker.ui.dashboard.coinlist.CoinListFragment
import com.yfmacit.cryptocurrencypricetracker.ui.dashboard.favourite.FavouriteFragment
import com.yfmacit.cryptocurrencypricetracker.ui.login.LoginActivity
import com.yfmacit.cryptocurrencypricetracker.utils.AppLogger
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import javax.inject.Inject

class DashboardActivity : BaseActivity<ActivityDashboardBinding, DashboardViewModel>(),
    HasSupportFragmentInjector, DashboardNavigator, BaseDelegate, FragNavController.RootFragmentListener,
    FragNavController.TransactionListener, BottomNavigationView.OnNavigationItemSelectedListener,
    BaseFragment.FragmentNavigation{

    @Inject
    lateinit var fragmentDispatchingAndroidInjector: DispatchingAndroidInjector<Fragment>
    @Inject
    lateinit var mViewModelFactory: ViewModelProvider.Factory
    override val viewModel: DashboardViewModel
        get() {
            return ViewModelProviders.of(this, mViewModelFactory)
                .get(DashboardViewModel::class.java)
        }
    override val bindingVariable: Int
        get() = BR.viewModel
    override val layoutId: Int
        get() = R.layout.activity_dashboard

    private var mActivityDashboardBinding: ActivityDashboardBinding? = null
    private val fragNavController: FragNavController = FragNavController(supportFragmentManager,
        R.id.frameLayoutContainer)

    companion object {
        fun newIntent(context: Context): Intent {
            return Intent(context, DashboardActivity::class.java)
        }
    }

    lateinit var bottomNavigationView: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mActivityDashboardBinding = super.dataBinding
        viewModel.navigator = this

        mActivityDashboardBinding?.bottomNavigationView?.setOnNavigationItemSelectedListener(this)

        fragNavController.apply {
            transactionListener = this@DashboardActivity
            rootFragmentListener = this@DashboardActivity


            createEager = false
            fragNavLogger = object : FragNavLogger {
                override fun error(message: String, throwable: Throwable) {
                    AppLogger.d(DashboardActivity::class.java, message)
                }
            }

            defaultTransactionOptions = FragNavTransactionOptions
                .newBuilder()
                .allowStateLoss(true)
                .customAnimations(
                    R.anim.slide_in_from_right,
                    R.anim.slide_out_to_left,
                    R.anim.slide_in_from_left,
                    R.anim.slide_out_to_right
                ).build()

            fragmentHideStrategy = FragNavController.HIDE

            navigationStrategy = UnlimitedTabHistoryStrategy(object : FragNavSwitchController {
                override fun switchTab(index: Int, transactionOptions: FragNavTransactionOptions?) {
                    when (index) {
                        TAB_DASHBOARD -> {
                            mActivityDashboardBinding?.bottomNavigationView?.selectedItemId = R.id.navigationHome
                        }
                        TAB_FAVOURITE -> {
                            if (viewModel.dataManager.userIsLoggedIn())
                                mActivityDashboardBinding?.bottomNavigationView?.selectedItemId = R.id.navigationHome
                        }
                    }
                }
            })
        }

        fragNavController.initialize(TAB_DASHBOARD, savedInstanceState)
        if (savedInstanceState == null)
            mActivityDashboardBinding?.bottomNavigationView?.selectedItemId = R.id.navigationHome
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == LOGIN_REQUEST_CODE_IN_DASHBOARD &&
                resultCode == LOGIN_SUCCESS_RESULT_CODE) {
            clickedTab(TAB_FAVOURITE)
        } else if (requestCode == LOGIN_REQUEST_CODE_IN_DASHBOARD &&
            resultCode == LOGIN_FAILED_RESULT_CODE) {
            mActivityDashboardBinding?.bottomNavigationView?.selectedItemId = R.id.navigationHome
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }

    override fun supportFragmentInjector(): DispatchingAndroidInjector<Fragment>? {
        return fragmentDispatchingAndroidInjector
    }

    override fun onNavigationItemSelected(menu: MenuItem): Boolean {
        var selectedTabPosition = -1
        when (menu.itemId) {
            R.id.navigationHome -> {
                selectedTabPosition = 0
                if (fragNavController.currentStackIndex == selectedTabPosition &&
                    fragNavController.currentFrag !is CoinListFragment) {
                    do {
                        fragNavController.popFragment()
                    } while (fragNavController.currentFrag !is CoinListFragment)
                }
            }
            R.id.navigationFavourite -> {
                selectedTabPosition = 1
                if (fragNavController.currentStackIndex == selectedTabPosition &&
                    fragNavController.currentFrag !is FavouriteFragment) {
                    do {
                        fragNavController.popFragment()
                    } while (fragNavController.currentFrag !is FavouriteFragment)
                }
            }
        }

        this.clickedTab(selectedTabPosition)

        return true
    }

    private fun clickedTab(position: Int) {
        when (position) {
            TAB_DASHBOARD -> {
                fragNavController.switchTab(position)
            }
            TAB_FAVOURITE -> {
                if (dataManager.userId == null) {
                    startActivityForResult(Intent(
                        LoginActivity.newIntent(this)),
                        LOGIN_REQUEST_CODE_IN_DASHBOARD
                    )
                } else {
                    fragNavController.switchTab(position)
                }
            }
        }
    }

    override val numberOfRootFragments: Int = 2

    override fun getRootFragment(index: Int): Fragment {
        when (index) {
            TAB_DASHBOARD -> return CoinListFragment.newInstance()
            TAB_FAVOURITE -> return FavouriteFragment.newInstance()
        }
        throw IllegalStateException("Need to send an index that we know")
    }

    override fun onFragmentTransaction(
        fragment: Fragment?,
        transactionType: FragNavController.TransactionType
    ) {
        fragment?.onResume()
    }

    override fun onTabTransaction(fragment: Fragment?, index: Int) {
        fragment?.onResume()
    }

    override fun logout() {
        fragNavController.clearStack(TAB_FAVOURITE)
        onBackPressed()
    }

    override fun onBackPressed() {
        if (viewModel.loaderState.get())
            return

        if (fragNavController.popFragment().not())
            super.onBackPressed()
    }

    override fun pushFragment(fragment: Fragment) {
        fragNavController.pushFragment(fragment)
    }

    override fun popFragment(depth: Int) {
        fragNavController.popFragments(depth)
    }

    override fun showLoading() {
        viewModel.loaderState.set(true)
        dataBinding?.loader?.visibility = View.VISIBLE
    }

    override fun hideLoading() {
        viewModel.loaderState.set(false)
        dataBinding?.loader?.visibility = View.GONE
    }
}