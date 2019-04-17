package com.zf.mart.mvp.presenter

import com.zf.mart.base.BasePresenter
import com.zf.mart.mvp.contract.SecKillTimeContract
import com.zf.mart.mvp.model.SecKillTimeModel
import com.zf.mart.net.exception.ExceptionHandle

class SecKillTimePresenter : BasePresenter<SecKillTimeContract.View>(), SecKillTimeContract.Presenter {

    private val model: SecKillTimeModel by lazy { SecKillTimeModel() }

    override fun requestSecKillTime() {
        checkViewAttached()
        mRootView?.showLoading()
        val disposable = model.getSecKillTime()
                .subscribe({
                    mRootView?.apply {
                        dismissLoading()
                        when (it.status) {
                            0 -> if (it.data != null) {
                                setSecKillTime(it.data)
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