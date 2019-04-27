package com.zf.mart.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.zf.mart.R
import com.zf.mart.api.UriConstant
import com.zf.mart.mvp.bean.Goods
import com.zf.mart.utils.GlideUtils
import kotlinx.android.synthetic.main.item_en_order_goods.view.*


class EnGoodsAdapter(val context: Context?, val data: List<Goods>) :
        RecyclerView.Adapter<EnGoodsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_en_order_goods, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = data.size


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.apply {
            goodsName.text = data[position].goods_name
            GlideUtils.loadUrlImage(context, UriConstant.BASE_URL + data[position].original_img, goodsIcon)
            goodsName.text = data[position].goods_name
            goodsPrice.text = "¥${data[position].shop_price}"
        }
    }


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

}