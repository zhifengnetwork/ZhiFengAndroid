package com.zf.mart.mvp.model

import com.zf.mart.base.BaseBean
import com.zf.mart.net.RetrofitManager
import com.zf.mart.scheduler.SchedulerUtils
import io.reactivex.Observable
import okhttp3.RequestBody

class EvaluateModel {


    fun requestEvaluate(info: String): Observable<BaseBean<Unit>> {
        return RetrofitManager.service.requestEvaluate(info)
                .compose(SchedulerUtils.ioToMain())
    }
}