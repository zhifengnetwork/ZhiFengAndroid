package com.zf.mart.mvp.model

import com.zf.mart.base.BaseBean
import com.zf.mart.net.RetrofitManager
import com.zf.mart.scheduler.SchedulerUtils
import io.reactivex.Observable

class ForgetPwdModel {

    fun requestContract(mobile: String, code: String): Observable<BaseBean<Unit>> {
        return RetrofitManager.service.requestContrast(mobile, code)
                .compose(SchedulerUtils.ioToMain())
    }

    fun requestChangePwd(mobile: String, password: String, password2: String): Observable<BaseBean<Unit>> {
        return RetrofitManager.service.requestChangePwd(mobile, password, password2)
                .compose(SchedulerUtils.ioToMain())
    }

    fun requestCode(scene: Int, mobile: String): Observable<BaseBean<Unit>> {
        return RetrofitManager.service.requestCode(scene, mobile)
                .compose(SchedulerUtils.ioToMain())
    }

}