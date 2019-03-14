package com.zf.mart.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.zf.mart.R
import kotlinx.android.synthetic.main.classify_right_item.view.*

class ClassifyRightAdapter(val context: Context?) : RecyclerView.Adapter<ClassifyRightAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.classify_right_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = 3

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.apply {
            val adapter = ClassifyRightconterAdapter(context)
            classify_left_item_cylv.layoutManager = GridLayoutManager(context,3)
            classify_left_item_cylv.adapter=adapter
        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

}