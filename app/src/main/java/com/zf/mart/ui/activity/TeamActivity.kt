package com.zf.mart.ui.activity

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.zf.mart.R
import com.zf.mart.base.BaseActivity
import com.zf.mart.mvp.bean.MemberOrderList
import com.zf.mart.mvp.bean.MyMemberOrderBean
import com.zf.mart.mvp.bean.User
import com.zf.mart.mvp.contract.MemberOrderContract
import com.zf.mart.mvp.presenter.MemberOrderPresenter
import com.zf.mart.showToast
import com.zf.mart.ui.adapter.TeamAdapter
import com.zf.mart.utils.StatusBarUtils
import kotlinx.android.synthetic.main.activity_team.*
import kotlinx.android.synthetic.main.layout_toolbar.*

/**
 * 我的团队
 */
class TeamActivity : BaseActivity(), MemberOrderContract.View {
    override fun showError(msg: String, errorCode: Int) {
        refreshLayout.setEnableLoadMore(false)
        showToast(msg)
    }

    override fun getMenberOrder(bean: MyMemberOrderBean) {
        mUser = bean.user
        mData.clear()
        mData.addAll(bean.list)
        val adapter = TeamAdapter(this, mUser, mData)
        recyclerView.adapter = adapter
        adapter.notifyDataSetChanged()
    }

    override fun freshEmpty() {
        refreshLayout.setEnableLoadMore(false)
    }

    override fun setLoadMore(bean: List<MemberOrderList>) {
        mData.addAll(bean)
        val adapter = TeamAdapter(this, mUser, mData)
        recyclerView.adapter = adapter
        adapter.notifyDataSetChanged()
    }

    override fun setLoadComplete() {
        refreshLayout.finishLoadMoreWithNoMoreData()
    }

    override fun loadMoreError(msg: String, errorCode: Int) {
        showToast(msg)
    }


    override fun showLoading() {
        refreshLayout.setEnableLoadMore(false)
    }

    override fun dismissLoading() {
        refreshLayout.finishRefresh()
        refreshLayout.finishLoadMore()
    }

    companion object {
        fun actionStart(context: Context?) {
            context?.startActivity(Intent(context, TeamActivity::class.java))
        }
    }

    override fun initToolBar() {

        StatusBarUtils.darkMode(this, ContextCompat.getColor(this, R.color.colorSecondText), 0.3f)

        back.setOnClickListener {
            finish()
        }
        titleName.text = "我的团队业绩"
        rightLayout.visibility = View.INVISIBLE

    }

    private val presenter by lazy { MemberOrderPresenter() }

    private var mUser: User? = null

    private var mData = ArrayList<MemberOrderList>()

//    private val adapter by lazy { TeamAdapter(this, mUser, mData) }

    override fun layoutId(): Int = R.layout.activity_team

    override fun initData() {
    }

    override fun initView() {
        presenter.attachView(this)

        recyclerView.layoutManager = LinearLayoutManager(this)
//        recyclerView.adapter = adapter

    }

    override fun initEvent() {
        /**上拉加载*/
        refreshLayout.setOnLoadMoreListener {
            presenter.requestMemberOrder(null, 6)
        }
        /**下拉刷新*/
        refreshLayout.setOnRefreshListener {
            start()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.detachView()
    }

    override fun start() {
        presenter.requestMemberOrder(1, 6)
    }

}