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
import com.zf.mart.utils.LogUtils
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
            adapter.setOnItemClickListener(object : CartGoodsAdapter.OnItemClickListener {
                override fun addCheck(id: Int) {
                    //去重
                    mCheckList = filter()
                    mCheckList.add(id)
                }

                override fun removeCheck(id: Int) {
                    mCheckList = filter()
                    mCheckList.remove(id)
                }

                override fun checkAll() {
                    checkBox.isChecked = true
                }

                override fun unCheckAll() {
                    checkBox.isChecked = false
                }
            })

            //是否全选
            checkBox.isChecked = mIfCheckAll
            adapter.ifCheckAll(checkBox.isChecked)

            checkBox.setOnClickListener {
                adapter.ifCheckAll(checkBox.isChecked)
            }
        }

    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

}