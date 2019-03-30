package com.zf.mart.ui.activity

import android.os.Build
import android.view.View
import android.view.WindowManager
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import com.zf.mart.R
import com.zf.mart.api.UriConstant
import com.zf.mart.base.BaseActivity
import com.zf.mart.utils.Preference
import kotlinx.android.synthetic.main.activity_splash.*

/**
 * 闪屏页
 */
class SplashActivity : BaseActivity() {

    override fun initToolBar() {
    }

    override fun layoutId(): Int = R.layout.activity_splash

    override fun initData() {
    }

    private val token by Preference(UriConstant.TOKEN, "")

    override fun initView() {
        /**  隐藏虚拟按键，以保证启动图不会变形  */
        hideBottomUi()

        val animation = AlphaAnimation(1.0f, 1.0f)
        imageView.startAnimation(animation)
        animation.duration = 1000
        animation.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationRepeat(animation: Animation?) {
            }

            override fun onAnimationEnd(animation: Animation?) {
                finishAnimation()
            }

            override fun onAnimationStart(animation: Animation?) {
            }
        })
    }

    private fun finishAnimation() {
//        if (token.isNotEmpty()) {
        MainActivity.actionStart(this)
//        } else {
//            LoginActivity.actionStart(this)
//        }
        finish()
    }

    private fun hideBottomUi() {
        if (Build.VERSION.SDK_INT >= 19) {
            val decorView = window.decorView
            val uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
            decorView.systemUiVisibility = uiOptions
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION)
        } else {
            window.decorView.systemUiVisibility = View.GONE
        }
    }

    override fun initEvent() {
    }

    override fun start() {
    }
}