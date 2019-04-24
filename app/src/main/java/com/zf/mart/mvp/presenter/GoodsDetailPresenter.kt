package com.zf.mart.mvp.presenter

import com.zf.mart.api.UriConstant
import com.zf.mart.base.BasePresenter
import com.zf.mart.mvp.contract.GoodsDetailContract
import com.zf.mart.mvp.model.GoodsDetailModel
import com.zf.mart.net.exception.ExceptionHandle

class GoodsDetailPresenter : BasePresenter<GoodsDetailContract.View>(), GoodsDetailContract.Presenter {

    private val model by lazy { GoodsDetailModel() }
    override fun requestGoodsDetail(goods_id: String) {
        checkViewAttached()
        mRootView?.showLoading()
        val disposable = model.getGoodsDetail(goods_id)
            .subscribe({
                mRootView?.apply {
                    dismissLoading()
                    when (it.status) {
                        0 -> {
                            if (it.data != null) {
                                getGoodsDetail(it.data.goods)
                            }
                        }
                        else -> showError(it.msg,it.status)
                    }
                }
            },{
                mRootView?.apply {
                    dismissLoading()
                    showError(ExceptionHandle.handleException(it), ExceptionHandle.errorCode)
                }
            })
        addSubscription(disposable)
    }

    private var mPage = 1
    override fun requestGoodEva(goodId: String, type: Int, page: Int?) {
        mPage = page ?: mPage

        checkViewAttached()
        mRootView?.showLoading()
        val disposable = model.getGoodEva(goodId, type, mPage)
            .subscribe({
                mRootView?.apply {
                    when (it.status) {
                        0 ->{
                            if (it.data!=null){
                                setGoodEva(it.data.commentlist)
                            }
                        }
                        else -> showError(it.msg,it.status)
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

    override fun requestAddress() {
        checkViewAttached()
        mRootView?.showLoading()
        val disposable = model.requestAddress()
            .subscribe({
                mRootView?.apply {
                    dismissLoading()
                    when (it.status) {
                        0 -> {
                            if (it.data!=null){
                                getAddress(it.data)
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

    override fun requestGoodsFreight(goods_id: String, region_id: String, buy_num: String) {
        checkViewAttached()
        mRootView?.showLoading()
        val disposable = model.getGoodsFreight(goods_id,region_id,buy_num)
            .subscribe({
                mRootView?.apply {
                    dismissLoading()
                    when(it.status){
                        0 ->{
                            if (it.data!=null){
                                getGoodsFreight(it.data)
                            }
                        }
                        else ->showError(it.msg,it.status)
                    }
                }
            },{
                mRootView?.apply {
                    dismissLoading()
                    showError(ExceptionHandle.handleException(it), ExceptionHandle.errorCode)
                }
            })
        addSubscription(disposable)
    }


}