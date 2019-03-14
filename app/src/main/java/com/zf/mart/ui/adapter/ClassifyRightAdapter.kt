package com.zf.mart.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.zf.mart.R


class ClassifyRightAdapter(val context: Context?) : RecyclerView.Adapter<ClassifyRightAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.classify_content_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = 15

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

}

//class ClassifyRightAdapter(val context: Context?) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
//
//
//    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
//
//    }
//
//    override fun getItemViewType(position: Int): Int {
//
//        return super.getItemViewType(position)
//    }
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
////        val view = LayoutInflater.from(context).inflate(R.layout.classify_content_item, parent, false)
//
////        return ViewHolder(view)
//
//        return when (viewType) {
//            0 -> {
//                ViewHolder(LayoutInflater.from(context).inflate(R.layout.classify_content_item, parent, false))
//            }
//            1 -> {
//                HeadHolder(LayoutInflater.from(context).inflate(R.layout.classify_content_item, parent, false))
//            }
//            else -> {
//                ViewHolder(LayoutInflater.from(context).inflate(R.layout.classify_content_item, parent, false))
//            }
//        }
//
//    }
//
//    override fun getItemCount(): Int = 15
//
////    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
////    }
//
//    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
//
//    class HeadHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
//
//}
