package com.zf.mart.mvp.model

import com.zf.mart.base.BaseBean
import com.zf.mart.net.RetrofitManager
import com.zf.mart.scheduler.SchedulerUtils
import io.reactivex.Observable

class MyFootModel {
    fun getMyFoot(): Observable<BaseBean<Unit>> {
        return RetrofitManager.service.getMyFoot().compose(SchedulerUtils.ioToMain())
    }

    fun setMyFoot(visit_ids: List<String>): Observable<BaseBean<Unit>> {
        return RetrofitManager.service.setMyFoot(visit_ids).compose(SchedulerUtils.ioToMain())
    }

    fun clearMyFoot(): Observable<BaseBean<Unit>> {
        return RetrofitManager.service.clearMyFoot().compose(SchedulerUtils.ioToMain())
    }
}