package com.zf.mart.ui.activity

import android.content.Context
import android.content.Intent
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.zf.mart.R
import com.zf.mart.base.BaseActivity
import com.zf.mart.base.BaseFragmentAdapter
import com.zf.mart.ui.fragment.focus.FocusGoodsFragment
import com.zf.mart.ui.fragment.focus.FocusShopFragment
import com.zf.mart.utils.StatusBarUtils
import kotlinx.android.synthetic.main.activity_focus.*
import kotlinx.android.synthetic.main.layout_toolbar.*
import java.util.ArrayList

/**
 * 我的关注
 */
class FocusActivity : BaseActivity() {

    override fun initToolBar() {

        StatusBarUtils.darkMode(
            this,
            ContextCompat.getColor(this, R.color.colorSecondText),
            0.3f
        )

        titleName.text = "我的关注"
    }

    companion object {
        const val FLAG = "Tag"
        const val SHOP = "SHOP"
        const val GOODS = "GOODS"

        fun actionStart(context: Context?, tag: String) {
            val intent = Intent(context, FocusActivity::class.java)
            intent.putExtra(FLAG, tag)
            context?.startActivity(intent)
        }
    }

    override fun layoutId(): Int = R.layout.activity_focus

    private var currentPage = ""

    override fun initData() {
        currentPage = intent.getStringExtra(FLAG)
    }

    override fun initView() {

//        val titles = arrayListOf("商品"
////            , "店铺"
//        )
//        val fragments = arrayListOf(
//            FocusGoodsFragment.newInstance() as Fragment
////            , FocusShopFragment.newInstance() as Fragment
//        )
//        val adapter = BaseFragmentAdapter(supportFragmentManager, fragments, titles)
//        viewPager.adapter = adapter
//        tabLayout.setViewPager(viewPager)
//        //设置默认进来哪个页面
//        tabLayout.currentTab = if (currentPage == GOODS) 0 else 1
    }

    override fun initEvent() {
        back.setOnClickListener {
            finish()
        }
    }

    override fun start() {
    }
}