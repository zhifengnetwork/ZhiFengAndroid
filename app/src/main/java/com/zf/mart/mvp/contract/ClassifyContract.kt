package com.zf.mart.mvp.contract

import com.zf.mart.base.IBaseView
import com.zf.mart.base.IPresenter
import com.zf.mart.mvp.bean.ClassifyBean

interface ClassifyContract{
    interface View:IBaseView{
        fun showError(msg: String, errorCode: Int)
        fun setTitle(bean: List<ClassifyBean>)
    }
    interface Presenter:IPresenter<View>{
        fun requestClassify()
    }
}