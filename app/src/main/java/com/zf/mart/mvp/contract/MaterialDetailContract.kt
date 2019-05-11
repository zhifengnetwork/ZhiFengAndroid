package com.zf.mart.mvp.contract

import com.zf.mart.base.IBaseView
import com.zf.mart.base.IPresenter
import com.zf.mart.mvp.bean.MaterialDetailBean

interface MaterialDetailContract {
    interface View : IBaseView {
        fun showError(msg: String, errorCode: Int)

        fun getMaterialDetail(bean: MaterialDetailBean)
    }

    interface Presenter : IPresenter<View> {
        fun requestMaterialDetail(id: String)
    }
}