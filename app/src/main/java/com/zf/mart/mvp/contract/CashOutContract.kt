package com.zf.mart.mvp.contract

import com.zf.mart.base.IBaseView
import com.zf.mart.base.IPresenter
import com.zf.mart.mvp.bean.BonusBean

interface CashOutContract{
    interface View:IBaseView{
        fun showError(msg: String, errorCode: Int)

        fun requestCashOutSuccess(msg: String)

        fun getBonus(bean: BonusBean)
    }
    interface Presenter:IPresenter<View>{
        fun requestCashOut(paypwd:String,money:String,bank_name:String,bank_card:String,realname:String)

        fun requestBonus()
    }
}