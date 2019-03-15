package com.zf.mart.ui.activity

import android.content.Context
import android.content.Intent
import android.view.View
import com.zf.mart.R
import com.zf.mart.base.BaseActivity
import kotlinx.android.synthetic.main.layout_toolbar.*

class SigninGiftActivity:BaseActivity(){
    companion object {
        fun actionStart(context: Context?){
            context?.startActivity(Intent(context,SigninGiftActivity::class.java))
        }
    }

    override fun initToolBar() {
        titleName.text="累计积分"
        back.setOnClickListener {

        }
        rightLayout.visibility= View.INVISIBLE
    }

    override fun layoutId(): Int= R.layout.activity_sign_in_gift

    override fun initData() {

    }

    override fun initView() {

    }

    override fun initEvent() {

    }

    override fun start() {

    }

}