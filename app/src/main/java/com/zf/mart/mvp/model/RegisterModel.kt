package com.zf.mart.mvp.model

import com.zf.mart.base.BaseBean
import com.zf.mart.net.RetrofitManager
import com.zf.mart.scheduler.SchedulerUtils
import io.reactivex.Observable

class RegisterModel {

    fun requestRegister(phone: String, password: String): Observable<BaseBean<Unit>> {
        return RetrofitManager.service.register(phone, password)
            .compose(SchedulerUtils.ioToMain())
    }

}