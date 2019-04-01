package com.zf.mart.mvp.presenter

import com.zf.mart.api.UriConstant
import com.zf.mart.base.BasePresenter
import com.zf.mart.mvp.bean.OrderListBean
import com.zf.mart.mvp.contract.OrderListContract
import com.zf.mart.mvp.model.OrderListModel
import com.zf.mart.net.exception.ExceptionHandle

class OrderListPresenter : BasePresenter<OrderListContract.View>(), OrderListContract.Presenter {

    private var mPage: Int = 1

    override fun requestOrderList(type: String, page: Int?) {
        checkViewAttached()

        mPage = page ?: mPage

        //加载图在activity中写,showLoading只做结束refreshLayout的finish事件
        val disposable = model.requestOrderList(type, mPage)
            .subscribe({
                mRootView?.apply {
                    dismissLoading()
                    when (it.status) {
                        0 -> {
                            if (it.data.size < UriConstant.PER_PAGE) {
                                setLoadComplete()
                            }
                            if (mPage == 1) {
                                setFinishRefresh(it.data)
                                if (it.data.isEmpty()) {
                                    //刷新数据为空
                                    setEmptyOrder()
                                }
                            } else {
                                setFinishLoadMore(it.data)
                            }
                            mPage += 1
                        }
                        else -> {
                            if (mPage == 1) showError(it.msg, it.status) else loadMoreError(it.msg, it.status)
                        }
                    }
                }
            }, {
                mRootView?.apply {
                    dismissLoading()
                    if (mPage == 1) showError(ExceptionHandle.handleException(it), ExceptionHandle.errorCode)
                    else loadMoreError(ExceptionHandle.handleException(it), ExceptionHandle.errorCode)

                }

            })
        addSubscription(disposable)
    }

    private val model: OrderListModel by lazy { OrderListModel() }

}