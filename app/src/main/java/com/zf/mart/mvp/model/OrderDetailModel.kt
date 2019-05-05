package com.zf.mart.mvp.model

import com.zf.mart.base.BaseBean
import com.zf.mart.mvp.bean.OrderDetailBean
import com.zf.mart.mvp.bean.OrderListBean
import com.zf.mart.net.RetrofitManager
import com.zf.mart.scheduler.SchedulerUtils
import io.reactivex.Observable

class OrderDetailModel {
    fun getOrderDetail(id: String): Observable<BaseBean<OrderListBean>> {
        return RetrofitManager.service.getOrderDetail(id)
            .compose(SchedulerUtils.ioToMain())
    }
}