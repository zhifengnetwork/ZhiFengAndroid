package com.zf.mart.ui.activity

import android.content.Context
import android.content.Intent
import android.opengl.Visibility
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.TextView
import com.zf.mart.R
import com.zf.mart.base.BaseActivity
import kotlinx.android.synthetic.main.activity_myorder_search.*
import kotlinx.android.synthetic.main.layout_toolbar.*

class MyOrderSearchActivity : BaseActivity() {
    override fun initToolBar() {
        titleName.text = "我的订单"
    }

    companion object {
        fun actionStart(context: Context?) {
            context?.startActivity(Intent(context, MyOrderSearchActivity::class.java))
        }
    }

    override fun layoutId(): Int = R.layout.activity_myorder_search

    override fun initData() {
    }

    override fun initView() {
        initToolBar()
    }


    override fun initEvent() {

        inputText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {
                delete.visibility = if (s.isEmpty()) View.INVISIBLE else View.VISIBLE
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        })

        delete.setOnClickListener {
            inputText.text?.clear()
        }

        back.setOnClickListener {
            finish()
        }
    }

    override fun start() {
    }
}