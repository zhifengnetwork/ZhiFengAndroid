package com.zf.mart.mvp.contract

import com.zf.mart.base.IBaseView
import com.zf.mart.base.IPresenter
import com.zf.mart.mvp.bean.MyFollowBean

interface MyFollowContract{
    interface View:IBaseView{
        fun showError(msg: String, errorCode: Int)
        fun getMyFollowSuccess(bean:List<MyFollowBean>)
    }
    interface Presenter:IPresenter<View>{
        fun requestMyFollow()
    }
}