package com.zf.mart.ui.activity

import android.content.Context
import android.content.Intent
import android.os.CountDownTimer
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.view.View
import androidx.core.content.ContextCompat
import com.zf.mart.R
import com.zf.mart.base.BaseActivity
import com.zf.mart.showToast
import com.zf.mart.view.dialog.RegisterDialog
import kotlinx.android.synthetic.main.activity_register.*

/**
 * 忘记密码
 */
class RegisterActivity : BaseActivity() {

    companion object {
        fun actionStart(context: Context?) {
            context?.startActivity(Intent(context, RegisterActivity::class.java))
        }
    }

    override fun initToolBar() {
    }

    override fun layoutId(): Int = R.layout.activity_register

    override fun initData() {
    }

    override fun initView() {

        /** 阅读并同意协议 */
        val txtBuilder = SpannableStringBuilder()
        val clickSpan = object : ClickableSpan() {
            override fun onClick(widget: View?) {
                showToast("服务协议")

            }

            override fun updateDrawState(ds: TextPaint?) {
                ds?.let {
                    it.color = ContextCompat.getColor(this@RegisterActivity, R.color.colorPrimaryText)
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


        register.setOnClickListener {
            RegisterDialog.showDialog(supportFragmentManager)
                .setOnItemClickListener(object : RegisterDialog.OnItemClickListener {
                    override fun onItemClick() {
                        LoginActivity.actionStart(this@RegisterActivity)
                    }
                })
        }

        login.setOnClickListener {
            LoginActivity.actionStart(this)
        }
    }

    override fun start() {
    }
}