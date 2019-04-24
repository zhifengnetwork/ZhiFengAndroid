package com.zf.mart.mvp.model

import com.zf.mart.base.BaseBean
import com.zf.mart.mvp.bean.GoodsAttrBean
import com.zf.mart.net.RetrofitManager
import com.zf.mart.scheduler.SchedulerUtils
import io.reactivex.Observable

class GoodsAttrModel{
    fun getGoodsAttr(goods_id:String):Observable<BaseBean<GoodsAttrBean>>{
        return RetrofitManager.service.getGoodsAttr(goods_id)
            .compose(SchedulerUtils.ioToMain())
    }
}