package com.zf.mart.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.zf.mart.R
import com.zf.mart.mvp.bean.MyMemberBean
import kotlinx.android.synthetic.main.item_my_member.view.*

class MyMemberAdapter(val context: Context, val data: List<MyMemberBean>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_my_member, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        holder.itemView.apply {
            //用户ID
            user_id.text=data[position].user_id
            //用户名字
            user_name.text=data[position].nickname
            //点击查看按钮
            look_btn.setOnClickListener {

            }
        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}