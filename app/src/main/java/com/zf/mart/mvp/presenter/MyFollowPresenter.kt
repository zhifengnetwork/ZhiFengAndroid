package com.zf.mart.mvp.presenter

import com.zf.mart.base.BasePresenter
import com.zf.mart.mvp.contract.MyFollowContract
import com.zf.mart.mvp.model.MyFollowModel
import com.zf.mart.net.exception.ExceptionHandle

class MyFollowPresenter : BasePresenter<MyFollowContract.View>(), MyFollowContract.Presenter {
    private val model by lazy { MyFollowModel() }
    override fun requestMyFollow() {
        checkViewAttached()
        mRootView?.showLoading()
        val disposable = model.getMyFollow()
            .subscribe({
                mRootView?.apply {
                    dismissLoading()
                    when (it.status) {
                        0 -> if (it.data != null) {
                            getMyFollowSuccess(it.data)
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