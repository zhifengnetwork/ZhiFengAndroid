package com.zf.mart.ui.activity

import android.content.Context
import android.content.Intent
import com.zf.mart.R
import com.zf.mart.base.BaseActivity

class ResetPwdActivity : BaseActivity() {

    companion object {
        fun actionStart(context: Context?) {
            context?.startActivity(Intent(context, ResetPwdActivity::class.java))
        }
    }

    override fun initToolBar() {

    }

    override fun layoutId(): Int = R.layout.activity_setpwd

    override fun initData() {
    }

    override fun initView() {
    }

    override fun initEvent() {
    }

    override fun start() {
    }
}