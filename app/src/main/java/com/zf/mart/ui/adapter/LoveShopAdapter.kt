package com.zf.mart.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.scwang.smartrefresh.layout.util.DensityUtil
import com.zf.mart.R
import com.zf.mart.view.recyclerview.RecyclerViewDivider
import kotlinx.android.synthetic.main.item_love_shop.view.*

class LoveShopAdapter(val context: Context?) : RecyclerView.Adapter<LoveShopAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_love_shop, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = 5

    private val adapter by lazy { LoveShopGoodsAdapter(context) }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.apply {
            /** 关注店铺用红色背景  进入店铺用浅红背景 */
            if (position == 1) {
                operation.text = "关注店铺"
                context?.apply {
                    operation.setTextColor(ContextCompat.getColor(this, R.color.whit))
                    operation.background = ContextCompat.getDrawable(this, R.drawable.shape_focus)
                }
            } else {
                operation.text = "进入店铺"
                context?.apply {
                    operation.setTextColor(ContextCompat.getColor(this, R.color.colorPrice))
                    operation.background = ContextCompat.getDrawable(this, R.drawable.shape_focus_in)
                }
            }

            /** 猜你喜欢的商家的商品 */
            val manager = LinearLayoutManager(context)
            manager.orientation = LinearLayoutManager.HORIZONTAL
            recyclerView.layoutManager = manager
            recyclerView.adapter = adapter

        }

    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

}