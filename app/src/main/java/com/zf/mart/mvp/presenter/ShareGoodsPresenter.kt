package com.zf.mart.mvp.presenter

import com.zf.mart.base.BasePresenter
import com.zf.mart.mvp.contract.ShareGoodsContract
import com.zf.mart.mvp.model.ShareGoodsModel
import com.zf.mart.net.exception.ExceptionHandle

class ShareGoodsPresenter : BasePresenter<ShareGoodsContract.View>(), ShareGoodsContract.Presenter {
    private val model by lazy { ShareGoodsModel() }
    private var mPage = 1
    override fun requestShareGoods(is_distribut: Int, page: Int?, num: Int) {
        checkViewAttached()
        mPage = page ?: mPage
        mRootView?.showLoading()
        val disposable = model.getShareGoods(is_distribut, mPage, num)
            .subscribe({
                mRootView?.apply {
                    dismissLoading()
                    when (it.status) {
                        0 -> {
                            if (it.data != null) {
                                if (mPage == 1) {
                                    if (it.data.goods_list.isNotEmpty()) {
                                        getShareGoods(it.data.goods_list)
                                    } else {
                                        freshEmpty()
                                    }
                                } else {
                                    setLoadMore(it.data.goods_list)
                                }
                                if (it.data.goods_list.size < num) {
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