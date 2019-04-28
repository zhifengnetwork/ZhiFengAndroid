package com.zf.mart.ui.activity

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.zf.mart.R
import com.zf.mart.base.BaseActivity
import com.zf.mart.mvp.bean.OrderDetailBean
import com.zf.mart.mvp.bean.OrderGoodsList
import com.zf.mart.mvp.contract.OrderDetailContract
import com.zf.mart.mvp.presenter.OrderDetailPresenter
import com.zf.mart.showToast
import com.zf.mart.ui.adapter.OrderGoodsAdapter
import com.zf.mart.utils.StatusBarUtils
import com.zf.mart.utils.TimeUtils
import kotlinx.android.synthetic.main.activity_order_detail.*
import kotlinx.android.synthetic.main.layout_detail_price.*
import kotlinx.android.synthetic.main.layout_en_order_address.*
import kotlinx.android.synthetic.main.layout_toolbar.*

/**
 * 订单详情
 */
class OrderDetailActivity : BaseActivity(), OrderDetailContract.View {

    override fun showError(msg: String, errorCode: Int) {
        showToast(msg)
    }

    @SuppressLint("SetTextI18n")
    override fun setOrderDetail(bean: OrderDetailBean) {
        userName.text = bean.consignee
        userPhone.text = bean.mobile
        userAddress.text = "${bean.province}${bean.city}${bean.district}${bean.twon}${bean.address}"

        shopName.text = bean.store_name

        data.clear()
        data.addAll(bean.goods)
        adapter.notifyDataSetChanged()

        goodsPrice.text = "¥${bean.goods_price}" //商品总价
        shippingPrice.text = "¥${bean.shipping_price}" //运费
        orderPrice.text = "¥${bean.order_amount}" //订单总价

        shopDiscount.text = "-¥${bean.order_prom_amount}"
        creditPrice.text = "-¥${bean.integral_money}"
        signPrice.text = "-¥${bean.sign_price}"
        userMoney.text = "-¥${bean.user_money}"
        discountMoney.text = "-¥${bean.coupon_price}"

        orderNum.text = bean.order_sn
        createTime.text = TimeUtils.myOrderTime(bean.add_time)
    }

    override fun showLoading() {
    }

    override fun dismissLoading() {
    }

    companion object {
        private var mOrderId = ""
        fun actionStart(context: Context?, orderId: String) {
            val intent = Intent(context, OrderDetailActivity::class.java)
            intent.putExtra("orderId", orderId)
            context?.startActivity(intent)
        }
    }

    override fun initToolBar() {
        StatusBarUtils.darkMode(this, ContextCompat.getColor(this, R.color.colorSecondText), 0.3f)
        rightLayout.visibility = View.INVISIBLE
        back.setOnClickListener { finish() }
        titleName.text = "订单详情"
    }

    private val orderDetailPresenter by lazy { OrderDetailPresenter() }

    override fun layoutId(): Int = R.layout.activity_order_detail

    override fun initData() {
        mOrderId = intent.getStringExtra("orderId")
    }

    private val data = ArrayList<OrderGoodsList>()

    private val adapter by lazy { OrderGoodsAdapter(this, data) }

    override fun initView() {
        orderDetailPresenter.attachView(this)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
    }

    override fun onDestroy() {
        super.onDestroy()
        orderDetailPresenter.detachView()
    }

    override fun initEvent() {
    }

    override fun start() {
        orderDetailPresenter.requestOrderDetail(mOrderId)
    }
}