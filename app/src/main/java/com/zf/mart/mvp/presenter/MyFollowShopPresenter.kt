package com.zf.mart.mvp.presenter

import com.zf.mart.base.BasePresenter
import com.zf.mart.mvp.contract.MyFollowShopContract
import com.zf.mart.mvp.model.MyFollowShopModel
import com.zf.mart.net.exception.ExceptionHandle

class MyFollowShopPresenter : BasePresenter<MyFollowShopContract.View>(), MyFollowShopContract.Presenter {
    private val model by lazy { MyFollowShopModel() }
    override fun requestMyFollowShop() {
        checkViewAttached()
        mRootView?.showLoading()
        val disposable = model.getMyFollowShop()
            .subscribe({
                mRootView?.apply {
                    dismissLoading()
                    when (it.status) {
                        0 -> {
                            if (it.data != null) {
                                getMyFollowShop(it.data.list)
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