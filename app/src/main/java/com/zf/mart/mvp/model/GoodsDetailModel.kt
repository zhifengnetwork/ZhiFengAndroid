package com.zf.mart.mvp.model

import com.zf.mart.api.UriConstant
import com.zf.mart.base.BaseBean
import com.zf.mart.mvp.bean.AddressBean
import com.zf.mart.mvp.bean.GoodEvaBean
import com.zf.mart.mvp.bean.GoodsDetailBean
import com.zf.mart.mvp.bean.GoodsFreightBean
import com.zf.mart.net.RetrofitManager
import com.zf.mart.scheduler.SchedulerUtils
import io.reactivex.Observable

class GoodsDetailModel {
    fun getGoodsDetail(goods_id: String): Observable<BaseBean<GoodsDetailBean>> {
        return RetrofitManager.service.getGoodsDetail(goods_id)
            .compose(SchedulerUtils.ioToMain())
    }

    fun getGoodEva(goodId: String, type: Int, page: Int): Observable<BaseBean<GoodEvaBean>> {
        return RetrofitManager.service.getGoodEva(goodId, type, page, UriConstant.PER_PAGE)
            .compose(SchedulerUtils.ioToMain())
    }

    fun requestAddress(): Observable<BaseBean<List<AddressBean>>> {
        return RetrofitManager.service.getAddressList()
            .compose(SchedulerUtils.ioToMain())
    }

    fun getGoodsFreight(goods_id: String, region_id: String, buy_num: String): Observable<BaseBean<GoodsFreightBean>> {
        return RetrofitManager.service.getGoodsFreight(goods_id, region_id, buy_num)
            .compose(SchedulerUtils.ioToMain())
    }
}