package com.zf.mart.ui.activity

import android.content.Context
import android.content.Intent
import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.scwang.smartrefresh.layout.util.DensityUtil
import com.zf.mart.R
import com.zf.mart.base.BaseActivity
import com.zf.mart.ui.adapter.MyOrderAdapter
import com.zf.mart.utils.StatusBarUtils
import com.zf.mart.view.recyclerview.RecyclerViewDivider
import kotlinx.android.synthetic.main.activity_search_my_order.*
import kotlinx.android.synthetic.main.layout_toolbar.*

/**
 * 我的订单搜索结果
 */
class SearchMyOrderActivity : BaseActivity() {

    companion object {
        fun actionStart(context: Context?) {
            context?.startActivity(Intent(context, SearchMyOrderActivity::class.java))
        }
    }

    private val adapter by lazy { MyOrderAdapter(this) }

    override fun initToolBar() {
        StatusBarUtils.darkMode(this, ContextCompat.getColor(this, R.color.colorSecondText), 0.3f)
        titleName.text = "我的订单"
        rightLayout.visibility = View.INVISIBLE
        back.setOnClickListener { finish() }
    }

    override fun layoutId(): Int = R.layout.activity_search_my_order

    override fun initData() {
    }

    override fun initView() {

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
        recyclerView.addItemDecoration(
            RecyclerViewDivider(
                this, LinearLayoutManager.VERTICAL,
                DensityUtil.dp2px(10f), ContextCompat.getColor(this, R.color.colorBackground)
            )
        )
    }

    override fun initEvent() {
    }

    override fun start() {
    }
}