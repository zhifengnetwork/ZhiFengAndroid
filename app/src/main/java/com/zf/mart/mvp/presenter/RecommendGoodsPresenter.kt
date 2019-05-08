package com.zf.mart.mvp.presenter

import com.zf.mart.api.UriConstant.PER_PAGE
import com.zf.mart.base.BasePresenter
import com.zf.mart.mvp.contract.RecommendGoodsContract
import com.zf.mart.mvp.model.RecommendGoodsModel
import com.zf.mart.net.exception.ExceptionHandle

class RecommendGoodsPresenter : BasePresenter<RecommendGoodsContract.View>(), RecommendGoodsContract.Presenter {
    override fun requestRecommendGoods(id: String, sort_asc: String, page: Int) {
        checkViewAttached()
        mRootView?.showLoading()
        val disposable = model.getRecommendGoods(id, sort_asc, page, PER_PAGE)
            .subscribe({
                mRootView?.apply {
                    dismissLoading()
                    when (it.status) {
                        0 -> {
                            if (it.data != null) {
                                getRecommendGoods(it.data.goods_list)
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

    private val model by lazy { RecommendGoodsModel() }


}