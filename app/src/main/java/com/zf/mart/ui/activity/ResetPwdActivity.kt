package com.zf.mart.ui.activity

import android.content.Context
import android.content.Intent
import android.view.View
import androidx.core.content.ContextCompat
import com.zf.mart.R
import com.zf.mart.base.BaseActivity
import com.zf.mart.utils.StatusBarUtils
import kotlinx.android.synthetic.main.activity_setpwd.*

/**
 * 重设密码先验证手机号
 */
class ResetPwdActivity : BaseActivity() {

    companion object {
        fun actionStart(context: Context?) {
            context?.startActivity(Intent(context, ResetPwdActivity::class.java))
        }
    }

    override fun initToolBar() {
        StatusBarUtils.darkMode(this,ContextCompat.getColor(this,R.color.colorSecondText),0.3f)
    }

    override fun layoutId(): Int = R.layout.activity_setpwd

    override fun initData() {
    }

    override fun initView() {
    }

    override fun initEvent() {
        confirm.setOnClickListener {
            when {
                phone.text.isEmpty() -> phoneError.visibility = View.VISIBLE
                code.text.isEmpty() -> {
                    phoneError.visibility = View.GONE
                    codeHint.visibility = View.VISIBLE
                }
                else -> {
                    phoneError.visibility = View.GONE
                    codeHint.visibility = View.GONE
                    InputPwdActivity.actionStart(this)
                }
            }
        }
    }

    override fun start() {
    }
}