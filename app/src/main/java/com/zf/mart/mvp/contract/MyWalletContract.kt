package com.zf.mart.mvp.contract

import com.zf.mart.base.IBaseView
import com.zf.mart.base.IPresenter
import com.zf.mart.mvp.bean.MyWalletBean

interface MyWalletContract{
    interface View:IBaseView{
        fun showError(msg: String, errorCode: Int)

        fun getMyWallet(bean:MyWalletBean)
    }
    interface Presenter:IPresenter<View>{
        fun requestMyWallet()
    }
}
