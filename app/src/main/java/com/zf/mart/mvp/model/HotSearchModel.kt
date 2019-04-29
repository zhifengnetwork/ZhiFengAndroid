package com.zf.mart.mvp.model

import com.zf.mart.base.BaseBean
import com.zf.mart.net.RetrofitManager
import com.zf.mart.scheduler.SchedulerUtils
import io.reactivex.Observable

class HotSearchModel {
    fun requestHotSearch(): Observable<BaseBean<String>> {
        return RetrofitManager.service.requestHotSearch()
                .compose(SchedulerUtils.ioToMain())
    }
}