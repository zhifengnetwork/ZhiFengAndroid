package com.zf.mart.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.zf.mart.R
import com.zf.mart.mvp.bean.OrderListBean
import com.zf.mart.ui.activity.OrderDetailActivity
import kotlinx.android.synthetic.main.item_myorder.view.*

class MyOrderAdapter(val context: Context?, val data: List<OrderListBean>) :
    RecyclerView.Adapter<MyOrderAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_myorder, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.apply {

            goodsName.text = data[position].goods_name
            addTime.text = data[position].add_time
            shopName.text = data[position].seller_name

            setOnClickListener {
                OrderDetailActivity.actionStart(context)
            }

        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

}