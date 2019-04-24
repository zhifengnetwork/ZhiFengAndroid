package com.zf.mart.ui.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.zf.mart.R
import com.zf.mart.mvp.bean.GoodsAttrBean
import kotlinx.android.synthetic.main.item_order_info.view.*

class OrderInfoAdapter(val context: Context?, val data: GoodsAttrBean?) :
    RecyclerView.Adapter<OrderInfoAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_order_info, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        var i = 0
        if (data?.goods_attribute != null) i = data?.goods_attribute.size
        return i
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.itemView.apply {

            if (data?.goods_attribute != null && data.goods_attribute.isNotEmpty()) {
                attribute.text = data.goods_attribute[position].attr_name
            }
            if (data?.goods_attr_list != null && data.goods_attr_list.isNotEmpty()) {
                attr.text = data.goods_attr_list[position].attr_value
            }

        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

}