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
import com.zf.mart.utils.LogUtils
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


    private var isCheckAll = false

    private val checkList = ArrayList<Int>()


    //输入数量
    var onInputListener: ((num: Int) -> Unit)? = null
    //规格
    var onSpecListener: ((spec: String) -> Unit)? = null
    var onAddCheck: ((id: Int) -> Unit)? = null
    var onRemoveCheck: ((id: Int) -> Unit)? = null
    var onCheckAll: (() -> Unit)? = null
    var onUnCheckAll: (() -> Unit)? = null

    fun ifCheckAll(ifCheck: Boolean) {

        checkList.clear()

        isCheckAll = ifCheck
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.apply {

            goodsSpec.setOnClickListener {
                //弹出规格选择框
                onSpecListener?.invoke(goodsSpec.text.toString())
            }

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

            goodsName.text = data[position].goodsName + "--id:--" + data[position].id

            checkBox.isChecked = isCheckAll

            initCheck(this, position)


            checkBox.setOnClickListener {
                initCheck(this, position)
                //外层商铺是否选中
                if (checkList.size == data.size) {
                    onCheckAll?.invoke()
                } else {
                    onUnCheckAll?.invoke()
                }
            }
        }
    }

    private fun initCheck(view: View, position: Int) {
        view.apply {
            if (checkBox.isChecked) {
                checkList.add(data[position].id)
                onAddCheck?.invoke(data[position].id)
            } else {
                checkList.remove(data[position].id)
                onRemoveCheck?.invoke(data[position].id)
            }

        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

}