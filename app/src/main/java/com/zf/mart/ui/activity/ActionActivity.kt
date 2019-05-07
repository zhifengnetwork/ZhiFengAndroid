package com.zf.mart.ui.activity

import android.content.Context
import android.content.Intent
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.zf.mart.R
import com.zf.mart.base.BaseActivity
import com.zf.mart.base.BaseFragmentAdapter
import com.zf.mart.ui.fragment.action.AuctionFragment
import com.zf.mart.ui.fragment.action.GroupFragment
import com.zf.mart.ui.fragment.action.SecKillFragment
import com.zf.mart.utils.StatusBarUtils
import kotlinx.android.synthetic.main.activity_action.*

class ActionActivity : BaseActivity() {

    override fun initToolBar() {
        StatusBarUtils.darkMode(
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
        const val SEC_KILL = "SEC_KILL"
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
        val titles = arrayListOf("竞拍", "拼团", "秒杀")
        val fragments = arrayListOf<Fragment>(
            AuctionFragment.newInstance(),
            GroupFragment.newInstance(),
            SecKillFragment.newInstance()
        )
        val adapter = BaseFragmentAdapter(supportFragmentManager, fragments, titles)
        viewPager.adapter = adapter
        viewPager.offscreenPageLimit = 3
        tabLayout.setupWithViewPager(viewPager)
        viewPager.currentItem = when (mType) {
            AUCTION -> 0
            GROUP -> 1
            SEC_KILL -> 2
            else -> 0
        }
    }

    override fun initEvent() {
        close.setOnClickListener { finish() }
    }

    override fun start() {
    }
}