package com.zf.mart.mvp.contract

import com.zf.mart.base.IBaseView
import com.zf.mart.base.IPresenter
import com.zf.mart.mvp.bean.AdvertList
import com.zf.mart.mvp.bean.BonusBean

interface BonusContract {
    interface View : IBaseView {
        fun showError(msg: String, errorCode: Int)

        fun getBonus(bean: BonusBean)
        //广告图
        fun getAdList(bean: List<AdvertList>)
    }

    interface Presenter : IPresenter<View> {
        fun requestBonus()

        fun requestAdList(pid: String)
    }
}