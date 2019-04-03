package com.zf.mart.mvp.presenter

import com.zf.mart.api.UriConstant
import com.zf.mart.base.BasePresenter
import com.zf.mart.mvp.contract.CartListContract
import com.zf.mart.mvp.model.CartListModel
import com.zf.mart.net.exception.ExceptionHandle

class CartListPresenter : BasePresenter<CartListContract.View>(), CartListContract.Presenter {

    private val model: CartListModel by lazy { CartListModel() }

    private var mPage = 1

    override fun requestCartList(page: Int?) {

        mPage = page ?: mPage

        checkViewAttached()
        mRootView?.showLoading()
        val disposable = model.getCartList()
            .subscribe({
                mRootView?.apply {
                    dismissLoading()
                    when (it.status) {
                        0 -> {
                            if (mPage == 1) {
                                if (it.data.isNotEmpty()) {
                                    setRefreshCart(it.data)
                                } else {
                                    setEmpty()
                                }
                            } else {
                                setLoadMoreCart(it.data)
                            }
                            if (it.data.size < UriConstant.PER_PAGE) {
                                setLoadComplete()
                            }
                        }
                        else -> if (mPage == 1) {
                            showError(it.msg, it.status)
                        } else {
                            loadMoreError(it.msg, it.status)
                        }
                    }
                }
            }, {
                mRootView?.apply {
                    dismissLoading()
                    if (mPage == 1) {
                        showError(ExceptionHandle.handleException(it), ExceptionHandle.errorCode)
                    } else {
                        loadMoreError(ExceptionHandle.handleException(it), ExceptionHandle.errorCode)
                    }
                }
            })
        addSubscription(disposable)
    }

}