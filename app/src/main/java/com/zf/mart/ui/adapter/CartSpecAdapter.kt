package com.zf.mart.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.zf.mart.R
import com.zf.mart.mvp.bean.CartGoodsList
import com.zf.mart.mvp.bean.SpecCorrect
import com.zhy.view.flowlayout.FlowLayout
import com.zhy.view.flowlayout.TagAdapter
import kotlinx.android.synthetic.main.item_cart_spec.view.*

class CartSpecAdapter(val context: Context, val data: List<SpecCorrect>, private val cartGoods: CartGoodsList) : RecyclerView.Adapter<CartSpecAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_cart_spec, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.apply {
            name.text = data[position].name

            val history = ArrayList<String>()
            data[position].list.forEach {
                history.add(it.item)
            }
            hotLayout.adapter = object : TagAdapter<String>(history) {
                override fun getView(parent: FlowLayout?, position: Int, t: String?): View {
                    val tv: TextView = LayoutInflater.from(context).inflate(R.layout.layout_textview_style, hotLayout, false) as TextView
                    tv.text = t
                    return tv
                }
            }

            var choosePos: Int? = null
            val oldSpec = cartGoods.spec_key.split("_")
            repeat(data[position].list.size) {
                if (oldSpec.contains(data[position].list[it].id)) {
                    choosePos = it
                    data[position].chooseId = data[position].list[it].id
                }
            }
            //默认选中规格
            if (choosePos != null) {
                hotLayout.adapter.setSelectedList(setOf(choosePos))
            }

            hotLayout.setOnSelectListener {
                data[position].chooseId = if (it.toMutableList().isNotEmpty()) {
                    data[position].list[it.toMutableList()[0]].id
                } else ""
            }

        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

}