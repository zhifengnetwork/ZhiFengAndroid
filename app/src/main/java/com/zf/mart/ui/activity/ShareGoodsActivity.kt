package com.zf.mart.ui.activity

import android.content.Context
import android.content.Intent
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.zf.mart.R
import com.zf.mart.base.BaseActivity
import com.zf.mart.mvp.bean.CommendList
import com.zf.mart.mvp.contract.ShareGoodsContract
import com.zf.mart.mvp.presenter.ShareGoodsPresenter
import com.zf.mart.showToast
import com.zf.mart.ui.adapter.ShareGoodsAdapter
import kotlinx.android.synthetic.main.activity_share_goods.*
import kotlinx.android.synthetic.main.layout_toolbar.*

/**
 * 分润商品
 */
class ShareGoodsActivity : BaseActivity(), ShareGoodsContract.View {
    override fun showError(msg: String, errorCode: Int) {
        refreshLayout.setEnableLoadMore(false)
        showToast(msg)
    }

    override fun getShareGoods(bean: List<CommendList>) {
        mData.clear()
        mData.addAll(bean)
        mAdapter.notifyDataSetChanged()
    }

    override fun freshEmpty() {
        refreshLayout.setEnableLoadMore(false)
    }

    override fun setLoadMore(bean: List<CommendList>) {
        mData.addAll(bean)
        mAdapter.notifyDataSetChanged()
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
            context?.startActivity(Intent(context, ShareGoodsActivity::class.java))
        }
    }

    override fun initToolBar() {
        back.setOnClickListener {
            finish()
        }
        titleName.text = "分润商品"
        rightLayout.visibility = View.INVISIBLE
    }

    private val presenter by lazy { ShareGoodsPresenter() }

    private var mData = ArrayList<CommendList>()

    private val mAdapter by lazy { ShareGoodsAdapter(this,mData) }

    override fun layoutId(): Int = R.layout.activity_share_goods

    override fun initData() {

    }

    override fun initView() {
        presenter.attachView(this)

        share_rl.layoutManager = LinearLayoutManager(this)
        share_rl.adapter = mAdapter
    }

    override fun initEvent() {
        /**上拉加载*/
        refreshLayout.setOnLoadMoreListener {
            presenter.requestShareGoods(1, null, 6)
        }
        /**下拉刷新*/
        refreshLayout.setOnRefreshListener {
            start()

        }
    }

    override fun start() {
        presenter.requestShareGoods(1, 1, 6)
    }

}