package com.zf.mart.mvp.presenter

import com.zf.mart.base.BasePresenter
import com.zf.mart.mvp.contract.WXPayContract
import com.zf.mart.mvp.model.WXPayModel
import com.zf.mart.net.exception.ExceptionHandle

class WXPayPresenter : BasePresenter<WXPayContract.View>(), WXPayContract.Presenter {

    private val model: WXPayModel by lazy { WXPayModel() }

    override fun requestWXPay(order_sn: String) {
        checkViewAttached()
        mRootView?.showLoading()
        val disposable = model.requestWXPay(order_sn)
            .subscribe({
                mRootView?.apply {
                    dismissLoading()
                    when (it.status) {
                        0 -> if (it.data != null) {
                            setWXPay(it.data)
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