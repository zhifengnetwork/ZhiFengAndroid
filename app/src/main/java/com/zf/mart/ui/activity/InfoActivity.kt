package com.zf.mart.ui.activity

import android.content.Context
import android.content.Intent
import android.view.View
import androidx.navigation.fragment.NavHostFragment
import com.zf.mart.R
import com.zf.mart.base.BaseActivity
import kotlinx.android.synthetic.main.layout_toolbar.*

class InfoActivity : BaseActivity() {

    companion object {
        fun actionStart(context: Context?) {
            context?.startActivity(Intent(context, InfoActivity::class.java))
        }
    }

    override fun initToolBar() {
        titleName.text = "个人资料"
        rightLayout.visibility = View.INVISIBLE
    }

    override fun layoutId(): Int = R.layout.activity_info

    override fun initData() {
    }

    override fun initView() {
    }

    override fun initEvent() {

        val host = supportFragmentManager.findFragmentById(R.id.fragment) as NavHostFragment
        val navController = host.navController

        back.setOnClickListener {
            if (!navController.navigateUp()) {
                finish()
            } else {
                navController.navigateUp()
            }
        }

    }

    override fun start() {
    }
}