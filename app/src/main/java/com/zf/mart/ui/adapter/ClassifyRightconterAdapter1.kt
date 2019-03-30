package com.zf.mart.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.zf.mart.R




class ClassifyRightconterAdapter1(val context: Context?) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):RecyclerView.ViewHolder {

        val view = LayoutInflater.from(context).inflate(R.layout.classify_right_shop_item1, parent, false)
        return oneViewHolder(view)


    }

     override fun getItemCount(): Int=6

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        holder.itemView.setOnClickListener {
            Toast.makeText(context,"点击了,里面"+position+"--", Toast.LENGTH_SHORT).show()
        }
    }

    class oneViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)


}


