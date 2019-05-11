package com.zf.mart.mvp.presenter

import com.zf.mart.base.BasePresenter
import com.zf.mart.mvp.contract.MaterialDetailContract
import com.zf.mart.mvp.model.MaterialDetailModel
import com.zf.mart.net.exception.ExceptionHandle

class MaterialDetailPresnter : BasePresenter<MaterialDetailContract.View>(), MaterialDetailContract.Presenter {
    private val model by lazy { MaterialDetailModel() }
    override fun requestMaterialDetail(id: String) {
        checkViewAttached()
        mRootView?.showLoading()
        val disposable = model.getMaterialDetail(id)
            .subscribe({
                mRootView?.apply {
                    dismissLoading()
                    when (it.status) {
                        0 -> {
                            if (it.data != null) {
                                getMaterialDetail(it.data)
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