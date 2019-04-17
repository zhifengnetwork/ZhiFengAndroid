package com.zf.mart.mvp.presenter

import com.zf.mart.api.UriConstant
import com.zf.mart.base.BasePresenter
import com.zf.mart.mvp.contract.SecKillListContract
import com.zf.mart.mvp.model.SecKillListModel
import com.zf.mart.net.exception.ExceptionHandle

class SecKillListPresenter : BasePresenter<SecKillListContract.View>(), SecKillListContract.Presenter {

    private val model: SecKillListModel by lazy { SecKillListModel() }

    private var mPage = 1

    override fun requestSecKillList(startTime: String, endTime: String, page: Int?) {

        mPage = page ?: mPage

        checkViewAttached()
        mRootView?.showLoading()
        val disposable = model.getSecKillList(startTime, endTime, mPage)
                .subscribe({
                    mRootView?.apply {
                        dismissLoading()
                        when (it.status) {
                            0 -> {
                                if (mPage == 1) {
                                    if (it.data != null && it.data.flash_sale_goods.isNotEmpty()) {
                                        setSecKillList(it.data.flash_sale_goods)
                                    } else {
                                        setEmpty()
                                    }
                                } else {
                                    if (it.data != null && it.data.flash_sale_goods.isNotEmpty()) {
                                        setLoadMore(it.data.flash_sale_goods)
                                    } else {
                                        setLoadComplete()
                                    }
                                }
                                if (it.data != null && it.data.flash_sale_goods.isNotEmpty()) {
                                    if (it.data.flash_sale_goods.size < UriConstant.PER_PAGE) {
                                        setLoadComplete()
                                    }
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