package com.zf.mart.mvp.contract

import com.zf.mart.base.IBaseView

interface DelAddressContract{
    interface View:IBaseView{
        fun showError(msg: String, errorCode: Int
        fun delAddress(bean: List<AddressBean>)
    }
}