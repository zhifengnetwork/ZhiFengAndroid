package com.zf.mart.ui.activity

import android.content.Context
import android.content.Intent
import android.view.KeyEvent
import androidx.core.content.ContextCompat
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import com.zf.mart.R
import com.zf.mart.base.BaseActivity
import com.zf.mart.showToast
import com.zf.mart.utils.LogUtils
import com.zf.mart.utils.StatusBarUtilNotUse
import kotlinx.android.synthetic.main.activity_action.*

class ActionActivity : BaseActivity() {

    override fun initToolBar() {
        StatusBarUtilNotUse.darkMode(
            this,
            ContextCompat.getColor(this, R.color.colorSecondText),
            0.3f
        )
    }

    private var mType = ""

    companion object {
        const val FLAG = "flag"
        const val AUCTION = "AUCTION"
        const val GROUP = "GROUP"
        const val SECKILL = "SECKILL"
        fun actionStart(context: Context?, type: String) {
            val intent = Intent(context, ActionActivity::class.java)
            intent.putExtra(FLAG, type)
            context?.startActivity(intent)
        }
    }

    override fun layoutId(): Int = R.layout.activity_action

    override fun initData() {
        mType = intent.getStringExtra(FLAG)
    }

    override fun initView() {
    }


    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            finish()
        }
        return super.onKeyDown(keyCode, event)
    }

    override fun initEvent() {

        close.setOnClickListener { finish() }

        radioGroup.check(
            when (mType) {
                AUCTION -> {
                    findNavController(R.id.fragment).navigate(R.id.auctionFragment)
                    auction.id
                }
                GROUP -> {
                    findNavController(R.id.fragment).navigate(R.id.groupFragment)
                    group.id
                }
                SECKILL -> {
                    findNavController(R.id.fragment).navigate(R.id.secKillFragment)
                    secKill.id
                }
                else -> auction.id
            }
        )

        radioGroup.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                auction.id -> findNavController(R.id.fragment).navigate(R.id.auctionFragment)
                group.id -> findNavController(R.id.fragment).navigate(R.id.groupFragment)
                secKill.id -> findNavController(R.id.fragment).navigate(R.id.secKillFragment)
                else -> findNavController(R.id.fragment).navigate(R.id.auctionFragment)
            }
        }

    }

    override fun start() {
    }
}