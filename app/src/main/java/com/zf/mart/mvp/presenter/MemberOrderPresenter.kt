package com.zf.mart.mvp.presenter

import com.zf.mart.base.BasePresenter
import com.zf.mart.mvp.contract.MemberOrderContract
import com.zf.mart.mvp.model.MemberOrderModel
import com.zf.mart.net.exception.ExceptionHandle

class MemberOrderPresenter : BasePresenter<MemberOrderContract.View>(), MemberOrderContract.Presenter {
    private val model by lazy { MemberOrderModel() }
    private var mPage = 1
    override fun requestMemberOrder(page: Int?, num: Int) {
        checkViewAttached()
        mPage = page ?: mPage
        mRootView?.showLoading()
        val disposable = model.getMemberOrder(mPage, num)
            .subscribe({
                mRootView?.apply {
                    dismissLoading()
                    when (it.status) {
                        0 -> {
                            if (it.data != null) {
                                if (mPage == 1) {

                                    getMenberOrder(it.data)

                                    if (it.data.list.isNotEmpty()) {
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
                    dismissLoading()
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