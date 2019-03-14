package com.zf.mart.ui.activity

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.zf.mart.R
import com.zf.mart.base.BaseActivity
import com.zf.mart.showToast
import com.zf.mart.utils.LogUtils
import com.zf.mart.utils.StatusBarUtilNotUse
import com.zhy.view.flowlayout.FlowLayout
import com.zhy.view.flowlayout.TagAdapter
import kotlinx.android.synthetic.main.activity_search.*


/**
 * 首页进入的搜索界面
 */
class SearchActivity : BaseActivity() {

    companion object {
        fun actionStart(context: Context?) {
            context?.startActivity(Intent(context, SearchActivity::class.java))
        }
    }

    override fun initToolBar() {

        StatusBarUtilNotUse.darkMode(
            this,
            ContextCompat.getColor(this, R.color.colorSecondText),
            0.3f
        )

        backLayout.setOnClickListener {
            finish()
        }
    }

    override fun layoutId(): Int = R.layout.activity_search

    override fun initData() {
    }

    override fun initView() {

        /** 热门搜索 */
        val history = arrayOf("洗衣机", "热水器", "电风扇", "电脑", "水壶", "手机", "衣服", "游戏", "扇子")
        hotLayout.adapter = object : TagAdapter<String>(history) {
            override fun getView(parent: FlowLayout?, position: Int, t: String?): View {
                val tv: TextView = LayoutInflater.from(this@SearchActivity).inflate(
                    R.layout.layout_textview, hotLayout, false
                ) as TextView
                tv.text = t
                return tv
            }
        }
        hotLayout.setOnTagClickListener { _, position, _ ->
            showToast(history[position])
            return@setOnTagClickListener true
        }

        /** 搜索发现 */
        val discovery = arrayOf(
            "洗衣机2", "热水器2", "电风扇2", "热门电脑", "水壶", "手机", "衣服"
            , "游戏", "扇子", "游戏", "扇子", "好玩的游戏", "扇子", "游戏", "扇子"
        )

        discoveryLayout.adapter = object : TagAdapter<String>(discovery) {
            override fun getView(parent: FlowLayout?, position: Int, t: String?): View {
                val tv: TextView = LayoutInflater.from(this@SearchActivity).inflate(
                    R.layout.layout_textview, discoveryLayout, false
                ) as TextView
                tv.text = t
                return tv
            }
        }

        discoveryLayout.setOnTagClickListener { _, position, _ ->
            showToast(discovery[position])
            return@setOnTagClickListener true
        }

    }

    override fun initEvent() {
        searchLayout.setOnClickListener {
            SearchOrderActivity.actionStart(this)
        }
    }

    override fun start() {
    }
}