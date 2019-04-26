package com.zf.mart.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.zf.mart.R
import com.zf.mart.mvp.bean.CartGoodsList
import kotlinx.android.synthetic.main.item_en_order_goods.view.*


class EnGoodsAdapter(val context: Context?, val data: List<CartGoodsList>?) :
        RecyclerView.Adapter<EnGoodsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_en_order_goods, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = data?.size ?:0


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.apply {

            data?.let {
                goodsName.text = data[position].goods.goods_name + "   " + data[position].id
            }


        }
    }


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

}