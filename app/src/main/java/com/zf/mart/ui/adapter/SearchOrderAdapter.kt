package com.zf.mart.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.zf.mart.R
import com.zf.mart.ui.activity.OrderDetailActivity
import com.zf.mart.utils.LogUtils
import kotlinx.android.synthetic.main.item_order_two.view.*
import kotlinx.android.synthetic.main.item_search_order.view.*

/**
 * 传入type  1 是1列排版
 *            2 是2列排版
 */
class SearchOrderAdapter(val context: Context, val data: List<String>) :
    RecyclerView.Adapter<SearchOrderAdapter.ViewHolder>() {

    private var mType = 1

    fun changeType(type: Int) {
        mType = type
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = if (mType == 1) {
            LayoutInflater.from(context).inflate(R.layout.item_search_order, parent, false)
        } else {
            LayoutInflater.from(context).inflate(R.layout.item_order_two, parent, false)
        }
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.apply {
            if (mType == 1) {
                goodsName.text = data[position]
            } else {
                goodsName2.text = data[position]
            }

            setOnClickListener {
                OrderDetailActivity.actionStart(context)
            }

        }

    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

}