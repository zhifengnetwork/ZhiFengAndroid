package com.zf.mart.mvp.presenter




import com.zf.mart.base.BasePresenter
import com.zf.mart.mvp.contract.ClassifyContract
import com.zf.mart.mvp.model.ClassifyModel
import com.zf.mart.net.exception.ExceptionHandle


class ClassifyPresenter : BasePresenter<ClassifyContract.View>(), ClassifyContract.Presenter{
    override fun requestClassify() {
        checkViewAttached()
        mRootView?.showLoading()
        val disposable=model.requestClassify()
            .subscribe({
                mRootView?.apply {
                    dismissLoading()
                    when(it.status){
                        0 ->setTitle(it.data)
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


    private val model: ClassifyModel by lazy { ClassifyModel() }

}