package com.zf.mart.mvp.presenter

import com.zf.mart.base.BasePresenter
import com.zf.mart.mvp.contract.OrderOperateContract
import com.zf.mart.mvp.model.OrderOperateModel
import com.zf.mart.net.exception.ExceptionHandle

class OrderOperatePresenter : BasePresenter<OrderOperateContract.View>(), OrderOperateContract.Presenter {

    private val model: OrderOperateModel by lazy { OrderOperateModel() }

    //确认收货
    override fun requestConfirmReceipt(orderId: String) {
        checkViewAttached()
        mRootView?.showLoading()
        val disposable = model.requestConfirmReceipt(orderId)
            .subscribe({
                mRootView?.apply {
                    dismissLoading()
                    when (it.status) {
                        0 -> if (it.data != null) {
                            setConfirmReceipt()
                        }
                        else -> showError(it.msg, it.status)
                    }
                }
            }, {
                mRootView?.apply {
                    dismissLoading()
                    showError(ExceptionHandle.handleException(it), ExceptionHandle.errorCode)
                }
            })
        addSubscription(disposable)
    }

    override fun requestCancelOrder(orderId: String) {
        checkViewAttached()
        mRootView?.showLoading()
        val disposable = model.requestCancelOrder(orderId)
            .subscribe({
                mRootView?.apply {
                    dismissLoading()
                    when (it.status) {
                        0 -> if (it.data != null) {
                            setCancelOrder()
                        }
                        else -> showError(it.msg, it.status)
                    }
                }
            }, {
                mRootView?.apply {
                    dismissLoading()
                    showError(ExceptionHandle.handleException(it), ExceptionHandle.errorCode)
                }
            })
        addSubscription(disposable)
    }

}