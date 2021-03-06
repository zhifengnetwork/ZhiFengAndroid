package com.zf.mart.mvp.model

import com.zf.mart.base.BaseBean
import com.zf.mart.mvp.bean.AppSignBean
import com.zf.mart.mvp.bean.AppSignDayBean
import com.zf.mart.net.RetrofitManager
import com.zf.mart.scheduler.SchedulerUtils
import io.reactivex.Observable

class AppSignDayModel {
    fun requestAppSign(): Observable<BaseBean<AppSignBean>> {
        return RetrofitManager.service.requestAppSign().compose(SchedulerUtils.ioToMain())
    }
    fun getAppSignDay(): Observable<BaseBean<AppSignDayBean>> {
        return RetrofitManager.service.getSignDay().compose(SchedulerUtils.ioToMain())
    }
}