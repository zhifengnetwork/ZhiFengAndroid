package com.zf.mart.ui.adapter

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
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
class CartGoodsAdapter1(val context: Context?, val data: ArrayList<CartGoodsList>) :
    RecyclerView.Adapter<CartGoodsAdapter1.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_cart_goods, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = data.size

    var checkGoodsListener: ((ArrayList<CartGoodsList>) -> Unit)? = null

    //输入数量
    var onInputListener: ((num: Int) -> Unit)? = null
    //规格
    var onSpecListener: ((spec: String) -> Unit)? = null

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.apply {

            goodsName.text = data[position].goodsName + "   " + data[position].id



            goodsSpec.setOnClickListener {
                //弹出规格选择框
                onSpecListener?.invoke(goodsSpec.text.toString())
            }


            /**
             * 商品数量
             */
            reduce.isSelected = numberInput.text.toString().toInt() < 2

            numberInput.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    reduce.isSelected = numberInput.text.toString().toInt() < 2
                }

                override fun afterTextChanged(s: Editable?) {
                }
            })

            //减少
            reduce.setOnClickListener {
                if (numberInput.text.toString().toInt() > 1) {
                    reduce.isSelected = false
                    numberInput.text = (numberInput.text.toString().toInt() - 1).toString()
                }
            }

            //增加
            increase.setOnClickListener {
                numberInput.text = (numberInput.text.toString().toInt() + 1).toString()
            }

            //输入数量
            numberInput.setOnClickListener {
                onInputListener?.invoke(numberInput.text.toString().toInt())
            }


            /**
             * checkBox逻辑
             */
            checkBox.setOnClickListener {
                data[position].ifCheck = checkBox.isChecked
                checkGoodsListener?.invoke(data)
            }

            checkBox.isChecked = data[position].ifCheck


        }
    }


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

}