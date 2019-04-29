package com.zf.mart.mvp.presenter

import com.zf.mart.base.BasePresenter
import com.zf.mart.mvp.contract.ActiveSpecContract
import com.zf.mart.mvp.model.ActiveSpecModel
import com.zf.mart.net.exception.ExceptionHandle

class ActiveSpecPresenter : BasePresenter<ActiveSpecContract.View>(), ActiveSpecContract.Presenter {

    private val model: ActiveSpecModel by lazy { ActiveSpecModel() }

    //根据规格获取商品信息
    override fun requestSpecInfo(key: String, goodsId: String) {
        checkViewAttached()
        mRootView?.showLoading()
        val disposable = model.requestSpecInfo(key, goodsId)
                .subscribe({
                    mRootView?.apply {
                        dismissLoading()
                        when (it.status) {
                            0 -> {
                                if (it.data != null) {
                                    setSpecInfo(it.data.info)
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

    override fun requestSpec(id: String) {
        checkViewAttached()
        mRootView?.showLoading()
        val disposable = model.requestSpec(id)
                .subscribe({
                    mRootView?.apply {
                        dismissLoading()
                        when (it.status) {
                            0 -> if (it.data != null) {
                                setGoodsSpec(it.data)
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