package com.zf.mart.mvp.presenter

import com.zf.mart.api.UriConstant
import com.zf.mart.base.BasePresenter
import com.zf.mart.mvp.contract.MaterialListContract
import com.zf.mart.mvp.model.MaterialListModel
import com.zf.mart.net.exception.ExceptionHandle

class MaterialListPresenter : BasePresenter<MaterialListContract.View>(), MaterialListContract.Presenter {
    private val model by lazy { MaterialListModel() }
    private var mPage = 1
    override fun requestMaterialList(cid: String, page: Int?) {
        checkViewAttached()
        mPage = page ?: mPage
        mRootView?.showLoading()
        val disposable = model.getMaterialList(cid, mPage, UriConstant.PER_PAGE)
            .subscribe({
                mRootView?.apply {
                    when (it.status) {
                        0 -> {
                            if (it.data != null) {
                                if (mPage == 1) {
                                    if (it.data.list.isNotEmpty()) {
                                        getMaterialList(it.data.list)
                                    } else {
                                        freshEmpty()
                                    }
                                } else {
                                    setLoadMore(it.data.list)
                                }
                                if (it.data.list.size < UriConstant.PER_PAGE) {
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