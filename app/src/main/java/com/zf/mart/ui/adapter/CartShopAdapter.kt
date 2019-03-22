package com.zf.mart.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.zf.mart.R
import com.zf.mart.mvp.bean.CartGoodsList
import com.zf.mart.mvp.bean.ShopList
import kotlinx.android.synthetic.main.item_cart_shop.view.*

/**
 * 店铺和商品
 */
class CartShopAdapter(val context: Context?, val data: List<ShopList>) :
    RecyclerView.Adapter<CartShopAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_cart_shop, parent, false)
        return ViewHolder(view)
    }

    /** 购物车商品 的列表 */
    private var goodsList = ArrayList<CartGoodsList>()

    override fun getItemCount(): Int = data.size

    private var mIfCheckAll = false

    fun ifCheckAll(ifCheckAll: Boolean) {
        mCheckList.clear()
        mIfCheckAll = ifCheckAll
        notifyDataSetChanged()
    }

    var mCheckList = ArrayList<Int>()

    var onShopSpecListener: ((spec: String) -> Unit)? = null
    var onShopNumListener: ((num:Int) -> Unit)? = null

    private fun filter(): ArrayList<Int> {
        //去重
        val newList = ArrayList<Int>()
        val it = mCheckList.iterator()
        while (it.hasNext()) {
            val obj = it.next()
            if (!newList.contains(obj)) {
                newList.add(obj)
            }
        }
        return newList
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.apply {
            shopName.text = data[position].shopName
            goodsList = data[position].goodsList
            //商品
            val adapter = CartGoodsAdapter(context, goodsList)
            recyclerView.layoutManager = LinearLayoutManager(context)
            recyclerView.adapter = adapter

            adapter.apply {
                onCheckAll = {
                    checkBox.isChecked = true
                }
                onUnCheckAll = {
                    checkBox.isChecked = false
                }
                onRemoveCheck = {
                    mCheckList = filter()
                    mCheckList.remove(it)
                }
                onAddCheck = {
                    //去重
                    mCheckList = filter()
                    mCheckList.add(it)
                }
                onInputListener = {
                    onShopNumListener?.invoke(it)
                }
                onSpecListener = {
                    onShopSpecListener?.invoke(it)
                }
            }

            //是否全选
            checkBox.isChecked = mIfCheckAll
            adapter.ifCheckAll(checkBox.isChecked)

            checkBox.setOnClickListener {
                adapter.ifCheckAll(checkBox.isChecked)
            }

            //判断是否被全部选中


        }

    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

}