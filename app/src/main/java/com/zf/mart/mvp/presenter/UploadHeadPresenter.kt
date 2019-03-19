package com.zf.mart.mvp.presenter

import com.zf.mart.base.BasePresenter
import com.zf.mart.mvp.contract.UploadHeadContract
import com.zf.mart.mvp.model.UploadHeadModel
import com.zf.mart.net.exception.ExceptionHandle
import com.zf.mart.utils.LogUtils
import okhttp3.MultipartBody

class UploadHeadPresenter : BasePresenter<UploadHeadContract.View>(), UploadHeadContract.Presenter {

    private val model: UploadHeadModel by lazy {
        UploadHeadModel()
    }

    override fun upLoadHead(head: MultipartBody.Part) {
        checkViewAttached()
        val disposable = model.uploadHead(head)
            .subscribe({

                mRootView?.apply {
                }
            }, {
                mRootView?.apply {
                    showError(ExceptionHandle.handleException(it), ExceptionHandle.errorCode)
                }
            })
        addSubscription(disposable)
    }
}
