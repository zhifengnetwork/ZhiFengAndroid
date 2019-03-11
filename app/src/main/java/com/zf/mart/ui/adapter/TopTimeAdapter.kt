package com.zf.mart.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.zf.mart.R
import kotlinx.android.synthetic.main.item_top_time.view.*

class TopTimeAdapter(val context: Context?) : RecyclerView.Adapter<TopTimeAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_top_time, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = 10

    private var selectedPos = 0

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.time.isSelected = selectedPos == position
        holder.itemView.state.isSelected = selectedPos == position
        holder.itemView.linearLayout.isSelected = selectedPos == position
        holder.itemView.setOnClickListener {
            selectedPos = position
            notifyDataSetChanged()
        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

}