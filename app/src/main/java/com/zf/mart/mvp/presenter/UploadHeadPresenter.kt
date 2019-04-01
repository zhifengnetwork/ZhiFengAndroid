package com.zf.mart.mvp.presenter

import com.zf.mart.base.BasePresenter
import com.zf.mart.mvp.contract.UploadHeadContract
import com.zf.mart.mvp.model.UploadHeadModel
import com.zf.mart.net.exception.ExceptionHandle
import okhttp3.MultipartBody

class UploadHeadPresenter : BasePresenter<UploadHeadContract.View>(), UploadHeadContract.Presenter {

    private val model: UploadHeadModel by lazy {
        UploadHeadModel()
    }

    override fun upLoadHead(head: MultipartBody.Part) {
        checkViewAttached()
        mRootView?.showLoading()
        val disposable = model.uploadHead(head)
            .subscribe({ it ->
                mRootView?.apply {
                    dismissLoading()
                    when (it.status) {
                        0 -> setHead(it)
                        -2 -> showError(it.data, it.status)
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
