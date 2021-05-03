package com.yfmacit.cryptocurrencypricetracker.ui.dashboard.coindetail

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import com.yfmacit.cryptocurrencypricetracker.BR
import com.yfmacit.cryptocurrencypricetracker.R
import com.yfmacit.cryptocurrencypricetracker.data.model.app.constants.AppConstants
import com.yfmacit.cryptocurrencypricetracker.databinding.FragmentCoinDetailBinding
import com.yfmacit.cryptocurrencypricetracker.ui.base.BaseDelegate
import com.yfmacit.cryptocurrencypricetracker.ui.base.BaseFragment
import com.yfmacit.cryptocurrencypricetracker.ui.common.dialogs.CommonInputDialog
import com.yfmacit.cryptocurrencypricetracker.ui.login.LoginActivity
import com.yfmacit.cryptocurrencypricetracker.utils.AppLogger
import javax.inject.Inject

class CoinDetailFragment : BaseFragment<FragmentCoinDetailBinding, CoinDetailViewModel, BaseDelegate>(), CoinDetailNavigator{
    @Inject
    internal lateinit var mViewModel: CoinDetailViewModel
    override val bindingVariable: Int
        get() = BR.viewModel
    override val layoutId: Int
        get() = R.layout.fragment_coin_detail
    override val viewModel: CoinDetailViewModel
        get() {return mViewModel}

    private var mBinding: FragmentCoinDetailBinding? = null
    private lateinit var coinId: String
    private var simplePriceHandler: Handler = Handler(Looper.myLooper()!!)

    companion object {
        fun newInstance(coinId: String): CoinDetailFragment {
            val fragment = CoinDetailFragment()
            val args = Bundle()
            args.putString("coinId", coinId)

            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.viewModel.navigator = this

        if (arguments != null)
            this.coinId = arguments!!.getString("coinId")?:""

    }

    override fun onDestroy() {
        super.onDestroy()
        simplePriceHandler.removeCallbacks(simplePriceListener)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == AppConstants.LOGIN_REQUEST_CODE_IN_DETAIL &&
                resultCode == AppConstants.LOGIN_SUCCESS_RESULT_CODE) {
            viewModel.addOrRemoveFavouriteCoinClicked()
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        this.mBinding = super.viewDataBinding
        viewModel.fetchCoinDetail(coinId)
    }

    override fun setSimplePriceRefreshInterval(value: Int) {
        if (context != null) {
            CommonInputDialog.showInputDialog(context!!,
                context!!.resources.getString(R.string.dialog_set_price_refresh_message),
                value, object : CommonInputDialog.InputDialogInterface {
                    override fun setValue(value: Int) {
                        viewModel.coinSimplePriceRefreshIntervalSecond.set(value)
                        simplePriceListener.run()
                    }
                }
            )
        }
    }

    override fun refreshPrice() {
        simplePriceListener.run()
    }

    override fun openLogin() {
        startActivityForResult(Intent(LoginActivity.newIntent(context!!)),
            AppConstants.LOGIN_REQUEST_CODE_IN_DETAIL)
    }

    private var simplePriceListener: Runnable = object : Runnable {
        override fun run() {
            viewModel.fetchCoinSimplePrice()
            AppLogger.d(CoinDetailFragment::class.java, "interval Second: " + viewModel.coinSimplePriceRefreshIntervalSecond.get()!!)
            simplePriceHandler?.postDelayed(this, (viewModel.coinSimplePriceRefreshIntervalSecond.get()!! * 1000).toLong())
        }
    }
}