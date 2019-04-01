package com.zf.mart.ui.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.KeyEvent
import androidx.fragment.app.FragmentTransaction
import com.flyco.tablayout.listener.CustomTabEntity
import com.flyco.tablayout.listener.OnTabSelectListener
import com.zf.mart.R
import com.zf.mart.api.UriConstant
import com.zf.mart.base.BaseActivity
import com.zf.mart.livedata.UserInfoLiveData
import com.zf.mart.mvp.bean.TabEntity
import com.zf.mart.mvp.bean.UserInfoBean
import com.zf.mart.mvp.contract.UserInfoContract
import com.zf.mart.mvp.presenter.UserInfoPresenter
import com.zf.mart.showToast
import com.zf.mart.ui.fragment.ClassifyFragment
import com.zf.mart.ui.fragment.HomeFragment
import com.zf.mart.ui.fragment.MeFragment
import com.zf.mart.ui.fragment.ShoppingCartFragment1
import com.zf.mart.utils.Preference
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity(), UserInfoContract.View {

    private val userInfoPresenter by lazy { UserInfoPresenter() }

    override fun setUserInfo(bean: UserInfoBean) {
        UserInfoLiveData.value = bean
    }

    override fun showError(msg: String, code: Int) {
        showToast(msg)
    }

    override fun showLoading() {
    }

    override fun dismissLoading() {
    }

    override fun initView() {
        userInfoPresenter.attachView(this)
    }

    override fun onDestroy() {
        userInfoPresenter.detachView()
        super.onDestroy()
    }

    private val token by Preference(UriConstant.TOKEN, "")

    override fun start() {
        requestUserInfo()
    }

    //退出登录->登录->再次进主页->同样要获取用户信息
    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        requestUserInfo()
    }

    //newIntent()和start()
    private fun requestUserInfo() {
        if (token.isNotEmpty()) {
            userInfoPresenter.requestUserInfo()
        }
    }

    companion object {
        fun actionStart(context: Context?) {
            context?.startActivity(Intent(context, MainActivity::class.java))
        }
    }

    override fun initToolBar() {

    }

    override fun initEvent() {

    }

    private val mTitles = listOf("首页", "分类", "购物车", "我的")

    private val mIconUnSelectIds = listOf(
        R.drawable.ic_sy,
        R.drawable.ic_fl,
        R.drawable.ic_gwc,
        R.drawable.ic_wo
    )

    private val mIconSelectIds = listOf(
        R.drawable.ic_sy_se,
        R.drawable.ic_fl_se,
        R.drawable.ic_gwc_se,
        R.drawable.ic_wo_se
    )

    private val mTabEntities = ArrayList<CustomTabEntity>()

    private var mHomeFragment: HomeFragment? = null
    private var mDiscoveryFragment: ClassifyFragment? = null
    private var mHotFragment: ShoppingCartFragment1? = null
    private var mMineFragment: MeFragment? = null

    private var mIndex = 0

    override fun layoutId(): Int = R.layout.activity_main

    override fun onCreate(savedInstanceState: Bundle?) {
        if (savedInstanceState != null) {
            mIndex = savedInstanceState.getInt("currTabIndex")
        }
        super.onCreate(savedInstanceState)
        initTab()
        tabLayout.currentTab = mIndex
        switchFragment(mIndex)
    }


    private fun switchFragment(index: Int) {
        val transaction = supportFragmentManager.beginTransaction()
        hideFragments(transaction)
        when (index) {
            0 -> mHomeFragment?.let { transaction.show(it) }
                ?: HomeFragment.getInstance().let {
                    mHomeFragment = it
                    transaction.add(R.id.fl_container, it, "home")
                }
            1 -> mDiscoveryFragment?.let { transaction.show(it) }
                ?: ClassifyFragment.getInstance().let {
                    mDiscoveryFragment = it
                    transaction.add(R.id.fl_container, it, "discovery")
                }
            2 -> mHotFragment?.let { transaction.show(it) }
                ?: ShoppingCartFragment1.getInstance().let {
                    mHotFragment = it
                    transaction.add(R.id.fl_container, it, "hot")
                }
            3 -> mMineFragment?.let { transaction.show(it) }
                ?: MeFragment.getInstance().let {
                    mMineFragment = it
                    transaction.add(R.id.fl_container, it, "mine")
                }
            else -> {
            }
        }
        mIndex = index
        tabLayout.currentTab = mIndex
        transaction.commitAllowingStateLoss()
    }

    private fun hideFragments(transaction: FragmentTransaction) {
        mHomeFragment?.let {
            transaction.hide(it)
        }
        mDiscoveryFragment?.let {
            transaction.hide(it)
        }
        mHotFragment?.let {
            transaction.hide(it)
        }
        mMineFragment?.let {
            transaction.hide(it)
        }
    }

    private fun initTab() {
        (0 until mTitles.size).mapTo(mTabEntities) {
            TabEntity(mTitles[it], mIconSelectIds[it], mIconUnSelectIds[it])
        }
        tabLayout.setTabData(mTabEntities)
        tabLayout.setOnTabSelectListener(object : OnTabSelectListener {
            override fun onTabReselect(position: Int) {}
            override fun onTabSelect(position: Int) {
                switchFragment(position)
            }
        })
    }


    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        //记录fragment的位置,防止崩溃 activity被系统回收时，fragment错乱
        if (tabLayout != null) {
            outState.putInt("currTabIndex", mIndex)
        }
    }

    private var mExitTime: Long = 0
    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (System.currentTimeMillis().minus(mExitTime) <= 2000) {
                finish()
            } else {
                mExitTime = System.currentTimeMillis()
                showToast("再按一次退出程序")
            }
            return true
        }
        return super.onKeyDown(keyCode, event)
    }

    override fun initData() {
    }


}
