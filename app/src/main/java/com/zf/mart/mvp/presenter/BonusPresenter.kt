package com.zf.mart.mvp.presenter

import com.zf.mart.base.BasePresenter
import com.zf.mart.mvp.contract.BonusContract
import com.zf.mart.mvp.model.BonusModel
import com.zf.mart.net.exception.ExceptionHandle

class BonusPresenter : BasePresenter<BonusContract.View>(), BonusContract.Presenter {
    private val model by lazy { BonusModel() }
    override fun requestBonus() {
        checkViewAttached()
        mRootView?.showLoading()
        val disposable = model.getBonus()
            .subscribe({
                mRootView?.apply {
                    when (it.status) {
                        0 -> {
                            if (it.data != null) {
                                getBonus(it.data)
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