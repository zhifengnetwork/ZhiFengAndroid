package com.zf.mart.mvp.presenter

import com.zf.mart.base.BasePresenter
import com.zf.mart.mvp.contract.UserInfoContract
import com.zf.mart.mvp.model.UserInfoModel
import com.zf.mart.net.exception.ExceptionHandle

class UserInfoPresenter : BasePresenter<UserInfoContract.View>(), UserInfoContract.Presenter {

    private val model by lazy {
        UserInfoModel()
    }

    override fun requestUserInfo() {
        checkViewAttached()
        val disposable = model.requestUserInfo().subscribe(
            {
                mRootView?.apply {
                    when (it.status) {
                        0 -> {
                            setUserInfo(it.data)
                        }
                        else -> {
                            showError(it.msg, it.status)
                        }
                    }
                }
            }, {
                mRootView?.apply {
                    showError(ExceptionHandle.handleException(it), ExceptionHandle.errorCode)
                }
            })
        addSubscription(disposable)

    }
}