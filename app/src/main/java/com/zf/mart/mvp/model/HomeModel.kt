package com.zf.mart.mvp.model

import com.zf.mart.base.BaseBean
import com.zf.mart.mvp.bean.HomeBean
import com.zf.mart.net.RetrofitManager
import com.zf.mart.scheduler.SchedulerUtils
import io.reactivex.Observable

class HomeModel {
    fun requestHome(): Observable<BaseBean<HomeBean>> {
        return RetrofitManager.service.requestHome()
                .compose(SchedulerUtils.ioToMain())
    }
}