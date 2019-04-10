package com.zf.mart.mvp.contract

import com.zf.mart.base.BaseBean
import com.zf.mart.base.IBaseView
import com.zf.mart.base.IPresenter
import com.zf.mart.mvp.bean.ClassifyProductBean

interface ClassifyProductContract{
    interface View:IBaseView{
        fun showError(msg: String, errorCode: Int)
        fun setProduct(bean :List<ClassifyProductBean>)
    }
    interface Presenter:IPresenter<View>{
        fun requestClassifyProduct(cat_id:String)
    }
}