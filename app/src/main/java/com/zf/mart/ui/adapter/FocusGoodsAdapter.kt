package com.zf.mart.ui.adapter

import android.content.Context
import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.zf.mart.R
import kotlinx.android.synthetic.main.item_focus_goods.view.*

class FocusGoodsAdapter(val context: Context?) : RecyclerView.Adapter<FocusGoodsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_focus_goods, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = 3

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.apply {
            /** 给价格添加中划线 */
            oldPrice.paint.flags = Paint.STRIKE_THRU_TEXT_FLAG
        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

}