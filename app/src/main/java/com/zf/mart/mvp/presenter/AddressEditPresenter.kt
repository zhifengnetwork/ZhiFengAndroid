package com.zf.mart.mvp.presenter

import com.zf.mart.base.BasePresenter
import com.zf.mart.mvp.contract.AddressEditContract
import com.zf.mart.mvp.model.AddressEditModel
import com.zf.mart.net.exception.ExceptionHandle

class AddressEditPresenter:BasePresenter<AddressEditContract.View>(),AddressEditContract.Presenter{
    private val model: AddressEditModel by lazy { AddressEditModel() }
    override fun requestAddressEdit() {
        checkViewAttached()
        mRootView?.showLoading()
        val disposable = model.requestAddressEdit()
            .subscribe({
                mRootView?.apply {
                    when (it.status) {
                        0 -> setAddress(it.data)
                        else -> showError(it.msg, it.status)
                    }
                }
            },{
                mRootView?.apply {
                    dismissLoading()
                    showError(ExceptionHandle.handleException(it), ExceptionHandle.errorCode)
                }
            })
        addSubscription(disposable)
    }

}