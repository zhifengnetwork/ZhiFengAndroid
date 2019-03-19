package com.zf.mart.mvp.presenter

import com.zf.mart.base.BasePresenter
import com.zf.mart.mvp.contract.RegisterContract
import com.zf.mart.mvp.model.RegisterModel
import com.zf.mart.net.exception.ExceptionHandle

class RegisterPresenter : BasePresenter<RegisterContract.View>(), RegisterContract.Presenter {

    private val model by lazy { RegisterModel() }

    override fun requestRegister(phone: String, password: String) {
        checkViewAttached()
        mRootView?.showLoading()
        val disposable = model.requestRegister(phone, password)
            .subscribe({
                mRootView?.apply {
                    dismissLoading()
                    when (it.status) {
                        0 -> registerSuccess()
                        else -> registerError(it.msg, it.status)
                    }
                }

            }, {
                mRootView?.apply {
                    dismissLoading()
                    registerError(ExceptionHandle.handleException(it), ExceptionHandle.errorCode)
                }
            })

        addSubscription(disposable)
    }

}