package com.zf.mart.mvp.presenter

import com.zf.mart.base.BasePresenter
import com.zf.mart.mvp.contract.GoodsAttrContract
import com.zf.mart.mvp.model.GoodsAttrModel
import com.zf.mart.net.exception.ExceptionHandle

class GoodsAttrPresenter : BasePresenter<GoodsAttrContract.View>(), GoodsAttrContract.Presenter {

    private val model by lazy { GoodsAttrModel() }
    override fun requestGoodsAttr(goods_id: String) {
        checkViewAttached()
        mRootView?.showLoading()
        val disposable = model.getGoodsAttr(goods_id)
            .subscribe({
                mRootView?.apply {
                    dismissLoading()
                    when (it.status) {
                        0 -> {
                            if (it.data != null) {
                                getGoodsAttr(it.data)
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