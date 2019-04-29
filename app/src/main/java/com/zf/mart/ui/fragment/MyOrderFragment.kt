package com.zf.mart.ui.fragment

import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.zf.mart.R
import com.zf.mart.base.BaseFragment
import com.zf.mart.mvp.bean.OrderListBean
import com.zf.mart.mvp.contract.OrderListContract
import com.zf.mart.mvp.contract.OrderOperateContract
import com.zf.mart.mvp.presenter.OrderListPresenter
import com.zf.mart.mvp.presenter.OrderOperatePresenter
import com.zf.mart.net.exception.ErrorStatus
import com.zf.mart.showToast
import com.zf.mart.ui.activity.MyOrderActivity
import com.zf.mart.ui.activity.ShippingActivity
import com.zf.mart.ui.adapter.MyOrderAdapter
import com.zf.mart.utils.LogUtils
import kotlinx.android.synthetic.main.fragment_myorder.*

class MyOrderFragment : BaseFragment(), OrderListContract.View, OrderOperateContract.View {

    //订单操作失败
    override fun showOperateError(msg: String, errorCode: Int) {
        showToast(msg)
    }

    override fun setConfirmReceipt() {
        showToast("成功确认收货")
        LogUtils.e(">>>>>成功确认收货")
        lazyLoad()
    }

    override fun setCancelOrder() {
        LogUtils.e(">>>>成功取消订单")
        showToast("成功取消订单")
        lazyLoad()
    }

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
        LogUtils.e(">>>>true")
        mLayoutStatusView?.showContent()
        orderListData.clear()
        orderListData.addAll(bean)
        adapter.notifyDataSetChanged()
    }

    //加载下一页数据完成，渲染数据
    override fun setFinishLoadMore(bean: List<OrderListBean>) {
        orderListData.addAll(bean)
        adapter.notifyDataSetChanged()
    }

    //加载完全部
    override fun setLoadComplete() {
        refreshLayout.finishLoadMoreWithNoMoreData()

    }

    override fun showLoading() {
    }

    override fun dismissLoading() {
        refreshLayout.finishRefresh()
        refreshLayout.finishLoadMore()
    }

    private var mType = ""
    private var mKeyWord = ""

    companion object {
        fun newInstance(type: String, keyWord: String): MyOrderFragment {
            val fragment = MyOrderFragment()
            fragment.mType = type
            fragment.mKeyWord = keyWord
            return fragment
        }
    }

    override fun getLayoutId(): Int = R.layout.fragment_myorder

    private val orderListData = ArrayList<OrderListBean>()

    private val adapter by lazy { MyOrderAdapter(context, orderListData) }

    private val orderListPresenter by lazy { OrderListPresenter() }

    private val orderOperatePresenter by lazy { OrderOperatePresenter() }

    override fun initView() {
        mLayoutStatusView = multipleStatusView
        orderListPresenter.attachView(this)
        orderOperatePresenter.attachView(this)

        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = adapter


    }

    override fun lazyLoad() {
        if (orderListData.isEmpty()) {
            mLayoutStatusView?.showLoading()
        }
        refreshLayout.setEnableLoadMore(false)
        requestOrderList(1)
    }

    override fun initEvent() {

        adapter.apply {
            //删除
            deleteListener = {
                //                DeleteMyOrderDialog.showDialog(childFragmentManager, it)
                AlertDialog.Builder(context!!)
                    .setTitle("提示")
                    .setMessage("删除该订单")
                    .setNegativeButton("取消", null)
                    .setPositiveButton("确定") { _, _ ->

                    }
                    .show()
            }

            //取消订单
            onCancelOrderListener = { orderId ->
                AlertDialog.Builder(context!!)
                    .setTitle("提示")
                    .setMessage("取消该订单")
                    .setNegativeButton("取消", null)
                    .setPositiveButton("确定") { _, _ ->

                        orderOperatePresenter.requestCancelOrder(orderId)
                    }
                    .show()

            }

            //确认收货
            onConfirmReceiveListener = { orderId ->
                AlertDialog.Builder(context!!)
                    .setTitle("提示")
                    .setMessage("确认收货")
                    .setNegativeButton("取消", null)
                    .setPositiveButton("确定") { _, _ ->
                        orderOperatePresenter.requestConfirmReceipt(orderId)
                    }
                    .show()
            }

            //查看物流
            onShippingListener = { orderId ->
                ShippingActivity.actionStart(context, orderId)
            }

        }

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
                orderListPresenter.requestOrderList("0", page, "")
            }
            MyOrderActivity.waitPay -> {
                orderListPresenter.requestOrderList("2", page, "")
            }
            MyOrderActivity.waiSend -> {
                orderListPresenter.requestOrderList("1", page, "")
            }
            MyOrderActivity.waitTake -> {
                orderListPresenter.requestOrderList("3", page, "")
            }
            MyOrderActivity.waitEva -> {
                orderListPresenter.requestOrderList("4", page, "")
            }
            else -> {
                //搜索订单
                orderListPresenter.requestOrderList("", page, mKeyWord)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        orderListPresenter.detachView()
        orderOperatePresenter.detachView()
    }

}