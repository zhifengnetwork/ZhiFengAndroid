package com.zf.mart.ui.activity

import android.content.Context
import android.content.Intent
import android.view.View
import com.zf.mart.R
import com.zf.mart.base.BaseActivity
import kotlinx.android.synthetic.main.layout_toolbar.*

/**
 * wo de ji fen
 */
class IntegralActivity:BaseActivity() {


    companion object {
        fun actionStart(context: Context?){
            context?.startActivity(Intent(context,IntegralActivity::class.java))
        }
    }

    override fun initToolBar() {
        titleName.text = "积分累计"
        back.setOnClickListener {

        }
        rightLayout.visibility= View.INVISIBLE
    }

    override fun layoutId(): Int = R.layout.activity_integral

    override fun initData() {
    }

    override fun initView() {
    }

    override fun initEvent() {

    }
    override fun start() {

    }
}