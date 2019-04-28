package com.zf.mart.mvp.model

import com.zf.mart.base.BaseBean
import com.zf.mart.mvp.bean.ShippingBean
import com.zf.mart.net.RetrofitManager
import com.zf.mart.scheduler.SchedulerUtils
import io.reactivex.Observable

class ShippingModel {
    fun requestShipping(orderId: String): Observable<BaseBean<ShippingBean>> {
        return RetrofitManager.service.requestShipping(orderId)
                .compose(SchedulerUtils.ioToMain())
    }
}