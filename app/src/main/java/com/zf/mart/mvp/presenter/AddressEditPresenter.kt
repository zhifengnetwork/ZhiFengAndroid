package com.zf.mart.mvp.presenter

import android.util.Log
import com.zf.mart.base.BasePresenter
import com.zf.mart.mvp.contract.AddressEditContract
import com.zf.mart.mvp.model.AddressEditModel
import com.zf.mart.net.exception.ExceptionHandle

class AddressEditPresenter:BasePresenter<AddressEditContract.View>(),AddressEditContract.Presenter{

    private val modelEdit: AddressEditModel by lazy { AddressEditModel() }

    override fun requestRegion(id: String) {
        checkViewAttached()

        mRootView?.showLoading()
        val disposable= modelEdit.requestRegion(id)
            .subscribe({
                mRootView?.apply {
                    dismissLoading()

                    when(it.status){
                        1 -> getRegion(it.data)
                        else -> showError(it.msg, it.status)
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


    override fun requestDelAddress(id: String) {
        checkViewAttached()
        mRootView?.showLoading()
        val disposable= modelEdit.requestDelAddressModel(id)
            .subscribe({
                mRootView?.apply {
                    dismissLoading()
                    when(it.status){
                        0 ->delAddressSuccess()
                        else -> showError(it.msg,it.status)
                    }
                }
            },{
                mRootView?.apply {
                    dismissLoading()
                    showError(ExceptionHandle.handleException(it), ExceptionHandle.errorCode)
                }
            })

        if (disposable != null) {
            addSubscription(disposable)
        }

    }


    override fun requestAddressEdit(consignee:String,mobile:String,province:String,city:String,district:String,address:String,is_default:String) {
        checkViewAttached()
        mRootView?.showLoading()
        val disposable = modelEdit.requestAddressEdit(consignee,mobile,province,city,district,address,is_default)
            .subscribe({
                mRootView?.apply {
                    when (it.status) {
                        0 -> setAddress(it.data)
                        else -> showError(it.msg, it.status)
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