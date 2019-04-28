package com.zf.mart.mvp.presenter

import com.zf.mart.base.BasePresenter
import com.zf.mart.mvp.contract.MyMemberContract
import com.zf.mart.mvp.model.MyMemberModel
import com.zf.mart.net.exception.ExceptionHandle

class MyMemberPresenter:BasePresenter<MyMemberContract.View>(),MyMemberContract.Presenter{
    private val model by lazy { MyMemberModel() }
    override fun requestMyMember() {
        checkViewAttached()
        mRootView?.showLoading()
        val disposable = model.getMyMember()
            .subscribe({
                mRootView?.apply {
                    dismissLoading()
                    when (it.status) {
                        0->{if (it.data!=null){
                            getMyMember(it.data)
                        }}
                        else ->showError(it.msg, it.status)
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