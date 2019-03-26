package com.zf.mart.ui.activity

import android.content.Context
import android.content.Intent
import android.graphics.Paint
import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.scwang.smartrefresh.layout.util.DensityUtil
import com.zf.mart.R
import com.zf.mart.base.BaseActivity
import com.zf.mart.ui.adapter.SameGoodsAdapter
import com.zf.mart.utils.StatusBarUtils
import com.zf.mart.view.RecDecoration
import kotlinx.android.synthetic.main.activity_same_goods.*
import kotlinx.android.synthetic.main.layout_toolbar.*
import java.time.format.TextStyle

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
        StatusBarUtils.darkMode(this, ContextCompat.getColor(this, R.color.colorSecondText), 0.3f)
        back.setOnClickListener { finish() }
        titleName.text = "查看同款"
        rightLayout.visibility = View.INVISIBLE
    }

    override fun layoutId(): Int = R.layout.activity_same_goods

    override fun initData() {
    }

    private val adapter by lazy { SameGoodsAdapter(this) }

    override fun initView() {

        oldPrice.paint.flags = Paint.STRIKE_THRU_TEXT_FLAG

        recyclerView.layoutManager = GridLayoutManager(this, 2)
        recyclerView.adapter = adapter
        recyclerView.addItemDecoration(RecDecoration(DensityUtil.dp2px(10f)))
    }

    override fun initEvent() {
    }

    override fun start() {
    }
}