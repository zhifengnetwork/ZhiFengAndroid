package com.zf.mart.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.zf.mart.R




class ClassifyRightconterAdapter(val context: Context?) : RecyclerView.Adapter<ClassifyRightconterAdapter.ViewHolder>() {



    override fun getItemViewType(position: Int): Int {


        return super.getItemViewType(position)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {


        val view = LayoutInflater.from(context).inflate(com.zf.mart.R.layout.classify_right_shop_item1, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int=6

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}


