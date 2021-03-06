package com.zf.mart.ui.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.zf.mart.R
import com.zf.mart.mvp.bean.CommendList
import com.zf.mart.ui.activity.GoodsDetailActivity
import com.zf.mart.utils.GlideUtils
import kotlinx.android.synthetic.main.item_share_goods.view.*

class ShareGoodsAdapter(val context: Context?,val data:List<CommendList>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_share_goods, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = data.size

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
            holder.itemView.apply {
                //图片
                GlideUtils.loadUrlImage(context,"https://mobile.zhifengwangluo.c3w.cc"+data[position].original_img,goodsIcon)
                //名字
                goodsName.text=data[position].goods_name
                //价格
                shop_price.text=data[position].shop_price
                //分润
                commission_num.text=data[position].commission_num
            }
        holder.itemView.setOnClickListener {
            GoodsDetailActivity.actionStart(context,data[position].goods_id)
        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}