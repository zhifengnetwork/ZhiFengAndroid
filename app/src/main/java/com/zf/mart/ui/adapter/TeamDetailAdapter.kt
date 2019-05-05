package com.zf.mart.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.zf.mart.R
import com.zf.mart.mvp.bean.MemberOrderList
import com.zf.mart.utils.TimeUtils
import kotlinx.android.synthetic.main.item_team_detail.view.*

class TeamDetailAdapter(val context: Context, val mData: List<MemberOrderList>) :
    RecyclerView.Adapter<TeamDetailAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_team_detail, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = mData.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.apply {
            user_name.text = mData[position].consignee
            order_time.text = TimeUtils.getYmd(mData[position].add_time)
            order_money.text=mData[position].total_amount
        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

}