package com.zf.mart.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.zf.mart.R
import com.zf.mart.mvp.bean.ShopList
import kotlinx.android.synthetic.main.item_cart_shop.view.*


/**
 * 店铺和商品
 */
class CartShopAdapter1(val context: Context?, val data: List<ShopList>) :
    RecyclerView.Adapter<CartShopAdapter1.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_cart_shop, parent, false)
        return ViewHolder(view)
    }


    override fun getItemCount(): Int = data.size

    var checkGoodsListener: ((List<ShopList>) -> Unit)? = null

    var onShopSpecListener: ((spec: String) -> Unit)? = null
    var onShopNumListener: ((num: Int) -> Unit)? = null


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.apply {
            shopName.text = data[position].shopName
            //初始化
            val adapter = CartGoodsAdapter1(context, data[position].goodsList)
            recyclerView.layoutManager = LinearLayoutManager(context)
            recyclerView.adapter = adapter

            /** checkBox 逻辑 */
            checkBox.isChecked = data[position].ifCheck

            //商品适配器的选中监听回调
            adapter.checkGoodsListener = {
                /** 重组数据 */
                data[position].goodsList = it
                var size = 0
                data[position].goodsList.forEach { goods ->
                    if (goods.ifCheck) {
                        size += 1
                    }
                }
                //该商家全部商品被选中或者反选  赋值商家的选中标记
                data[position].ifCheck = size == data[position].goodsList.size
                checkBox.isChecked = data[position].ifCheck
                //通知fragment刷新
                checkGoodsListener?.invoke(data)
            }

            adapter.apply {
                onInputListener = {
                    onShopNumListener?.invoke(it)
                }
                onSpecListener = {
                    onShopSpecListener?.invoke(it)
                }
            }

            checkBox.setOnClickListener {

                //重新给商品赋值是否选中
                data[position].goodsList.forEach { goodsList ->
                    goodsList.ifCheck = checkBox.isChecked
                }
                adapter.notifyDataSetChanged()


                //点击后改商家boolean值
                data[position].ifCheck = checkBox.isChecked
                //通知fragment刷新
                checkGoodsListener?.invoke(data)
            }

        }

    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

}