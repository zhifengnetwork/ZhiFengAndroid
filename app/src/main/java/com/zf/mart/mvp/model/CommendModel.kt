package com.zf.mart.mvp.model

import com.zf.mart.api.UriConstant
import com.zf.mart.base.BaseBean
import com.zf.mart.mvp.bean.AppSignBean
import com.zf.mart.mvp.bean.CommendBean
import com.zf.mart.net.RetrofitManager
import com.zf.mart.scheduler.SchedulerUtils
import io.reactivex.Observable

class CommendModel {
    fun requestCommend(type: String, page: Int): Observable<BaseBean<CommendBean>> {
        return RetrofitManager.service.requestCommend(type, page, UriConstant.PER_PAGE)
                .compose(SchedulerUtils.ioToMain())
    }

    fun requestAppSign(): Observable<BaseBean<AppSignBean>> {
        return RetrofitManager.service.requestAppSign().compose(SchedulerUtils.ioToMain())
    }
}