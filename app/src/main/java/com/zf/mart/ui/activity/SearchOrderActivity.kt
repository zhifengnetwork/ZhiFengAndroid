package com.zf.mart.ui.activity

import android.content.Context
import android.content.Intent
import android.view.Gravity
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.scwang.smartrefresh.layout.util.DensityUtil
import com.zf.mart.R
import com.zf.mart.base.BaseActivity
import com.zf.mart.ui.adapter.SearchOrderAdapter
import com.zf.mart.utils.StatusBarUtils
import com.zf.mart.view.LayoutGravity
import com.zf.mart.view.RecDecoration
import com.zf.mart.view.popwindow.SearchFilterPopupWindow
import com.zf.mart.view.popwindow.SynthesisPopupWindow
import com.zf.mart.view.recyclerview.RecyclerViewDivider
import kotlinx.android.synthetic.main.activity_search_order.*

/**
 * 搜索的订单结果
 */
class SearchOrderActivity : BaseActivity() {

    override fun initToolBar() {

        backLayout.setOnClickListener {
            finish()
        }

        StatusBarUtils.darkMode(
            this,
            ContextCompat.getColor(this, R.color.colorSecondText),
            0.3f
        )
    }

    //搜索关键词
    var mKeyWord = ""

    companion object {
        fun actionStart(context: Context?, keyWord: String) {
            val intent = Intent(context, SearchOrderActivity::class.java)
            intent.putExtra("key",keyWord)
            context?.startActivity(intent)

        }
    }

    override fun layoutId(): Int = R.layout.activity_search_order

    override fun initData() {
        mKeyWord = intent.getStringExtra("key")
    }

    override fun initView() {

        searchInput.text = mKeyWord

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = mAdapter
        recyclerView.addItemDecoration(oneDivider)
    }

    private val mAdapter by lazy { SearchOrderAdapter(this, data) }

    private val oneDivider by lazy {
        RecyclerViewDivider(
            this,
            LinearLayoutManager.VERTICAL,
            1,
            ContextCompat.getColor(this, R.color.colorLine)
        )
    }
    private val twoDivider by lazy { RecDecoration(DensityUtil.dp2px(10f)) }

    override fun initEvent() {

        //综合
        synthesis.setOnClickListener {
            val popWindow = object : SynthesisPopupWindow(
                this, R.layout.pop_search_synthesis, LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            ) {}
            val layoutGravity = LayoutGravity(LayoutGravity.ALIGN_RIGHT)
            popWindow.showBashOfAnchor(rankLayout, layoutGravity, 0, 0)
        }

        //筛选
        filterBtn.setOnClickListener {
            val popWindow = object : SearchFilterPopupWindow(
                this, R.layout.pop_search_filter, DensityUtil.dp2px(280f),
                LinearLayout.LayoutParams.MATCH_PARENT
            ) {}
            popWindow.showAtLocation(parentLayout, Gravity.RIGHT, 0, 0)
        }

        /** 切换排版，改变layoutManager后找到第一个可见的item 定位到 */
        composeLayout.setOnClickListener {

            val position = (recyclerView.layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()

            composeIcon.isSelected = !composeIcon.isSelected
            if (composeIcon.isSelected) {
                //两列排版
                val layoutManager = GridLayoutManager(this, 2)
                recyclerView.layoutManager = layoutManager
                recyclerView.adapter = mAdapter
                recyclerView.removeItemDecoration(oneDivider)
                recyclerView.addItemDecoration(twoDivider)
                mAdapter.changeType(2)

            } else {
                //一列排版
                recyclerView.layoutManager = LinearLayoutManager(this)
                recyclerView.adapter = mAdapter
                recyclerView.removeItemDecoration(twoDivider)
                recyclerView.addItemDecoration(oneDivider)
                mAdapter.changeType(1)
            }

            recyclerView.scrollToPosition(position)
        }

        searchInput.setOnClickListener {
            SearchActivity.actionStart(this, searchInput.text.toString())
        }
    }

    override fun start() {
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
}