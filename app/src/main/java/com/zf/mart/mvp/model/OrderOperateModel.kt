package com.zf.mart.mvp.model

import com.zf.mart.base.BaseBean
import com.zf.mart.net.RetrofitManager
import com.zf.mart.scheduler.SchedulerUtils
import io.reactivex.Observable

class OrderOperateModel {

    fun requestConfirmReceipt(orderId: String): Observable<BaseBean<Unit>> {
        return RetrofitManager.service.requestConfirmReceipt(orderId)
                .compose(SchedulerUtils.ioToMain())
    }

    fun requestCancelOrder(orderId: String): Observable<BaseBean<Unit>> {
        return RetrofitManager.service.requestCancelOrder(orderId)
                .compose(SchedulerUtils.ioToMain())
    }
}