package com.zf.mart.mvp.contract

import com.zf.mart.base.IBaseView
import com.zf.mart.base.IPresenter

interface MyfollowContract{
    interface View:IBaseView{
        fun showError(msg: String, errorCode: Int)
        fun getMyfollowSuccess()
    }
    interface Presenter:IPresenter<View>{
        fun requestMyfollow()
    }
}