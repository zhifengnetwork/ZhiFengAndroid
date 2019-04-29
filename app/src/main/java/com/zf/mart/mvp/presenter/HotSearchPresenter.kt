package com.zf.mart.mvp.presenter

import com.zf.mart.base.BasePresenter
import com.zf.mart.mvp.contract.HotSearchContract
import com.zf.mart.mvp.model.HotSearchModel
import com.zf.mart.net.exception.ExceptionHandle

class HotSearchPresenter : BasePresenter<HotSearchContract.View>(), HotSearchContract.Presenter {

    private val model: HotSearchModel by lazy { HotSearchModel() }

    override fun requestHotSearch() {
        checkViewAttached()
        mRootView?.showLoading()
        val disposable = model.requestHotSearch()
                .subscribe({
                    mRootView?.apply {
                        dismissLoading()
                        when (it.status) {
                            0 -> if (it.data != null) {
                                setHotSearch(it.data)
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