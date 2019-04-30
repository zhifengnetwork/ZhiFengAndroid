package com.zf.mart.mvp.presenter

import com.zf.mart.base.BasePresenter
import com.zf.mart.mvp.contract.EvaluateContract
import com.zf.mart.mvp.model.EvaluateModel
import com.zf.mart.net.exception.ExceptionHandle
import okhttp3.RequestBody

class EvaluatePresenter : BasePresenter<EvaluateContract.View>(), EvaluateContract.Presenter {

    private val model: EvaluateModel by lazy { EvaluateModel() }

    override fun requestEvaluate(info: String) {
        checkViewAttached()
        mRootView?.showLoading()
        val disposable = model.requestEvaluate(info)
                .subscribe({
                    mRootView?.apply {
                        dismissLoading()
                        when (it.status) {
                            0 -> if (it.data != null) {
                                setEvaluate()
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