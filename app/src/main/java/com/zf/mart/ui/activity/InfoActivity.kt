package com.zf.mart.ui.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import com.zf.mart.R
import com.zf.mart.base.BaseActivity
import com.zf.mart.utils.StatusBarUtilNotUse
import com.zf.mart.view.ToolBarHelper
import kotlinx.android.synthetic.main.activity_info.*

class InfoActivity : BaseActivity() {

    companion object {
        fun actionStart(context: Context?) {
            context?.startActivity(Intent(context, InfoActivity::class.java))
        }
    }

    override fun initToolBar() {
        StatusBarUtilNotUse.darkMode(
            this,
            ContextCompat.getColor(this, R.color.colorSecondText),
            0.3f
        )

    }

    override fun layoutId(): Int = R.layout.activity_info

    override fun initData() {
    }

    override fun initView() {
    }

    override fun initEvent() {

    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        val host = supportFragmentManager.findFragmentById(R.id.infoFragment) as NavHostFragment
        val navController = host.navController
        /** 切换fragment更改标题 */
        navController.addOnDestinationChangedListener { _, destination, _ ->
            ToolBarHelper.addMiddleTitle(this, destination.label, toolBar)
        }

        setSupportActionBar(toolBar)

        toolBar.run {
            title = ""
            navigationIcon = ContextCompat.getDrawable(this@InfoActivity, R.drawable.fanhui)
            setNavigationOnClickListener {
                if (!navController.navigateUp()) {
                    finish()
                } else {
                }
            }
        }
    }


    override fun onSupportNavigateUp(): Boolean {
        return findNavController(R.id.infoFragment).navigateUp()
    }

    override fun start() {
    }
}