package com.zf.mart.ui.activity

import android.content.Context
import android.content.Intent
import android.view.View
import androidx.core.content.ContextCompat
import com.zf.mart.R
import com.zf.mart.base.BaseActivity
import com.zf.mart.base.BaseFragmentAdapter
import com.zf.mart.ui.fragment.MessageFragment
import com.zf.mart.utils.StatusBarUtils
import kotlinx.android.synthetic.main.activity_message.*
import kotlinx.android.synthetic.main.layout_toolbar.*

class MessageActivity : BaseActivity() {


    companion object {
        fun actionStart(context: Context?) {
            context?.startActivity(Intent(context, MessageActivity::class.java))
        }
    }

    override fun initToolBar() {
        StatusBarUtils.darkMode(this, ContextCompat.getColor(this, R.color.colorSecondText), 0.3f)
        titleName.text = "站内信息"
        back.setOnClickListener { finish() }
        rightLayout.visibility = View.INVISIBLE
    }

    override fun layoutId(): Int = R.layout.activity_message

    override fun initData() {

    }

    override fun initView() {
        val titles = arrayListOf("站内", "消息")
        val fmgs = arrayListOf(MessageFragment.newInstance(), MessageFragment.newInstance())
        val adapter = BaseFragmentAdapter(supportFragmentManager, fmgs, titles)
        viewPager.adapter = adapter
        tabLayout.setupWithViewPager(viewPager)

    }

    override fun initEvent() {

    }

    override fun start() {

    }


}
