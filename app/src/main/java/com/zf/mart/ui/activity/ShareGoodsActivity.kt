package com.zf.mart.ui.activity

import android.content.Context
import android.content.Intent
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.zf.mart.R
import com.zf.mart.base.BaseActivity
import com.zf.mart.ui.adapter.ShareGoodsAdapter
import kotlinx.android.synthetic.main.activity_share_goods.*
import kotlinx.android.synthetic.main.layout_toolbar.*

/**
 * 分润商品
 */
class ShareGoodsActivity : BaseActivity() {
    companion object {
        fun actionStart(context: Context?) {
            context?.startActivity(Intent(context, ShareGoodsActivity::class.java))
        }
    }

    override fun initToolBar() {
        back.setOnClickListener {
            finish()
        }
        titleName.text = "分润商品"
        rightLayout.visibility = View.INVISIBLE
    }

    override fun layoutId(): Int = R.layout.activity_share_goods

    private val mAdapter by lazy { ShareGoodsAdapter(this) }

    override fun initData() {

    }

    override fun initView() {
        share_rl.layoutManager = LinearLayoutManager(this)
        share_rl.adapter = mAdapter
    }

    override fun initEvent() {

    }

    override fun start() {

    }

}