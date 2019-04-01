package com.zf.mart.mvp.presenter

import com.zf.mart.base.BasePresenter
import com.zf.mart.mvp.contract.AddressContract
import com.zf.mart.mvp.contract.LoginContract
import com.zf.mart.mvp.model.AddressModel
import com.zf.mart.mvp.model.LoginModel
import com.zf.mart.net.exception.ExceptionHandle

class AddressPresenter : BasePresenter<AddressContract.View>(), AddressContract.Presenter {

    override fun requestAddress() {
        checkViewAttached()
        mRootView?.showLoading()
        val disposable = model.requestAddress()
            .subscribe({
                mRootView?.apply {
                    dismissLoading()
                    when (it.status) {
                        0 -> setAddress(it.data)
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

    private val model: AddressModel by lazy { AddressModel() }


}