package com.zf.mart.ui.fragment.action

import androidx.recyclerview.widget.LinearLayoutManager
import com.zf.mart.R
import com.zf.mart.base.BaseFragment
import com.zf.mart.mvp.bean.GoodEvaList
import com.zf.mart.mvp.contract.GoodEvaContract
import com.zf.mart.mvp.presenter.GoodEvaPresenter
import com.zf.mart.net.exception.ErrorStatus
import com.zf.mart.showToast
import com.zf.mart.ui.adapter.EvaluationAdapter
import kotlinx.android.synthetic.main.fragment_evaluation.*

/**
 * 评价fragment
 */
class GroupEvaluationFragment : BaseFragment(), GoodEvaContract.View {

    override fun setGoodEva(bean: List<GoodEvaList>) {
        mLayoutStatusView?.showContent()
        refreshLayout.setEnableLoadMore(true)
        data.clear()
        data.addAll(bean)
        adapter.notifyDataSetChanged()
    }

    override fun setLoadMore(bean: List<GoodEvaList>) {
        data.addAll(bean)
        adapter.notifyDataSetChanged()
    }

    override fun setEmpty() {
        mLayoutStatusView?.showEmpty()
        refreshLayout.setEnableLoadMore(false)
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

    private var mGoodId = ""
    private var mType = 0

    companion object {
        fun newInstance(goodId: String, type: Int): GroupEvaluationFragment {
            val fragment = GroupEvaluationFragment()
            fragment.mGoodId = goodId
            fragment.mType = type
            return fragment
        }
    }

    override fun getLayoutId(): Int = R.layout.fragment_evaluation

    private val adapter by lazy { EvaluationAdapter(context, data) }

    private val evaPresenter by lazy { GoodEvaPresenter() }

    private val data = ArrayList<GoodEvaList>()

    override fun initView() {
        mLayoutStatusView = multipleStatusView
        evaPresenter.attachView(this)
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = adapter
    }

    override fun onDestroy() {
        super.onDestroy()
        evaPresenter.detachView()
    }

    override fun lazyLoad() {
        refreshLayout.setEnableLoadMore(false)
        if (data.isEmpty()) {
            mLayoutStatusView?.showLoading()
        }
        evaPresenter.requestGoodEva(mGoodId, mType, 1)
    }

    override fun initEvent() {

        refreshLayout.setOnRefreshListener {
            lazyLoad()
        }

        refreshLayout.setOnLoadMoreListener {
            evaPresenter.requestGoodEva(mGoodId, mType, null)
        }
    }
}