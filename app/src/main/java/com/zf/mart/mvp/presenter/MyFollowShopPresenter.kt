package com.zf.mart.mvp.presenter

import com.zf.mart.base.BasePresenter
import com.zf.mart.mvp.contract.MyFollowShopContract
import com.zf.mart.mvp.model.MyFollowShopModel
import com.zf.mart.net.exception.ExceptionHandle

class MyFollowShopPresenter : BasePresenter<MyFollowShopContract.View>(), MyFollowShopContract.Presenter {


    private val model by lazy { MyFollowShopModel() }

    private var mPage = 1
    private var nPage = 1

    override fun requsetShopList(page: Int?, num: Int, goodsnum: Int) {
        checkViewAttached()
        nPage = page ?: nPage
        mRootView?.showLoading()
        val disposable = model.getShopList(nPage, num, goodsnum)
            .subscribe({
                mRootView?.apply {
                    dismissLoading()
                    when (it.status) {
                        0 -> {
                            if (it.data != null) {
                                if (nPage == 1) {
                                    if (it.data.list.isNotEmpty()) {
                                        getShopList(it.data.list)
                                    } else {
                                        freshEmpty()
                                    }
                                } else {
                                    setLoadShopMore(it.data.list)
                                }
                                if (it.data.list.size < num) {
                                    setLoadShopComplete()
                                }
                                nPage += 1
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

    override fun requestMyFollowShop(page: Int?, num: Int) {
        checkViewAttached()
        mPage = page ?: mPage
        mRootView?.showLoading()
        val disposable = model.getMyFollowShop(mPage, num)
            .subscribe({
                mRootView?.apply {
                    dismissLoading()
                    when (it.status) {
                        0 -> {
                            if (it.data != null) {
                                if (mPage == 1) {
                                    if (it.data.list.isNotEmpty()) {
                                        getMyFollowShop(it.data)
                                    } else {
                                        freshEmpty()
                                    }
                                } else {
                                    setLoadFollowShopMore(it.data.list)
                                }
                                if (it.data.list.size < num) {
                                    setLoadFollowShopComplete()
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

    override fun requestDelMyFollowShop(seller_id: String, type: String, collect_id: String) {
        checkViewAttached()
        mRootView?.showLoading()
        val disposable = model.delMyFollowShop(seller_id, type, collect_id)
            .subscribe({
                mRootView?.apply {
                    dismissLoading()
                    when (it.status) {
                        0 -> {
                            delMyFollowShop(it.msg)
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