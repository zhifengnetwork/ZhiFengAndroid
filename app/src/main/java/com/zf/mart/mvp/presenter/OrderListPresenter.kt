package com.zf.mart.mvp.presenter

import com.zf.mart.base.BasePresenter
import com.zf.mart.mvp.contract.LoginContract
import com.zf.mart.mvp.contract.OrderListContract
import com.zf.mart.mvp.model.LoginModel
import com.zf.mart.mvp.model.OrderListModel
import com.zf.mart.net.exception.ExceptionHandle

class OrderListPresenter : BasePresenter<OrderListContract.View>(), OrderListContract.Presenter {

    override fun requestOrderList() {
        checkViewAttached()
        val disposable = model.requestOrderList()
            .subscribe({
                mRootView?.apply {
                    when (it.status) {
                        0 -> {
                            setOrderList(it.data)
                        }
                        else -> {
                            showError(it.msg, it.status)
                        }
                    }
                }
            }, {
                mRootView?.apply {
                    showError(ExceptionHandle.handleException(it), ExceptionHandle.errorCode)
                }

            })
        addSubscription(disposable)
    }

    private val model: OrderListModel by lazy { OrderListModel() }

}