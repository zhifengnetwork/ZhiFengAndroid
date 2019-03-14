package com.zf.mart.ui.activity

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.view.View
import androidx.core.content.ContextCompat
import com.zf.mart.R
import com.zf.mart.base.BaseActivity
import com.zf.mart.utils.StatusBarUtilNotUse
import kotlinx.android.synthetic.main.layout_toolbar.*

/**
 * 我的钱包
 */
class WalletActivity : BaseActivity() {

    companion object {
        fun actionStart(context: Context?) {
            context?.startActivity(Intent(context, WalletActivity::class.java))
        }
    }

    override fun initToolBar() {

        StatusBarUtilNotUse.darkMode(
            this,
            ContextCompat.getColor(this, R.color.colorSecondText),
            0.3f
        )


        titleName.text = "我的钱包"
        rightLayout.visibility = View.INVISIBLE
        back.setOnClickListener {
            finish()
        }
    }

    override fun layoutId(): Int = R.layout.activity_wallet

    override fun initData() {
    }


    override fun initView() {

    }

    override fun initEvent() {
    }

    override fun start() {
    }
}