package com.zf.mart.mvp.presenter

import com.zf.mart.base.BasePresenter
import com.zf.mart.mvp.contract.CartOperateContract
import com.zf.mart.mvp.model.CartOperateModel
import com.zf.mart.net.exception.ExceptionHandle
import okhttp3.RequestBody
import org.json.JSONArray

class CartOperatePresenter : BasePresenter<CartOperateContract.View>(), CartOperateContract.Presenter {

    private val model: CartOperateModel by lazy { CartOperateModel() }

    override fun requestSelect(cart: RequestBody) {
        checkViewAttached()
        mRootView?.showLoading()
        val disposable = model.setCartSelect(cart)
            .subscribe({
                mRootView?.apply {
                    dismissLoading()
                    when (it.status) {
                        0 -> {
                            if (it.data != null) {
                                setSelect(it.data.cart_price_info)
                            }
                        }
                        else -> cartOperateError(it.msg, it.status)
                    }
                }
            }, {
                mRootView?.apply {
                    dismissLoading()
                    cartOperateError(ExceptionHandle.handleException(it), ExceptionHandle.errorCode)
                }
            })
        addSubscription(disposable)
    }

    override fun requestCount(id: String, num: String) {
        checkViewAttached()
        mRootView?.showLoading()
        val disposable = model.setCount(id, num)
            .subscribe({
                mRootView?.apply {
                    dismissLoading()
                    when (it.status) {
                        0 -> {
                            if (it.data != null) {
                                setCount()
                            }
                        }
                        else -> cartOperateError(it.msg, it.status)
                    }
                }
            }, {
                mRootView?.apply {
                    dismissLoading()
                    cartOperateError(ExceptionHandle.handleException(it), ExceptionHandle.errorCode)
                }
            })
        addSubscription(disposable)
    }

}