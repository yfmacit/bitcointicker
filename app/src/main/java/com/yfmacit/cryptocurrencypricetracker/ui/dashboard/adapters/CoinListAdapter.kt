package com.yfmacit.cryptocurrencypricetracker.ui.dashboard.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.yfmacit.cryptocurrencypricetracker.R
import com.yfmacit.cryptocurrencypricetracker.data.model.api.list.CoinListItem
import com.yfmacit.cryptocurrencypricetracker.databinding.ItemCoinBinding
import com.yfmacit.cryptocurrencypricetracker.ui.common.listeners.ListClickListener
import com.yfmacit.cryptocurrencypricetracker.ui.dashboard.coinlist.CoinListNavigator

class CoinListAdapter(var listener: ListClickListener
) : RecyclerView.Adapter<CoinListAdapter.ViewHolder>() {
    class ViewHolder(var itemBinding : ItemCoinBinding) : RecyclerView.ViewHolder(itemBinding.root)

    private var coinList: MutableList<CoinListItem> = mutableListOf()

    fun addItems(coinList: List<CoinListItem>) {
        this.coinList.clear()
        this.coinList.addAll(coinList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: ItemCoinBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context), R.layout.item_coin, parent, false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemBinding.coin = this.coinList[position]

        holder.itemBinding.root.setOnClickListener {
            listener.onClick(position)
        }
    }

    override fun getItemCount(): Int {
        return this.coinList.size
    }
}