package com.zf.mart.mvp.model

import com.zf.mart.base.BaseBean
import com.zf.mart.mvp.bean.DiscountBean
import com.zf.mart.net.RetrofitManager
import com.zf.mart.scheduler.SchedulerUtils
import io.reactivex.Observable

class DiscountModel {

    fun requestDisount(status: String): Observable<BaseBean<List<DiscountBean>>> {
        return RetrofitManager.service.getDiscount(status)
            .compose(SchedulerUtils.ioToMain())
    }
}