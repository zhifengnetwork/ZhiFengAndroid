package com.zf.mart.ui.activity

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.View
import androidx.core.content.ContextCompat
import com.zf.mart.R
import com.zf.mart.base.BaseActivity
import com.zf.mart.mvp.bean.OrderDetailBean
import com.zf.mart.mvp.contract.OrderDetailContract
import com.zf.mart.mvp.presenter.OrderDetailPresenter
import com.zf.mart.showToast
import com.zf.mart.utils.GlideUtils
import com.zf.mart.utils.StatusBarUtils
import kotlinx.android.synthetic.main.activity_order_detail.*
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
        sellerName.text = bean.seller_name
        goodsName.text = bean.goods_name
        GlideUtils.loadUrlImage(this, bean.original_img, goodsIcon)

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
    }

    private val orderDetailPresenter by lazy { OrderDetailPresenter() }

    override fun layoutId(): Int = R.layout.activity_order_detail

    override fun initData() {
        mOrderId = intent.getStringExtra("orderId")
    }

    override fun initView() {
        orderDetailPresenter.attachView(this)
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