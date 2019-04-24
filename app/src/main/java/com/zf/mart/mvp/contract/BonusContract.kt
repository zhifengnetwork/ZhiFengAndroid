package com.zf.mart.mvp.contract

import com.zf.mart.base.IBaseView
import com.zf.mart.base.IPresenter
import com.zf.mart.mvp.bean.BonusBean

interface BonusContract{
    interface View:IBaseView{
        fun showError(msg: String, errorCode: Int)
        fun getBonus(bean: BonusBean)
    }
    interface Presenter:IPresenter<View>{
        fun requestBonus()
    }
}