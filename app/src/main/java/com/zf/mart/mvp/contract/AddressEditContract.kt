package com.zf.mart.mvp.contract

import com.zf.mart.base.IBaseView
import com.zf.mart.base.IPresenter
import com.zf.mart.mvp.bean.AddAddressBean
import com.zf.mart.mvp.bean.RegionBean

interface AddressEditContract{

    interface View:IBaseView{

        fun showError(msg: String, errorCode: Int)

        fun delAddressSuccess()

        fun setAddress(bean: List<AddAddressBean>)

        fun getRegion(bean: List<RegionBean>)
    }
interface Presenter : IPresenter<View> {
       fun requestAddressEdit(consignee:String,mobile:String,province:String,city:String,district:String,address:String,is_default:String)
       fun requestDelAddress(id:String)
       fun requestRegion(id:String)

}
}