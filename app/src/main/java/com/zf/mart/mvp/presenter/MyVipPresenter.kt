package com.zf.mart.mvp.presenter

import com.zf.mart.base.BasePresenter
import com.zf.mart.mvp.contract.MyVipContract
import com.zf.mart.mvp.model.MyVipModel
import com.zf.mart.net.exception.ExceptionHandle

class MyVipPresenter:BasePresenter<MyVipContract.View>(),MyVipContract.Presenter{
    private val model by lazy { MyVipModel() }
    override fun requestMyVip() {
        checkViewAttached()
        mRootView?.showLoading()
        val disposable = model.getMyVip()
            .subscribe({
                mRootView?.apply {
                    dismissLoading()
                    when (it.status) {
                        0->{if (it.data!=null){
                            getMyVip(it.data)
                        }}
                        else ->showError(it.msg, it.status)
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