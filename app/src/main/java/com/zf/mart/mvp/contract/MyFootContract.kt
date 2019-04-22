package com.zf.mart.mvp.contract

import com.zf.mart.base.IBaseView
import com.zf.mart.base.IPresenter

interface MyFootContract{
    interface View:IBaseView{
        fun showError(msg: String, errorCode: Int)
        //我的足迹
        fun getMyFoot()
        //编辑足迹
        fun setMyFoot()
        //清空足迹
        fun clearMyFoot()
    }
    interface Presenter:IPresenter<View>{
        fun requesetMyFoot()

        fun requestsetMyFoot(visit_ids:List<String>)

        fun requestclearMyFoot()
    }
}