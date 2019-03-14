package com.zf.mart.ui.activity

import android.content.Context
import android.content.Intent
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.scwang.smartrefresh.layout.util.DensityUtil
import com.zf.mart.R
import com.zf.mart.base.BaseActivity
import com.zf.mart.ui.adapter.HomeFragmentRecommendAdapter
import com.zf.mart.ui.adapter.SearchOrderAdapter
import com.zf.mart.ui.adapter.TwoOrderAdapter
import com.zf.mart.utils.StatusBarUtilNotUse
import com.zf.mart.view.RecDecoration
import kotlinx.android.synthetic.main.activity_search_order.*

/**
 * 搜索的订单结果
 */
class SearchOrderActivity : BaseActivity() {

    override fun initToolBar() {
        StatusBarUtilNotUse.darkMode(
            this,
            ContextCompat.getColor(this, R.color.colorSecondText),
            0.3f
        )

    }

    companion object {
        fun actionStart(context: Context?) {
            context?.startActivity(Intent(context, SearchOrderActivity::class.java))
        }
    }


    override fun layoutId(): Int = R.layout.activity_search_order

    override fun initData() {
    }

    private val data = arrayListOf(
        "手机",
        "电脑",
        "洗衣机",
        "电视机",
        "电吹风",
        "锤子",
        "鼠标",
        "盒子",
        "纸巾",
        "显示器",
        "笔",
        "书",
        "语文课本",
        "数学课本",
        "英语课本",
        "数据结构",
        "sql2008",
        "oc教程",
        "koglin书",
        "java书",
        "从入门到放弃",
        "盗版书"
    )

    override fun initView() {
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = mAdapter
    }

    private val mAdapter by lazy { SearchOrderAdapter(this, data) }

    override fun initEvent() {

        backLayout.setOnClickListener {
            finish()
        }

        //切换排版
        /** 切换排版，改变layoutManager后找到第一个可见的item 定位到 */
        composeLayout.setOnClickListener {

            val position = (recyclerView.layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()

            composeIcon.isSelected = !composeIcon.isSelected
            if (composeIcon.isSelected) {
                //两列排版
                val layoutManager = GridLayoutManager(this, 2)
                recyclerView.layoutManager = layoutManager
                recyclerView.adapter = mAdapter
                mAdapter.changeType(2)

            } else {
                //一列排版
                recyclerView.layoutManager = LinearLayoutManager(this)
                recyclerView.adapter = mAdapter
                mAdapter.changeType(1)
            }

            recyclerView.scrollToPosition(position)
        }

        searchInput.setOnClickListener {
            SearchActivity.actionStart(this)
        }
    }

    override fun start() {
    }


}