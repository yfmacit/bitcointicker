package com.yfmacit.cryptocurrencypricetracker.ui.dashboard.coindetail

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import com.yfmacit.cryptocurrencypricetracker.BR
import com.yfmacit.cryptocurrencypricetracker.R
import com.yfmacit.cryptocurrencypricetracker.data.model.app.constants.AppConstants
import com.yfmacit.cryptocurrencypricetracker.databinding.FragmentCoinDetailBinding
import com.yfmacit.cryptocurrencypricetracker.ui.base.BaseDelegate
import com.yfmacit.cryptocurrencypricetracker.ui.base.BaseFragment
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

    override fun openLogin() {
        startActivityForResult(Intent(LoginActivity.newIntent(context!!)),
            AppConstants.LOGIN_REQUEST_CODE_IN_DETAIL)
    }

}