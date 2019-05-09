package com.zf.mart.ui.activity

import android.content.Context
import android.content.Intent
import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.zf.mart.R
import com.zf.mart.api.UriConstant
import com.zf.mart.api.UriConstant.PER_PAGE
import com.zf.mart.base.BaseActivity
import com.zf.mart.mvp.bean.CashOutList
import com.zf.mart.mvp.contract.CashOutRecordContract
import com.zf.mart.mvp.presenter.CashOutRecordPresenter
import com.zf.mart.showToast
import com.zf.mart.ui.adapter.CashOutAdapter
import kotlinx.android.synthetic.main.activity_cash_out_record.*
import kotlinx.android.synthetic.main.layout_toolbar.*

class CashOutRecordActivity : BaseActivity(), CashOutRecordContract.View {
    override fun showError(msg: String, errorCode: Int) {
        showToast(msg)
        refreshLayout.setEnableLoadMore(false)
    }

    override fun getCashOutList(bean: List<CashOutList>) {
        refreshLayout.setEnableLoadMore(true)
        mData.clear()
        mData.addAll(bean)
        adapter.notifyDataSetChanged()
    }

    override fun freshEmpty() {
        refreshLayout.setEnableLoadMore(false)
    }

    override fun setLoadMore(bean: List<CashOutList>) {
        mData.addAll(bean)
        adapter.notifyDataSetChanged()
    }

    override fun setLoadComplete() {
        refreshLayout.finishLoadMoreWithNoMoreData()
    }

    override fun loadMoreError(msg: String, errorCode: Int) {
        showToast(msg)
    }

    override fun showLoading() {

    }

    override fun dismissLoading() {
        refreshLayout.finishRefresh()
        refreshLayout.finishLoadMore()
    }

    companion object {
        fun actionStart(context: Context?) {
            context?.startActivity(Intent(context, CashOutRecordActivity::class.java))
        }
    }

    override fun initToolBar() {

        titleName.text = "提现记录"
        rightLayout.visibility = View.INVISIBLE
        back.setOnClickListener {
            finish()
        }
    }

    private val presenter by lazy { CashOutRecordPresenter() }

    private val mData = ArrayList<CashOutList>()

    private val adapter by lazy { CashOutAdapter(this, mData) }

    override fun layoutId(): Int = R.layout.activity_cash_out_record

    override fun initData() {

    }

    override fun initView() {
        presenter.attachView(this)

        record_rl.layoutManager = LinearLayoutManager(this)
        record_rl.adapter = adapter

    }

    override fun initEvent() {
        /**上拉加载*/
        refreshLayout.setOnLoadMoreListener {
            presenter.requestCashOutList(null, PER_PAGE)
        }
        /**下拉刷新*/
        refreshLayout.setOnRefreshListener {
            refreshLayout.setEnableLoadMore(false)
            presenter.requestCashOutList(1, PER_PAGE)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.detachView()
    }

    override fun start() {
        refreshLayout.setEnableLoadMore(false)
        presenter.requestCashOutList(1, PER_PAGE)
    }

}