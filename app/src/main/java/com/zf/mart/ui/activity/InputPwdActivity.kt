package com.zf.mart.ui.activity

import android.content.Context
import android.content.Intent
import android.view.View
import androidx.core.content.ContextCompat
import com.zf.mart.R
import com.zf.mart.base.BaseActivity
import com.zf.mart.mvp.contract.ForgetPwdContract
import com.zf.mart.mvp.presenter.ForgetPwdPresenter
import com.zf.mart.utils.StatusBarUtils
import kotlinx.android.synthetic.main.activity_input_setpwd.*

/**
 * 重新设置密码
 */
class InputPwdActivity : BaseActivity(), ForgetPwdContract.View {

    override fun showError(msg: String, errorCode: Int) {
    }

    override fun setContract() {
    }

    override fun setChangePwd() {
    }

    override fun setCode(msg: String) {
    }

    override fun showLoading() {
    }

    override fun dismissLoading() {
    }

    private var mPhone = ""

    companion object {
        fun actionStart(context: Context?, phone: String) {
            val intent = Intent(context, InputPwdActivity::class.java)
            intent.putExtra("phone", phone)
            context?.startActivity(intent)
        }
    }

    override fun initToolBar() {
        StatusBarUtils.darkMode(
                this,
                ContextCompat.getColor(this, R.color.colorSecondText), 0.3f
        )
    }

    override fun layoutId(): Int = R.layout.activity_input_setpwd
    private val presenter by lazy { ForgetPwdPresenter() }

    override fun initData() {
        mPhone = intent.getStringExtra("phone")
    }

    override fun initView() {
        presenter.attachView(this)
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
                    presenter.requestChangePwd(mPhone, pwd.text.toString(), pwdEn.text.toString())
                }
            }
        }

    }

    override fun start() {
    }

    override fun onDestroy() {
        presenter.detachView()
        super.onDestroy()
    }

}
