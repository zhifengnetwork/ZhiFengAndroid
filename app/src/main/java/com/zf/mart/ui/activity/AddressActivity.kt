package com.zf.mart.ui.activity

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.zf.mart.R
import com.zf.mart.base.BaseActivity
import com.zf.mart.mvp.bean.AddressBean
import com.zf.mart.mvp.contract.AddressContract
import com.zf.mart.mvp.presenter.AddressPresenter
import com.zf.mart.ui.adapter.AddressAdapter
import com.zf.mart.utils.StatusBarUtils
import kotlinx.android.synthetic.main.activity_address.*
import kotlinx.android.synthetic.main.layout_toolbar.*

class AddressActivity : BaseActivity(), AddressContract.View {
    override fun showError(msg: String, errorCode: Int) {

    }

    override fun getAddress(bean: List<AddressBean>) {
        addressData.clear()
        addressData.addAll(bean)
        Log.e("检测","地址列表为："+bean)
        adapter.notifyDataSetChanged()
    }

    override fun showLoading() {

    }

    override fun dismissLoading() {

    }

    companion object {
        fun actionStart(context: Context?) {
            context?.startActivity(Intent(context, AddressActivity::class.java))
        }
    }

    override fun initToolBar() {
        StatusBarUtils.darkMode(this, ContextCompat.getColor(this, R.color.colorSecondText), 0.3f)
        back.setOnClickListener { finish() }
        titleName.text = "地址管理"
        rightLayout.visibility = View.INVISIBLE
    }

    private var addressData = ArrayList<AddressBean>()

    private val adapter by lazy { AddressAdapter(this, addressData) }

    private val addressPresenter by lazy { AddressPresenter() }

    override fun layoutId(): Int = R.layout.activity_address

    override fun initData() {
    }

    override fun initView() {

        addressPresenter.attachView(this)


        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
    }

    override fun initEvent() {
        newAddress.setOnClickListener {

            AddressEditActivity.actionStart(this,null)

        }


    }

    override fun onDestroy() {
        super.onDestroy()
        addressPresenter.detachView()

    }

    override fun onRestart() {
        super.onRestart()
        addressPresenter.requestAddress()

    }

    override fun start() {
        addressPresenter.requestAddress()

    }

}