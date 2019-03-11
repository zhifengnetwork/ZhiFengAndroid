package com.zf.mart.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.zf.mart.R
import kotlinx.android.synthetic.main.classify_title_item.view.*

class ClassifyTitleAdapter(val context: Context?, val data: List<String>) :
    RecyclerView.Adapter<ClassifyTitleAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.classify_title_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = data.size

    private var selectedPos = 0

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.classify_item_title_name.text = data[position]

        holder.itemView.classify_item_title_name.isSelected = selectedPos == position
        holder.itemView.classify_item_title_view.isSelected = selectedPos == position

        holder.itemView.setOnClickListener {
            selectedPos = position
            notifyDataSetChanged()
        }

    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

}