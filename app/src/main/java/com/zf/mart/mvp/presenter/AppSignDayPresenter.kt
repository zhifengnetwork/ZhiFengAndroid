package com.zf.mart.mvp.presenter

import com.zf.mart.base.BasePresenter
import com.zf.mart.mvp.contract.AppSignDayContract
import com.zf.mart.mvp.model.AppSignDayModel
import com.zf.mart.net.exception.ExceptionHandle

class AppSignDayPresenter : BasePresenter<AppSignDayContract.View>(), AppSignDayContract.Presenter {
    private val model by lazy { AppSignDayModel() }
    override fun requestAppSignDay() {
        checkViewAttached()
        mRootView?.showLoading()
        val disposable = model.getAppSignDay()
            .subscribe({
                mRootView?.apply {
                    dismissLoading()
                    when (it.status) {
                        0 -> {
                            if (it.data != null) {
                                getAppSignDay(it.data)
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

}