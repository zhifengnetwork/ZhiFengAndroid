package com.zf.mart.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.zf.mart.R
import com.zf.mart.mvp.bean.CartGoodsList
import kotlinx.android.synthetic.main.item_cart_goods.view.*

/**
 * 购物车商品
 */
class CartGoodsAdapter(val context: Context?, val data: ArrayList<CartGoodsList>) :
    RecyclerView.Adapter<CartGoodsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_cart_goods, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = data.size


    private var mListener: OnItemClickListener? = null
    fun setOnItemClickListener(listener: OnItemClickListener) {
        mListener = listener
    }

    interface OnItemClickListener {
        fun checkAll()
        fun unCheckAll()
        fun addCheck(id: Int)
        fun removeCheck(id: Int)
    }

    private var isCheckAll = false

    private val checkList = ArrayList<Int>()


    fun ifCheckAll(ifCheck: Boolean) {

        checkList.clear()

        isCheckAll = ifCheck
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.apply {

            goodsName.text = data[position].goodsName
            checkBox.isChecked = isCheckAll

            initCheck(this, position)

            checkBox.setOnClickListener {
                initCheck(this, position)
                //外层商铺是否选中
                if (checkList.size == data.size) {
                    mListener?.checkAll()
                } else {
                    mListener?.unCheckAll()
                }
            }
        }
    }

    private fun initCheck(view: View, position: Int) {
        view.apply {
            if (checkBox.isChecked) {
                checkList.add(data[position].id)
                mListener?.addCheck(data[position].id)
            } else {
                checkList.remove(data[position].id)
                mListener?.removeCheck(data[position].id)
            }
        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

}