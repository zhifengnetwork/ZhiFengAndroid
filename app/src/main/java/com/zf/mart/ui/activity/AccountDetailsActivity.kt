package com.zf.mart.ui.activity

import android.content.Context
import android.content.Intent
import android.view.View
import androidx.fragment.app.Fragment
import com.flyco.tablayout.listener.OnTabSelectListener
import com.zf.mart.R
import com.zf.mart.base.BaseActivity
import com.zf.mart.base.BaseFragmentAdapter
import com.zf.mart.ui.fragment.account.AccountDetailFragment
import com.zf.mart.view.verticalViewPager.DefaultTransformer
import kotlinx.android.synthetic.main.activity_account_details.*
import kotlinx.android.synthetic.main.layout_toolbar.*

/**
 * 账户明细
 **/
class AccountDetailsActivity : BaseActivity() {

    companion object {
        fun actionStart(context: Context?) {
            context?.startActivity(Intent(context, AccountDetailsActivity::class.java))
        }
    }

    override fun initToolBar() {
        back.setOnClickListener {
            finish()
        }
        titleName.text = "账户明细"
        rightLayout.visibility = View.INVISIBLE
    }

    override fun layoutId(): Int = R.layout.activity_account_details

    private val mTitles = arrayOf("全部", "赚取", "消费")

    private val mFragment = arrayListOf(
        AccountDetailFragment.newInstance(AccountDetailFragment.ALL) as Fragment,
        AccountDetailFragment.newInstance(AccountDetailFragment.PLUS) as Fragment,
        AccountDetailFragment.newInstance(AccountDetailFragment.MINUS) as Fragment
    )
    private val mAdapter by lazy { BaseFragmentAdapter(supportFragmentManager, mFragment) }
    override fun initData() {

    }

    override fun initView() {
        account_tab.setTabData(mTitles )
        account_vp.adapter = mAdapter
        //禁止滑动
        account_vp.setScroll(false)
//        account_vp.setNoScroll(true)
    }

    override fun initEvent() {
        account_tab.setOnTabSelectListener(object : OnTabSelectListener {
            override fun onTabSelect(position: Int) {
                account_vp.currentItem = position
            }

            override fun onTabReselect(position: Int) {

            }

        })
    }

    override fun start() {

    }

}