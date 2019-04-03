package com.zf.mart.ui.activity

import android.content.Context
import android.content.Intent
import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.zf.mart.R
import com.zf.mart.base.BaseActivity
import com.zf.mart.ui.adapter.AddressAdapter
import com.zf.mart.utils.StatusBarUtils
import kotlinx.android.synthetic.main.activity_address.*
import kotlinx.android.synthetic.main.layout_toolbar.*

class AddressActivity : BaseActivity() {

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

    private val adapter by lazy { AddressAdapter(this) }

    override fun layoutId(): Int = R.layout.activity_address

    override fun initData() {
    }

    override fun initView() {
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
    }

    override fun initEvent() {
        newAddress.setOnClickListener {
            AddressEditActivity.actionStart(this)
        }
    }

    override fun start() {
    }

}