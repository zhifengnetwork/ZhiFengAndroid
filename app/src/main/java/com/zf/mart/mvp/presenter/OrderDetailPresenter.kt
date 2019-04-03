package com.zf.mart.mvp.presenter

import com.zf.mart.base.BasePresenter
import com.zf.mart.mvp.contract.OrderDetailContract
import com.zf.mart.mvp.model.OrderDetailModel
import com.zf.mart.net.exception.ExceptionHandle

class OrderDetailPresenter : BasePresenter<OrderDetailContract.View>(), OrderDetailContract.Presenter {

    private val model: OrderDetailModel by lazy { OrderDetailModel() }

    override fun requestOrderDetail(id: String) {
        checkViewAttached()
        mRootView?.showLoading()
        val disposable = model.getOrderDetail(id)
            .subscribe({
                mRootView?.apply {
                    dismissLoading()
                    when (it.status) {
                        0 -> setOrderDetail(it.data)
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