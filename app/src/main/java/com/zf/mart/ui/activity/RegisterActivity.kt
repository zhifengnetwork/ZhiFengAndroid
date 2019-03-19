package com.zf.mart.ui.activity

import android.content.Context
import android.content.Intent
import android.os.CountDownTimer
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.TextPaint
import android.text.TextUtils
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.widget.addTextChangedListener
import com.zf.mart.R
import com.zf.mart.base.BaseActivity
import com.zf.mart.mvp.contract.RegisterContract
import com.zf.mart.mvp.presenter.RegisterPresenter
import com.zf.mart.showToast
import com.zf.mart.utils.StatusBarUtils
import com.zf.mart.view.dialog.RegisterDialog
import kotlinx.android.synthetic.main.activity_register.*

/**
 * 注册
 */
class RegisterActivity : BaseActivity(), RegisterContract.View {

    override fun registerSuccess() {
        RegisterDialog.showDialog(supportFragmentManager)
            .setOnItemClickListener(object : RegisterDialog.OnItemClickListener {
                override fun onItemClick() {
                    finish()
                }
            })
    }

    override fun registerError(message: String, errorCode: Int) {
        showToast(message)
    }

    override fun showLoading() {
        showLoadingDialog()
    }

    override fun dismissLoading() {
        dismissLoadingDialog()
    }

    companion object {
        fun actionStart(context: Context?) {
            context?.startActivity(Intent(context, RegisterActivity::class.java))
        }
    }

    override fun initToolBar() {
        StatusBarUtils.darkMode(
            this,
            ContextCompat.getColor(this, R.color.colorSecondText),
            0.3f
        )
    }

    private val registerPresenter by lazy { RegisterPresenter() }

    override fun layoutId(): Int = R.layout.activity_register

    override fun initData() {
    }

    override fun onDestroy() {
        registerPresenter.detachView()
        super.onDestroy()
    }

    override fun initView() {

        registerPresenter.attachView(this)

        /** 阅读并同意协议 */
        val txtBuilder = SpannableStringBuilder()
        val clickSpan = object : ClickableSpan() {
            override fun onClick(widget: View?) {
                showToast("服务协议")
            }

            override fun updateDrawState(ds: TextPaint?) {
                ds?.let {
                    it.color = ContextCompat.getColor(this@RegisterActivity, R.color.colorPrimaryBtn)
                    ds.isUnderlineText = false
                }
            }
        }
        val str = "我已阅读并同意"
        val content = "《服务协议》"
        txtBuilder.append(str + content)
        txtBuilder.setSpan(clickSpan, str.length, (str + content).length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        read.movementMethod = LinkMovementMethod.getInstance()
        read.text = txtBuilder

    }

    private var isRun = false

    override fun initEvent() {

        checkInput()

        register.setOnClickListener {
            if (checkBox.isChecked) {
                when {
                    TextUtils.isEmpty(account.text) -> accountError.visibility = View.VISIBLE
                    TextUtils.isEmpty(phone.text) -> phoneError.visibility = View.VISIBLE
                    TextUtils.isEmpty(code.text) -> codeError.visibility = View.VISIBLE
                    pwd.text.length < 6 -> pwdError.visibility = View.VISIBLE
                    pwd.text.toString() != pwdEn.text.toString() -> pwdEnError.visibility = View.VISIBLE
                    else -> {
                        accountError.visibility = View.GONE
                        phoneError.visibility = View.GONE
                        codeError.visibility = View.GONE
                        pwdError.visibility = View.GONE
                        pwdEnError.visibility = View.GONE
                        registerPresenter.requestRegister(phone.text.toString(), pwd.text.toString())
                    }
                }
            }
        }

        sendCode.setOnClickListener {
            if (!isRun) {
                sendCode.isSelected = !sendCode.isSelected
                val countDownTimer = object : CountDownTimer((10 * 1000).toLong(), 1000) {
                    override fun onFinish() {
                        isRun = false
                        sendCode.text = "点击再次获取"
                        sendCode.isSelected = !sendCode.isSelected
                    }

                    override fun onTick(millisUntilFinished: Long) {
                        isRun = true
                        sendCode.text = (millisUntilFinished / 1000).toString() + "秒后重新获取"
                    }
                }
                countDownTimer.start()
            }
        }

        login.setOnClickListener {
            LoginActivity.actionStart(this)
        }
    }

    private fun checkInput() {

        checkBox.setOnCheckedChangeListener { _, isChecked ->
            register.isSelected = isChecked
        }

        account.addTextChangedListener {
            accountError.visibility = View.GONE
        }

        phone.addTextChangedListener {
            phoneError.visibility = View.GONE
        }

        code.addTextChangedListener {
            codeError.visibility = View.GONE
        }

        pwd.addTextChangedListener {
            pwdError.visibility = View.GONE
        }

        pwdEn.addTextChangedListener {
            pwdEnError.visibility = View.GONE
        }
    }

    override fun start() {
    }
}