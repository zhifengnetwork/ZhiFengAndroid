package com.zf.mart.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
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


    private var mListener: OnItemClickListener? = null

    fun setOnItemClickListener(listener: OnItemClickListener) {
        mListener = listener
    }

    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.classify_item_title_name.text = data[position]
        holder.itemView.titleback.isSelected=selectedPos == position
//        holder.itemView.classify_item_title_name.isSelected = selectedPos == position
        holder.itemView.classify_item_title_view.isSelected = selectedPos == position

        holder.itemView.setOnClickListener {
            //点击选中时显示样式
            mListener?.onItemClick(position)
            selectedPos = position
            notifyDataSetChanged()

        }

    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

}