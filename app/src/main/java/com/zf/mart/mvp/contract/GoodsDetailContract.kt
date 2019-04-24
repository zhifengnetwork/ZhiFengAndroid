package com.zf.mart.mvp.contract

import com.zf.mart.base.IBaseView
import com.zf.mart.base.IPresenter
import com.zf.mart.mvp.bean.*

interface GoodsDetailContract {
    interface View : IBaseView {
        fun showError(msg: String, errorCode: Int)
        //商品详情
        fun getGoodsDetail(bean: GoodsInfo)
        //商品评论
        fun setGoodEva(bean: List<GoodEvaList>)
        //获取地址列表
        fun getAddress(bean: List<AddressBean>)
        //获得商品运费
        fun getGoodsFreight(bean:GoodsFreightBean)

    }

    interface Presenter : IPresenter<View> {
        fun requestGoodsDetail(goods_id:String)

        fun requestGoodEva(goodId:String,type:Int,page: Int?)

        fun requestAddress()

        fun requestGoodsFreight(goods_id:String,region_id:String,buy_num:String)
    }
}