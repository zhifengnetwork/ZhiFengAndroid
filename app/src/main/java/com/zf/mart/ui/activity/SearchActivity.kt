package com.zf.mart.ui.activity

import android.content.Context
import android.content.Intent
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.zf.mart.R
import com.zf.mart.base.BaseActivity
import com.zf.mart.showToast
import com.zf.mart.ui.adapter.SearchHintAdapter
import com.zf.mart.utils.LogUtils
import com.zf.mart.utils.StatusBarUtils
import com.zhy.view.flowlayout.FlowLayout
import com.zhy.view.flowlayout.TagAdapter
import kotlinx.android.synthetic.main.activity_search.*


/**
 * 首页进入的搜索界面
 */
class SearchActivity : BaseActivity() {

    //搜索关键词
    private var mKeyWord = ""

    companion object {
        fun actionStart(context: Context?, keyWord: String) {
            val intent = Intent(context, SearchActivity::class.java)
            intent.putExtra("key", keyWord)
            context?.startActivity(intent)
        }
    }

    override fun initToolBar() {

        StatusBarUtils.darkMode(
            this,
            ContextCompat.getColor(this, R.color.colorSecondText),
            0.3f
        )

        backLayout.setOnClickListener {
            finish()
        }
    }

    override fun layoutId(): Int = R.layout.activity_search

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        mKeyWord = intent.getStringExtra("key")
        inputText.setText(mKeyWord)
    }

    override fun initData() {
        mKeyWord = intent.getStringExtra("key")
    }

    private val adapter by lazy { SearchHintAdapter(this) }

    override fun initView() {

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        inputText.setText(mKeyWord)

        /** 热门搜索 */
        val history = arrayListOf("洗衣机", "热水器", "电风扇", "电脑", "水壶", "手机", "衣服", "游戏", "扇子")
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
            SearchOrderActivity.actionStart(this, history[position])
            return@setOnTagClickListener true
        }

        /** 搜索发现 */
        val discovery = arrayListOf(
            "洗衣机2", "热水器2", "电风扇2", "热门电脑", "水壶", "手机", "衣服"
            , "好玩的游戏", "扇子", "游戏", "扇子"
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
            SearchOrderActivity.actionStart(this, discovery[position])
            return@setOnTagClickListener true
        }

    }


    override fun initEvent() {

        inputText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                //判断是否为空，如果为空，则显示历史纪录，否则显示搜索提示
                if (s.isEmpty()) {
                    recyclerView.visibility = View.GONE
                    historyLayout.visibility = View.VISIBLE
                } else {
                    recyclerView.visibility = View.VISIBLE
                    historyLayout.visibility = View.GONE
                }
            }
        })

        searchLayout.setOnClickListener {
            SearchOrderActivity.actionStart(this, inputText.text.toString())
        }
    }

    override fun start() {
    }
}