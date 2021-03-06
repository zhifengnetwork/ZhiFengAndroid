package com.zf.mart.ui.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.zf.mart.R
import com.zf.mart.mvp.bean.AttriBute
import com.zf.mart.mvp.bean.GoodsAttrBean
import kotlinx.android.synthetic.main.item_order_info.view.*

class OrderInfoAdapter(val context: Context?, val data: List<AttriBute>) :
    RecyclerView.Adapter<OrderInfoAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_order_info, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.itemView.apply {


            attribute.text = data[position].attr_name

            if (data[position].attr.isNotEmpty()) {
                attr.text = data[position].attr[0].attr_value
            }else{
                attr.text=""
            }

        }


    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

}