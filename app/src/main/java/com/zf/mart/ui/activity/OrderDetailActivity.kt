package com.zf.mart.ui.activity

import android.content.Context
import android.content.Intent
import com.zf.mart.R
import com.zf.mart.base.BaseActivity

/**
 * 订单详情
 */
class OrderDetailActivity : BaseActivity() {

    companion object {
        fun actionStart(context: Context?) {
            context?.startActivity(Intent(context, OrderDetailActivity::class.java))
        }
    }

    override fun initToolBar() {
    }

    override fun layoutId(): Int = R.layout.activity_order_detail

    override fun initData() {
    }

    override fun initView() {
    }

    override fun initEvent() {
    }

    override fun start() {
    }
}