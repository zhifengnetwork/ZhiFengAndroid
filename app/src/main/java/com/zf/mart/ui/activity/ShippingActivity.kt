package com.zf.mart.ui.activity

import android.content.Context
import android.content.Intent
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.zf.mart.R
import com.zf.mart.base.BaseActivity
import com.zf.mart.mvp.bean.ShippingBean
import com.zf.mart.mvp.bean.ShippingList
import com.zf.mart.mvp.contract.ShippingContract
import com.zf.mart.mvp.presenter.ShippingPresenter
import com.zf.mart.showToast
import com.zf.mart.ui.adapter.ShippingAdapter
import kotlinx.android.synthetic.main.activity_shipping.*
import kotlinx.android.synthetic.main.layout_toolbar.*

/**
 * 物流信息
 */
class ShippingActivity : BaseActivity(), ShippingContract.View {

    override fun showError(msg: String, errorCode: Int) {
        showToast(msg)
    }

    override fun setShipping(bean: ShippingBean) {
        data.clear()
        data.addAll(bean.result)
        adapter.notifyDataSetChanged()
    }

    override fun showLoading() {
    }

    override fun dismissLoading() {
    }


    private var mOrderId: String = ""

    companion object {
        fun actionStart(context: Context?, orderId: String) {
            val intent = Intent(context, ShippingActivity::class.java)
            intent.putExtra("orderId", orderId)
            context?.startActivity(intent)
        }
    }

    override fun initToolBar() {
        titleName.text = "物流信息"
        rightLayout.visibility = View.INVISIBLE
        back.setOnClickListener { finish() }
    }

    override fun layoutId(): Int = R.layout.activity_shipping

    override fun initData() {
        mOrderId = intent.getStringExtra("orderId")
    }

    private val data = ArrayList<ShippingList>()
    private val adapter by lazy { ShippingAdapter(this, data) }

    override fun initView() {
        presenter.attachView(this)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
    }

    private val presenter by lazy { ShippingPresenter() }

    override fun initEvent() {
    }

    override fun start() {
        presenter.requestShipping(mOrderId)
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.detachView()
    }

}