package com.zf.mart.mvp.presenter

import com.zf.mart.base.BasePresenter
import com.zf.mart.mvp.contract.LoginContract
import com.zf.mart.mvp.model.LoginModel
import com.zf.mart.net.exception.ExceptionHandle

class LoginPresenter : BasePresenter<LoginContract.View>(), LoginContract.Presenter {

    private val model: LoginModel by lazy { LoginModel() }

    override fun requestLogin(phone: String, pwd: String) {
        checkViewAttached()
        mRootView?.showLoading()
        val disposable = model.login(phone, pwd)
            .subscribe({
                mRootView?.apply {
                    dismissLoading()
                    when (it.status) {
                        0 -> if (it.data != null) {
                            loginSuccess(it.data)
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