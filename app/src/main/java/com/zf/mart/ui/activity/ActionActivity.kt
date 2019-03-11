package com.zf.mart.ui.activity

import android.content.Context
import android.content.Intent
import android.view.KeyEvent
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import com.zf.mart.R
import com.zf.mart.base.BaseActivity
import com.zf.mart.showToast
import com.zf.mart.utils.LogUtils
import kotlinx.android.synthetic.main.activity_action.*

class ActionActivity : BaseActivity() {
    override fun initToolBar() {

    }

    companion object {
        fun actionStart(context: Context?) {
            context?.startActivity(Intent(context, ActionActivity::class.java))
        }
    }

    override fun layoutId(): Int = R.layout.activity_action

    override fun initData() {
    }

    override fun initView() {
    }

//    override fun onSupportNavigateUp(): Boolean {
//        return findNavController(R.id.fragment).navigateUp()
//    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            finish()
        }
        return super.onKeyDown(keyCode, event)
    }

    override fun initEvent() {

        close.setOnClickListener { finish() }

        auction.setOnClickListener {
            findNavController(R.id.fragment).navigate(R.id.auctionFragment)
        }

        group.setOnClickListener {
            findNavController(R.id.fragment).navigate(R.id.groupFragment)
        }

        secKill.setOnClickListener {
            findNavController(R.id.fragment).navigate(R.id.secKillFragment)
        }
    }

    override fun start() {
    }
}