package com.zf.mart.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.zf.mart.R
import com.zf.mart.api.UriConstant
import com.zf.mart.mvp.bean.AuctionList
import com.zf.mart.utils.GlideUtils
import com.zf.mart.utils.TimeUtils
import kotlinx.android.synthetic.main.item_auction.view.*

class AuctionAdapter(val context: Context?, val data: List<AuctionList>) :
        RecyclerView.Adapter<AuctionAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_auction, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = data.size

    var mClickListener: ((String) -> Unit)? = null

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.apply {

            startTime.text = "${TimeUtils.auctionTime(data[position].start_time)}准时开拍"
            goodsName.text = data[position].goods_name
            price.text = "¥${data[position].start_price}"
            GlideUtils.loadUrlImage(context, UriConstant.BASE_URL + data[position].original_img, goodsIcon)

            goAuction.setOnClickListener {
                mClickListener?.invoke(data[position].id)
            }
        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

}