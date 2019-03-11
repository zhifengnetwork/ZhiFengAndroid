package com.zf.mart.ui.activity

import android.content.Context
import android.content.Intent
import android.view.View
import com.zf.mart.R
import com.zf.mart.base.BaseActivity
import com.zf.mart.utils.CodeUtils
import com.zf.mart.utils.LogUtils
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : BaseActivity() {

    companion object {
        fun actionStart(context: Context?) {
            context?.startActivity(Intent(context, LoginActivity::class.java))
        }
    }

    override fun initToolBar() {
    }

    override fun layoutId(): Int = R.layout.activity_login

    override fun initData() {
    }

    override fun initView() {
        //获取动态验证码
        initCode()
    }

    private fun initCode() {
        val bmp = CodeUtils.getInstance().createBitmap()
        code.setImageBitmap(bmp)
    }


    override fun initEvent() {

        //忘记密码
        forgetPwd.setOnClickListener {
            ResetPwdActivity.actionStart(this)
        }

        code.setOnClickListener {
            val bmp = CodeUtils.getInstance().createBitmap()
            code.setImageBitmap(bmp)
        }

        login.setOnClickListener {
            LogUtils.e(">>>>>>动态验证码：" + CodeUtils.getInstance().code)
//            if (codeInput.text.toString().equals(CodeUtils.getInstance().code, true)) {
                MainActivity.actionStart(this)
//            } else {
//                val bmp = CodeUtils.getInstance().createBitmap()
//                code.setImageBitmap(bmp)
//                codeHint.visibility = View.VISIBLE
//            }
        }

        register.setOnClickListener {
            RegisterActivity.actionStart(this)
        }
    }

    override fun start() {
    }
}