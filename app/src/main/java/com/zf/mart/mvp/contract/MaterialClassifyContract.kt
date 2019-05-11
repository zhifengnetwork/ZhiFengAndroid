package com.zf.mart.mvp.contract

import com.zf.mart.base.IBaseView
import com.zf.mart.base.IPresenter
import com.zf.mart.mvp.bean.MaterialClassifyList
import com.zf.mart.mvp.bean.MaterialList

interface MaterialClassifyContract {
    interface View : IBaseView {
        fun showError(msg: String, errorCode: Int)

        fun getMaterialClassify(bean: List<MaterialClassifyList>)

    }

    interface Presenter : IPresenter<View> {
        fun requestMaterialClassify()

    }
}