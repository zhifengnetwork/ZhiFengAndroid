package com.zf.mart.ui.adapter

import android.content.Context
import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.zf.mart.R
import com.zf.mart.mvp.bean.MyFollowBean
import com.zf.mart.ui.activity.SameGoodsActivity
import com.zf.mart.utils.GlideUtils
import kotlinx.android.synthetic.main.item_focus_goods.view.*
import kotlinx.android.synthetic.main.item_focus_goods_main.view.*

class FocusGoodsAdapter(val context: Context?, val data: List<MyFollowBean>) :
    RecyclerView.Adapter<FocusGoodsAdapter.ViewHolder>() {
    private val mData = data
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_focus_goods_main, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = mData.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.itemView.apply {
            /** 给价格添加中划线 */
            oldPrice.paint.flags = Paint.STRIKE_THRU_TEXT_FLAG
            //商品价格
            goodsPrice.text= mData[position].shop_price
            //商品的名字
            goodsName.text=mData[position].goods_name
            //商品的图片
            GlideUtils.loadUrlImage(context,mData[position].original_img,goodsIcon)
            //商品的规格

            same.setOnClickListener {
                SameGoodsActivity.actionStart(context)
            }

            delete.setOnClickListener {
                Toast.makeText(context, "delete：$position", Toast.LENGTH_SHORT).show()
            }

        }
    }


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

}