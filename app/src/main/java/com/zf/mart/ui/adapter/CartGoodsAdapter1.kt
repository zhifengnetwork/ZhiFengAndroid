package com.zf.mart.ui.adapter

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.zf.mart.R
import com.zf.mart.api.UriConstant
import com.zf.mart.mvp.bean.CartCountBean
import com.zf.mart.mvp.bean.CartGoodsList
import com.zf.mart.utils.GlideUtils
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
    var onInputListener: ((CartCountBean) -> Unit)? = null
    //规格
    var onSpecListener: ((spec: String) -> Unit)? = null

    //增加减少数量
    var onCountListener: ((CartCountBean) -> Unit)? = null

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.apply {

            //商品名字
            goodsName.text = data[position].goods.goods_name
            //商品图片
            GlideUtils.loadUrlImage(context, UriConstant.BASE_URL + data[position].goods.original_img, goodsIcon)
            //商品价格
            goodsPrice.text = "¥${data[position].goods_price}"
            //数量
            numberInput.text = data[position].goods_num

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
                    onCountListener?.invoke(
                        CartCountBean(
                            data[position].id, (numberInput.text.toString().toInt() - 1).toString()
                        )
                    )

                }
            }

            //增加
            increase.setOnClickListener {
                onCountListener?.invoke(
                    CartCountBean(
                        data[position].id,
                        (numberInput.text.toString().toInt() + 1).toString()
                    )
                )
            }

            //输入数量
            numberInput.setOnClickListener {
                onInputListener?.invoke(CartCountBean(data[position].id, numberInput.text.toString()))
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