package com.zf.mart.mvp.contract

import com.zf.mart.base.IBaseView
import com.zf.mart.base.IPresenter
import com.zf.mart.mvp.bean.GoodsSpecInfo
import com.zf.mart.mvp.bean.SpecBean

interface ActiveSpecContract{
    interface View : IBaseView {

        fun showError(msg: String, errorCode: Int)

        //获取商品规格
        fun setGoodsSpec(specBean: List<List<SpecBean>>)

        //根据规格获取商品信息
        fun setSpecInfo(bean: GoodsSpecInfo)

    }

    interface Presenter : IPresenter<View> {

        fun requestSpec(id: String)

        fun requestSpecInfo(key: String, goodsId: String)
    }
}