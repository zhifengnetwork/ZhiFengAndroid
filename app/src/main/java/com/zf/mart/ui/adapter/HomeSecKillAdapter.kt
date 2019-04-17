package com.zf.mart.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.zf.mart.R
import com.zf.mart.ui.activity.SecKillDetailActivity

class HomeSecKillAdapter(val context: Context?) : RecyclerView.Adapter<HomeSecKillAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.home_item_seckill, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = 10

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.apply {
            setOnClickListener {
                SecKillDetailActivity.actionStart(context,"11")
            }
        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

}