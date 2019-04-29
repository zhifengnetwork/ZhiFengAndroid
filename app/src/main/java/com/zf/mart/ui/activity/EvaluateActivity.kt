package com.zf.mart.ui.activity

import android.content.Context
import android.content.Intent
import com.zf.mart.R
import com.zf.mart.base.BaseActivity

/**
 * 评价订单
 */
class EvaluateActivity : BaseActivity() {

    override fun initToolBar() {

    }

    companion object {
        fun actionStart(context: Context?, orderId: String) {
            val intent = Intent(context, EvaluateActivity::class.java)
            intent.putExtra("orderId", orderId)
            context?.startActivity(intent)
        }
    }

    override fun layoutId(): Int = R.layout.activity_evaluate

    private var mOrderId = ""

    override fun initData() {
        mOrderId = intent.getStringExtra("orderId")
    }

    override fun initView() {
    }

    override fun initEvent() {
    }

    override fun start() {
    }
}