package com.zf.mart.ui.activity

import android.content.Context
import android.content.Intent
import android.view.View
import androidx.core.content.ContextCompat
import com.zf.mart.R
import com.zf.mart.base.BaseActivity
import com.zf.mart.utils.LogUtils
import com.zf.mart.utils.StatusBarUtils
import kotlinx.android.synthetic.main.activity_input_setpwd.*

/**
 * 重新设置密码
 */
class InputPwdActivity : BaseActivity() {

    companion object {
        fun actionStart(context: Context?) {
            context?.startActivity(Intent(context, InputPwdActivity::class.java))
        }
    }

    override fun initToolBar() {
        StatusBarUtils.darkMode(
            this,
            ContextCompat.getColor(this, R.color.colorSecondText), 0.3f
        )
    }

    override fun layoutId(): Int = R.layout.activity_input_setpwd

    override fun initData() {
    }

    override fun initView() {
    }

    override fun initEvent() {
        confirm.setOnClickListener {
            when {
                pwd.text.isEmpty() || pwd.text.length < 6 -> pwdError.visibility = View.VISIBLE
                pwd.text.toString() != pwdEn.text.toString() -> {
                    pwdError.visibility = View.GONE
                    pwdEnError.visibility = View.VISIBLE
                }
                else -> {
                    pwdError.visibility = View.GONE
                    pwdEnError.visibility = View.GONE
                    LogUtils.e(">>>确认")
                }
            }
        }

    }

    override fun start() {
    }
}
