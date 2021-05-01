package com.yfmacit.cryptocurrencypricetracker.ui.dashboard.favourite

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.yfmacit.cryptocurrencypricetracker.BR
import com.yfmacit.cryptocurrencypricetracker.R
import com.yfmacit.cryptocurrencypricetracker.databinding.FragmentFavouriteBinding
import com.yfmacit.cryptocurrencypricetracker.ui.base.BaseDelegate
import com.yfmacit.cryptocurrencypricetracker.ui.base.BaseFragment
import com.yfmacit.cryptocurrencypricetracker.ui.common.listeners.ListClickListener
import com.yfmacit.cryptocurrencypricetracker.ui.dashboard.adapters.CoinListAdapter
import com.yfmacit.cryptocurrencypricetracker.ui.dashboard.coindetail.CoinDetailFragment
import javax.inject.Inject

class FavouriteFragment : BaseFragment<FragmentFavouriteBinding, FavouriteViewModel, BaseDelegate>(),
    FavouriteNavigator{
    @Inject
    internal lateinit var mViewModel: FavouriteViewModel
    override val bindingVariable: Int
        get() = BR.viewModel
    override val layoutId: Int
        get() = R.layout.fragment_favourite
    override val viewModel: FavouriteViewModel
        get() { return mViewModel }

    private var mBinding: FragmentFavouriteBinding? = null
    private lateinit var coinListAdapter: CoinListAdapter

    companion object{
        fun newInstance(): FavouriteFragment {
            return FavouriteFragment()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.viewModel.navigator = this
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        this.mBinding = super.viewDataBinding
        initViews()
    }

    override fun onResume() {
        super.onResume()
        if (isAdded) {
            viewModel.fetchFavouriteCoins(null)
        }
    }

    private fun initViews() {
        viewModel.dataManager.favoriteCoinList.observe(this, Observer {
            coinList ->
            if (coinList.isEmpty()) {
                viewModel.isEmptyList.set(true)
            } else {
                viewModel.isEmptyList.set(false)
                coinListAdapter.addItems(coinList)
            }
        })

        coinListAdapter = CoinListAdapter(object : ListClickListener {
            override fun onClick(position: Int) {
                openCoinDetail(viewModel.dataManager.favoriteCoinList.value?.get(position)?.id!!)
            }
        })

        viewDataBinding?.recyclerViewCoinList?.setHasFixedSize(true)
        viewDataBinding?.recyclerViewCoinList?.layoutManager = LinearLayoutManager(baseActivity)
        viewDataBinding?.recyclerViewCoinList?.adapter = coinListAdapter

        viewDataBinding?.swipeRefreshLayoutFavourite?.setOnRefreshListener {
            viewModel.fetchFavouriteCoins(swipeHandler = {
                viewDataBinding?.swipeRefreshLayoutFavourite?.isRefreshing = false
            })
        }
    }

    fun openCoinDetail(coinId: String) {
        mFragmentNavigation.pushFragment(CoinDetailFragment.newInstance(coinId))
    }

    override fun logout() {
        delegate?.logout()
    }
}