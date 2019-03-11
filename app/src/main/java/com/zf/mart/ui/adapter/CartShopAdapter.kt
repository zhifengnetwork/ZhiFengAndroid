package com.zf.mart.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.zf.mart.R
import kotlinx.android.synthetic.main.item_cart_shop.view.*

/**
 * 店铺和商品
 */
class CartShopAdapter(val context: Context?) : RecyclerView.Adapter<CartShopAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_cart_shop, parent, false)
        return ViewHolder(view)
    }

    /** 购物车商品 的列表 */
    private val adapter by lazy { CartGoodsAdapter(context) }

    override fun getItemCount(): Int = 5

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.apply {
            //商品
            recyclerView.layoutManager = LinearLayoutManager(context)
            recyclerView.adapter = adapter
        }

    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

}