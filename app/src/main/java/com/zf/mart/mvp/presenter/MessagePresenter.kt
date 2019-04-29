package com.zf.mart.mvp.presenter

import com.zf.mart.base.BasePresenter
import com.zf.mart.mvp.contract.MessageContract
import com.zf.mart.mvp.model.MessageModel
import com.zf.mart.net.exception.ExceptionHandle

class MessagePresenter : BasePresenter<MessageContract.View>(), MessageContract.Presenter {
    private val model by lazy { MessageModel() }
    private var mPage = 1
    override fun requestMessage(page: Int?, num: Int, type: String) {
        checkViewAttached()
        mPage = page ?: mPage
        mRootView?.showLoading()
        val disposable = model.getMessage(mPage, num, type)
            .subscribe({
                mRootView?.apply {
                    dismissLoading()
                    when (it.status) {
                        0 -> {
                            if (it.data != null) {
                                if (mPage == 1) {
                                    if (it.data.list.isNotEmpty()) {
                                        getMessage(it.data.list)
                                    } else {
                                        freshEmpty()
                                    }
                                } else {
                                    setLoadMore(it.data.list)
                                }
                                if (it.data.list.size < num) {
                                    setLoadComplete()
                                }
                                mPage += 1
                            }
                        }
                        -1 -> {

                        }
                        else -> if (mPage == 1) showError(it.msg, it.status) else loadMoreError(it.msg, it.status)
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