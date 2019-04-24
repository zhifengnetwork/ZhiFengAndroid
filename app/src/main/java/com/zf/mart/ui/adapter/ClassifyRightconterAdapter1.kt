package com.zf.mart.ui.adapter

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.zf.mart.R
import com.zf.mart.mvp.bean.ClassifyProductBean
import com.zf.mart.ui.activity.GoodsDetailActivity
import com.zf.mart.utils.GlideUtils
import kotlinx.android.synthetic.main.classify_right_shop_item1.view.*


class ClassifyRightconterAdapter1(val context: Context?,val data:ArrayList<ClassifyProductBean>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    val goods=data


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):RecyclerView.ViewHolder {

        val view = LayoutInflater.from(context).inflate(R.layout.classify_right_shop_item1, parent, false)
        return ViewHolder(view)


    }

     override fun getItemCount(): Int=goods.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        holder.itemView.apply {

            GlideUtils.loadUrlImage(context,goods[position].original_img,goods_img)

            goods_name.text=goods[position].goods_name
        }
        holder.itemView.setOnClickListener {
            GoodsDetailActivity.actionStart(context,goods[position].goods_id)
        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)


}


