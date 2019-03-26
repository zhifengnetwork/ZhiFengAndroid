package com.zf.mart.ui.activity

import android.content.Context
import android.content.Intent
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.scwang.smartrefresh.layout.util.DensityUtil
import com.zf.mart.R
import com.zf.mart.base.BaseActivity
import com.zf.mart.ui.adapter.SameGoodsAdapter
import com.zf.mart.view.RecDecoration
import kotlinx.android.synthetic.main.activity_same_goods.*

/**
 * 关注->商品->同款商品
 */
class SameGoodsActivity : BaseActivity() {

    companion object {
        fun actionStart(context: Context?) {
            context?.startActivity(Intent(context, SameGoodsActivity::class.java))
        }
    }

    override fun initToolBar() {
    }

    override fun layoutId(): Int = R.layout.activity_same_goods

    override fun initData() {
    }

    private val adapter by lazy { SameGoodsAdapter(this) }

    override fun initView() {
        recyclerView.layoutManager = GridLayoutManager(this, 2)
        recyclerView.adapter = adapter
        recyclerView.addItemDecoration(RecDecoration(DensityUtil.dp2px(10f)))
    }

    override fun initEvent() {
    }

    override fun start() {
    }
}