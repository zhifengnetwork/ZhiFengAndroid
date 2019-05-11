package com.zf.mart.mvp.presenter

import com.zf.mart.api.UriConstant.PER_PAGE
import com.zf.mart.base.BasePresenter
import com.zf.mart.mvp.contract.MaterialClassifyContract
import com.zf.mart.mvp.model.MaterialClassifyModel
import com.zf.mart.net.exception.ExceptionHandle

class MaterialClassifyPresenter : BasePresenter<MaterialClassifyContract.View>(), MaterialClassifyContract.Presenter {
    private val model by lazy { MaterialClassifyModel() }
    override fun requestMaterialClassify() {
        checkViewAttached()
        mRootView?.showLoading()
        val disposable = model.getMaterialClassify()
            .subscribe({
                mRootView?.apply {
                    dismissLoading()
                    when (it.status) {
                        0 -> {
                            if (it.data != null) {
                                getMaterialClassify(it.data.list)
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