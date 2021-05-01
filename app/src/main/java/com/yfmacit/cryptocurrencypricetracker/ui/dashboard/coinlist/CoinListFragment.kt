package com.yfmacit.cryptocurrencypricetracker.ui.dashboard.coinlist

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.yfmacit.cryptocurrencypricetracker.BR
import com.yfmacit.cryptocurrencypricetracker.R
import com.yfmacit.cryptocurrencypricetracker.databinding.FragmentCoinListBinding
import com.yfmacit.cryptocurrencypricetracker.ui.base.BaseDelegate
import com.yfmacit.cryptocurrencypricetracker.ui.base.BaseFragment
import com.yfmacit.cryptocurrencypricetracker.ui.common.listeners.ListClickListener
import com.yfmacit.cryptocurrencypricetracker.ui.dashboard.coindetail.CoinDetailFragment
import com.yfmacit.cryptocurrencypricetracker.ui.dashboard.adapters.CoinListAdapter
import com.yfmacit.cryptocurrencypricetracker.utils.AppLogger
import java.util.*
import javax.inject.Inject

class CoinListFragment : BaseFragment<FragmentCoinListBinding, CoinListViewModel, BaseDelegate>(), CoinListNavigator {
    @Inject
    internal lateinit var mViewModel: CoinListViewModel
    override val bindingVariable: Int
        get() = BR.viewModel
    override val layoutId: Int
        get() = R.layout.fragment_coin_list
    override val viewModel: CoinListViewModel
        get() { return mViewModel }

    private var mBinding: FragmentCoinListBinding? = null
    private lateinit var coinListAdapter: CoinListAdapter

    companion object {
        fun newInstance(): CoinListFragment {
            return CoinListFragment()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.viewModel.navigator = this
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        this.mBinding = super.viewDataBinding
        viewModel.fetchCoinList(null)
        initViews()
    }

    private fun initViews() {
        viewDataBinding?.textInputEditTextSearch?.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (count > 0) {
                    val search = s.toString().toLowerCase(Locale.ENGLISH)
                    viewModel.filteredCoinList.value = viewModel.coinList.value?.filter { coin ->
                        coin.name!!.toLowerCase(Locale.ENGLISH).contains(search)
                                || coin.symbol!!.toLowerCase(Locale.ENGLISH).contains(search)
                    }?.toMutableList()
                    AppLogger.d(CoinListFragment::class.java, "asdasd")
                } else {
                    viewModel.filteredCoinList.value = viewModel.coinList.value
                }
            }

            override fun afterTextChanged(s: Editable?) {

            }
        })

        viewModel.coinList.observe(this, Observer {
                list -> viewModel.filteredCoinList.value = list
        })

        viewModel.filteredCoinList.observe(this, Observer {
            filteredList -> coinListAdapter.addItems(filteredList)
        })

        coinListAdapter = CoinListAdapter(object : ListClickListener {
            override fun onClick(position: Int) {
                openCoinDetail(viewModel.filteredCoinList.value?.get(position)?.id!!)
            }
        })

        viewDataBinding?.recyclerViewCoinList?.setHasFixedSize(true)
        viewDataBinding?.recyclerViewCoinList?.layoutManager = LinearLayoutManager(baseActivity)
        viewDataBinding?.recyclerViewCoinList?.adapter = coinListAdapter

        viewDataBinding?.swipeRefreshLayoutCoinList?.setOnRefreshListener {
            viewModel.fetchCoinList(
                swipeHandler = {
                    viewDataBinding?.swipeRefreshLayoutCoinList?.isRefreshing = false
                }
            )
        }
    }

    override fun openCoinDetail(coinId: String) {
        mFragmentNavigation.pushFragment(CoinDetailFragment.newInstance(coinId))
    }
}