package com.zf.mart.mvp.model

import com.zf.mart.base.BaseBean
import com.zf.mart.net.RetrofitManager
import com.zf.mart.scheduler.SchedulerUtils
import io.reactivex.Observable

class LoginModel {
    fun login(phone: String, pwd: String): Observable<BaseBean<Unit>> {
        return RetrofitManager.service.login(phone, pwd)
            .compose(SchedulerUtils.ioToMain())
    }
}