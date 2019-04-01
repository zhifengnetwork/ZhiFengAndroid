package com.zf.mart.ui.fragment

import androidx.recyclerview.widget.LinearLayoutManager
import com.zf.mart.R
import com.zf.mart.base.BaseFragment
import com.zf.mart.mvp.bean.OrderListBean
import com.zf.mart.mvp.contract.OrderListContract
import com.zf.mart.mvp.presenter.OrderListPresenter
import com.zf.mart.net.exception.ErrorStatus
import com.zf.mart.net.exception.ExceptionHandle
import com.zf.mart.showToast
import com.zf.mart.ui.activity.MyOrderActivity
import com.zf.mart.ui.adapter.MyOrderAdapter
import kotlinx.android.synthetic.main.fragment_myorder.*

class MyOrderFragment : BaseFragment(), OrderListContract.View {


    //加载下一页失败
    override fun loadMoreError(msg: String, errorCode: Int) {
        showToast(msg)
    }

    //刷新失败
    override fun showError(msg: String, errorCode: Int) {
        //是否允许加载更多
        if (errorCode == ErrorStatus.NETWORK_ERROR) {
            mLayoutStatusView?.showNoNetwork()
        } else {
            mLayoutStatusView?.showError()
        }
    }

    //空列表
    override fun setEmptyOrder() {
        mLayoutStatusView?.showEmpty()
        refreshLayout.setEnableLoadMore(false)
    }

    //结束刷新，渲染数据
    override fun setFinishRefresh(bean: List<OrderListBean>) {
        refreshLayout.setEnableLoadMore(true)
        mLayoutStatusView?.showContent()
        /**
         * 渲染数据
         */
    }

    //加载下一页数据完成，渲染数据
    override fun setFinishLoadMore(bean: List<OrderListBean>) {
        /**
         * 渲染数据
         */
    }

    //加载完全部
    override fun setLoadComplete() {
        refreshLayout.finishLoadMoreWithNoMoreData()
    }

    //这里不需要了
    override fun showLoading() {
    }

    override fun dismissLoading() {
        refreshLayout.finishRefresh()
        refreshLayout.finishLoadMore()
    }

    private var mType = ""

    companion object {
        fun newInstance(type: String): MyOrderFragment {
            val fragment = MyOrderFragment()
            fragment.mType = type
            return fragment
        }
    }

    override fun getLayoutId(): Int = R.layout.fragment_myorder

    private val orderListData = ArrayList<OrderListBean>()

    private val adapter by lazy { MyOrderAdapter(context) }

    override fun initView() {
        mLayoutStatusView = multipleStatusView
        orderListPresenter.attachView(this)

        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = adapter
    }

    private val orderListPresenter by lazy { OrderListPresenter() }

    override fun lazyLoad() {
        if (orderListData.isEmpty()) {
            mLayoutStatusView?.showLoading()
        }
        requestOrderList(1)
    }

    override fun initEvent() {

        refreshLayout.setOnRefreshListener {
            lazyLoad()
        }

        refreshLayout.setOnLoadMoreListener {
            requestOrderList(null)
        }

    }

    private fun requestOrderList(page: Int?) {
        when (mType) {
            MyOrderActivity.all -> {
                orderListPresenter.requestOrderList("", page)
            }
            MyOrderActivity.waitPay -> {
                orderListPresenter.requestOrderList("", page)
            }
            MyOrderActivity.waiSend -> {
                orderListPresenter.requestOrderList("", page)
            }
            MyOrderActivity.waitTake -> {
                orderListPresenter.requestOrderList("", page)
            }
            MyOrderActivity.waitEva -> {
                orderListPresenter.requestOrderList("", page)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        orderListPresenter.detachView()
    }

}