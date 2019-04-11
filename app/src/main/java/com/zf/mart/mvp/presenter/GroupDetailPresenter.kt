package com.zf.mart.mvp.presenter

import com.zf.mart.base.BasePresenter
import com.zf.mart.mvp.contract.GroupDetailContract
import com.zf.mart.mvp.model.GroupDetailModel
import com.zf.mart.net.exception.ExceptionHandle

class GroupDetailPresenter : BasePresenter<GroupDetailContract.View>(), GroupDetailContract.Presenter {

    private val model: GroupDetailModel by lazy { GroupDetailModel() }

    override fun requestGroupDetail(id: String) {
        checkViewAttached()
        mRootView?.showLoading()
        val disposable = model.getGroupDetail(id)
            .subscribe({
                mRootView?.apply {
                    dismissLoading()
                    when (it.status) {
                        0 -> if (it.data != null) {
                            setGroupDetail(it.data)
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