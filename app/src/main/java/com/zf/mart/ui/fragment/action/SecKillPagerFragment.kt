package com.zf.mart.ui.fragment.action

import androidx.recyclerview.widget.LinearLayoutManager
import com.zf.mart.R
import com.zf.mart.base.NotLazyBaseFragment
import com.zf.mart.mvp.bean.SecKillList
import com.zf.mart.mvp.contract.SecKillListContract
import com.zf.mart.mvp.presenter.SecKillListPresenter
import com.zf.mart.net.exception.ErrorStatus
import com.zf.mart.showToast
import com.zf.mart.ui.adapter.SecKillAdapter
import com.zf.mart.utils.GlideImageLoader
import kotlinx.android.synthetic.main.fragment_seckill_page.*

/**
 * 秒杀页面多个布局
 */
class SecKillPagerFragment : NotLazyBaseFragment(), SecKillListContract.View {

    override fun setSecKillList(bean: List<SecKillList>) {
        mLayoutStatusView?.showContent()
        refreshLayout.setEnableLoadMore(true)
        data.clear()
        data.addAll(bean)
        adapter.notifyDataSetChanged()
    }

    override fun setLoadMore(bean: List<SecKillList>) {
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

    var mStartTime = ""
    var mEndTime = ""

    companion object {
        fun newInstance(startTime: String, endTime: String): SecKillPagerFragment {
            val fragment = SecKillPagerFragment()
            fragment.mStartTime = startTime
            fragment.mEndTime = endTime
            return fragment
        }
    }

    override fun getLayoutId(): Int = R.layout.fragment_seckill_page

    override fun initView() {
        mLayoutStatusView = multipleStatusView
        presenter.attachView(this)
        //商品列表
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = adapter

        initBanner()
    }

    private val data = ArrayList<SecKillList>()

    private val adapter by lazy { SecKillAdapter(context, data) }

    private val presenter by lazy { SecKillListPresenter() }


    override fun lazyLoad() {
        refreshLayout.setEnableLoadMore(false)
        if (data.isEmpty()) {
            mLayoutStatusView?.showLoading()
        }
        presenter.requestSecKillList(mStartTime, mEndTime, 1)
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.detachView()
    }

    override fun initEvent() {

        refreshLayout.setOnLoadMoreListener {
            presenter.requestSecKillList(mStartTime, mEndTime, null)
        }


    }

    private fun initBanner() {
        val images = arrayListOf(R.mipmap.v1, R.mipmap.v2, R.mipmap.v3, R.mipmap.v4)
        banner.setImageLoader(GlideImageLoader())
        banner.setImages(images)
        banner.start()

    }
}