package com.zf.mart.ui.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.zf.mart.R
import com.zf.mart.mvp.bean.OrderListBean
import com.zf.mart.ui.activity.OrderDetailActivity
import com.zf.mart.utils.GlideUtils
import com.zf.mart.utils.TimeUtils
import kotlinx.android.synthetic.main.item_myorder.view.*
import kotlinx.android.synthetic.main.layout_order_operation.view.*

class MyOrderAdapter(val context: Context?, val data: List<OrderListBean>) :
    RecyclerView.Adapter<MyOrderAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_myorder, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = data.size

    var deleteListener: ((Int) -> Unit)? = null

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.apply {

            delete.setOnClickListener {
                deleteListener?.invoke(data[position].order_id.toInt())
            }

            setOnClickListener {
                OrderDetailActivity.actionStart(context, data[position].order_id)
            }

            goodsName.text = data[position].goods_name
            addTime.text = TimeUtils.myOrderTime(data[position].add_time)
            shopName.text = data[position].seller_name
            GlideUtils.loadUrlImage(context, data[position].original_img, goodsIcon)
            shouldPay.text = "￥${data[position].order_amount}"
            totalNum.text = "共${data[position].goods_num}件"
            goodsPrice.text = "￥" + data[position].goods_price + "×" + data[position].goods_num

            //发货状态 shipping_status

            //支付状态 pay_status

            //是否实体物品，使用券码

            //是否货到付款
            when (data[position].pay_code) {
                //货到付款
                "cod" -> {

                }
                //非货到付款
                else -> {

                }
            }

            //订单状态 order_status
            when (data[position].order_status) {

                "0" -> {
                    //处理待发货
                    initWaitSend(this, data[position])

                    //待支付(非货到付款)—-pay_status=>0;order_status=>0;pay_code=>不为cod

                }
                "1" -> {
                    //处理待发货
                    initWaitSend(this, data[position])

                    //待收货：shipping_status=1并且order_status=1

                }
                //待评价
                "2" -> {
                    status.text = "交易成功"
                    afterSale.visibility = View.VISIBLE
                    evaluate.visibility = View.VISIBLE
                }
                //已取消
                "3" -> {
                    status.text = "已取消"

                }
                //已完成
                "4" -> {
                    status.text = "交易成功"

                }

            }
        }
    }

    //处理待发货
    private fun initWaitSend(view: View, bean: OrderListBean) {
        //待发货（货到付款）：order_status=>[0,1];    shipping_status=>1; pay_code=> cod
        //待发货（非货到付款）：order_status=>[0,1];  shipping_status=>0; pay_status=>1;pay_code=>非cod
        when (bean.shipping_status) {
        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    /**
     * 待付款 ->等待卖家付款：联系卖家 取消订单 立即付款
     * 待发货 ->待发货：提醒发货 取消订单
     *          待使用：取消订单 查看券码
     * 待收货 ->待收货：取消订单 确定收货
     * 待评价 ->交易成功：去售后 去评价
     */
}