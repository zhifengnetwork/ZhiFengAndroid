package com.zf.mart.mvp.presenter

import com.zf.mart.base.BasePresenter
import com.zf.mart.mvp.contract.AuctionDetailContract
import com.zf.mart.mvp.model.AuctionDetailModel
import com.zf.mart.net.exception.ExceptionHandle

class AuctionDetailPresenter : BasePresenter<AuctionDetailContract.View>(), AuctionDetailContract.Presenter {

    private val model: AuctionDetailModel by lazy { AuctionDetailModel() }

    override fun requestAuctionPrice(id: String) {
        checkViewAttached()
        mRootView?.showLoading()
        val disposable = model.getAuctionPrice(id)
                .subscribe({
                    mRootView?.apply {
                        dismissLoading()
                        when (it.status) {
                            0 -> if (it.data != null) {
                                setAuctionPrice(it.data)
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

    //竞拍详情
    override fun requestAuctionDetail(id: String) {
        checkViewAttached()
        mRootView?.showLoading()
        val disposable = model.getAuctionDetail(id)
                .subscribe({
                    mRootView?.apply {
                        dismissLoading()
                        when (it.status) {
                            0 -> if (it.data != null) {
                                setAuctionDetail(it.data)
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