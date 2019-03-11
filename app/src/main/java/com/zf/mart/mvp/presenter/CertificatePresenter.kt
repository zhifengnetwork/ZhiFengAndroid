package com.zf.mart.mvp.presenter

import com.zf.mart.base.BasePresenter
import com.zf.mart.mvp.contract.CertificateContract
import com.zf.mart.mvp.model.CertificateModel
import com.zf.mart.net.exception.ExceptionHandle

/**
 * 实名认证
 */
class CertificatePresenter : BasePresenter<CertificateContract.View>(), CertificateContract.Presenter {

    private val model: CertificateModel by lazy {
        CertificateModel()
    }

    override fun requestBankAccount(attribute: String) {
        checkViewAttached()
        mRootView?.showLoading()
        val disposable = model.requestBankAccount(attribute)
                .subscribe({ bean ->
                    mRootView?.apply {
                        mRootView?.dismissLoading()
                        when {
                            bean.code == 0 -> {
                            }
                            else -> showError(bean.message, bean.code)
                        }
                    }
                }, { t ->
                    mRootView?.apply {
                        mRootView?.dismissLoading()
                        showError(ExceptionHandle.handleException(t), ExceptionHandle.errorCode)
                    }
                })
        addSubscription(disposable)
    }
}