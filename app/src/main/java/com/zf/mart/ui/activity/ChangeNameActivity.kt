package com.zf.mart.ui.activity

import android.content.Intent
import android.view.View
import androidx.core.content.ContextCompat
import com.zf.mart.R
import com.zf.mart.base.BaseActivity
import com.zf.mart.showToast
import com.zf.mart.utils.StatusBarUtils
import kotlinx.android.synthetic.main.activity_change_name.*
import kotlinx.android.synthetic.main.layout_toolbar.*

class ChangeNameActivity : BaseActivity() {


    override fun initToolBar() {
        StatusBarUtils.darkMode(this, ContextCompat.getColor(this, R.color.colorSecondText), 0.3f)
        back.setOnClickListener { finish() }
        titleName.text = "修改名称"
        rightLayout.visibility = View.INVISIBLE
    }

    override fun layoutId(): Int = R.layout.activity_change_name

    private var mNickName = ""

    override fun initData() {
        mNickName = intent.getStringExtra("nickName")
    }

    override fun initView() {
        nickName.setText(mNickName)
    }

    override fun initEvent() {
        confirm.setOnClickListener {
            if (nickName.text.length < 4) {
                showToast("名称不能少于4个字符")
            } else {
                val intent = Intent()
                intent.putExtra("newName", nickName.text.toString())
                setResult(UserActivity.CHANGE_NAME_CODE, intent)
                finish()
            }
        }
    }


    override fun start() {
    }
}