package com.zf.mart.mvp.contract

import com.zf.mart.base.IBaseView
import com.zf.mart.base.IPresenter
import com.zf.mart.mvp.bean.AddressEditBean

interface AddressEditContract{
    interface View:IBaseView{
        fun showError(msg: String, errorCode: Int)

        fun setAddress(bean: List<AddressEditBean>)
    }
interface Presenter : IPresenter<View> {
    fun requestAddressEdit( )

}
}