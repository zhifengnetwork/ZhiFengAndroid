package com.zf.mart.mvp.contract

import com.zf.mart.base.IBaseView
import com.zf.mart.base.IPresenter
import com.zf.mart.mvp.bean.GoodsAttrBean

interface GoodsAttrContract{
    interface View:IBaseView{
        fun showError(msg: String, errorCode: Int)
        //商品属性
        fun getGoodsAttr(bean:GoodsAttrBean)
    }
    interface Presenter:IPresenter<View>{
        fun requestGoodsAttr(goods_id:String)
    }
}