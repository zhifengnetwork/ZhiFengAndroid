package com.zf.mart.ui.fragment.action

import androidx.recyclerview.widget.LinearLayoutManager
import com.zf.mart.R
import com.zf.mart.base.BaseFragment
import com.zf.mart.mvp.bean.AuctionList
import com.zf.mart.mvp.contract.AuctionListContract
import com.zf.mart.mvp.presenter.AuctionListPresenter
import com.zf.mart.net.exception.ErrorStatus
import com.zf.mart.showToast
import com.zf.mart.ui.activity.AuctionDetailActivity
import com.zf.mart.ui.adapter.AuctionAdapter
import kotlinx.android.synthetic.main.fragment_auction.*

/**
 * 竞拍
 */
class AuctionFragment : BaseFragment(), AuctionListContract.View {

    override fun setAuctionList(bean: List<AuctionList>) {
        mLayoutStatusView?.showContent()
        refreshLayout.setEnableLoadMore(true)
        data.clear()
        data.addAll(bean)
        adapter.notifyDataSetChanged()

    }

    override fun setLoadMore(bean: List<AuctionList>) {
        data.addAll(bean)
        adapter.notifyDataSetChanged()
    }

    override fun setEmpty() {
        refreshLayout.setEnableLoadMore(false)
        mLayoutStatusView?.showEmpty()
    }

    override fun setLoadComplete() {
        refreshLayout.finishLoadMoreWithNoMoreData()
    }

    override fun showError(msg: String, errorCode: Int) {
        refreshLayout.setEnableLoadMore(false)
        if (errorCode == ErrorStatus.NETWORK_ERROR) {
            mLayoutStatusView?.showNoNetwork()
        } else {
            mLayoutStatusView?.showError()
            showToast(msg)
        }
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

    override fun getLayoutId(): Int = R.layout.fragment_auction

    private val data = ArrayList<AuctionList>()

    private val adapter by lazy { AuctionAdapter(context, data) }

    private val presenter by lazy { AuctionListPresenter() }

    override fun initView() {
        mLayoutStatusView = multipleStatusView
        presenter.attachView(this)
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = adapter
    }

    override fun lazyLoad() {
        refreshLayout.setEnableLoadMore(false)
        if (data.isEmpty()) {
            mLayoutStatusView?.showLoading()
        }
        presenter.requestAuctionList(1)

    }

    override fun initEvent() {

        refreshLayout.setOnLoadMoreListener {
            presenter.requestAuctionList(null)
        }

        adapter.mClickListener = {
            AuctionDetailActivity.actionStart(context, it)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        adapter.finishCountDown()
    }


    override fun onDestroy() {
        super.onDestroy()
        presenter.detachView()
    }

}