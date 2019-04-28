package com.zf.mart.mvp.contract

import com.zf.mart.base.IBaseView
import com.zf.mart.base.IPresenter
import com.zf.mart.mvp.bean.FollowShopList
import com.zf.mart.mvp.bean.MyFollowShop

interface MyFollowShopContract{
    interface View:IBaseView{
        fun showError(msg: String, errorCode: Int)

        fun getMyFollowShop(bean:List<FollowShopList>)
    }
    interface Presenter:IPresenter<View>{
        fun requestMyFollowShop()
    }
}