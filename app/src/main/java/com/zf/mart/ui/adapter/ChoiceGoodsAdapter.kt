package com.zf.mart.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.zf.mart.R

class ChoiceGoodsAdapter (val context: Context?): RecyclerView.Adapter<ChoiceGoodsAdapter.ViewHolder>() {




    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChoiceGoodsAdapter.ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_choice_apps , parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int =6


    override fun onBindViewHolder(holder: ChoiceGoodsAdapter.ViewHolder, position: Int) {
        //        item 选定监听（OnItemSelectedListener）
//        item 点击监听（OnItemClickListener）
//        item 长按监听（OnItemLongClickListener）
//        遥控器其他按键监听（OnItemKeyListener）
//        holder.itemView.setFocusable(true)
//        holder.itemView.setTag(position)
//        holder.itemView.onFocusChangeListener = View.OnFocusChangeListener { v, hasFocus ->
//            if(hasFocus){
//
//            }
//        }
     holder.itemView.setOnClickListener {
         when(position){
             0 -> Toast.makeText(context,"点击了xxx"+position, Toast.LENGTH_SHORT).show()
             1 -> Toast.makeText(context,"点击了xxx"+position, Toast.LENGTH_SHORT).show()
             2 -> Toast.makeText(context,"点击了xxx"+position, Toast.LENGTH_SHORT).show()
         }
     }

    }
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}