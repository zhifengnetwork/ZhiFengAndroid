package com.zf.mart.mvp.presenter

import com.zf.mart.base.BasePresenter
import com.zf.mart.mvp.contract.MyFootContract
import com.zf.mart.mvp.model.MyFootModel
import com.zf.mart.net.exception.ExceptionHandle

class MyFootPresenter : BasePresenter<MyFootContract.View>(), MyFootContract.Presenter {
    private val model by lazy { MyFootModel() }
    override fun requesetMyFoot() {
        checkViewAttached()
        mRootView?.showLoading()
        val disposable = model.getMyFoot()
            .subscribe({
                mRootView?.apply {
                    dismissLoading()
                    when (it.status) {
                        0 -> {
                            if (it.data != null) {
                                getMyFoot(it.data)
                            }
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

    override fun requestsetMyFoot(visit_ids: String) {
        checkViewAttached()
        mRootView?.showLoading()
        val disposable = model.setMyFoot(visit_ids)
            .subscribe({
                mRootView?.apply {
                    dismissLoading()
                    when (it.status) {
                        0 -> setMyFoot()
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

    override fun requestclearMyFoot() {
        checkViewAttached()
        mRootView?.showLoading()
        val disposable = model.clearMyFoot()
            .subscribe({
                mRootView?.apply {
                    dismissLoading()
                    when (it.status) {
                        0 -> clearMyFoot()
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