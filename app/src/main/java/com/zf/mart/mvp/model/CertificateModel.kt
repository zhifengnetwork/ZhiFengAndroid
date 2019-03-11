package com.zf.mart.mvp.model

import com.zf.mart.base.BaseBean
import com.zf.mart.net.RetrofitManager
import com.zf.mart.scheduler.SchedulerUtils
import io.reactivex.Observable

class CertificateModel {

    fun requestBankAccount(attribute: String): Observable<BaseBean<Unit>> {
        return RetrofitManager.service.setBankAccount(attribute)
                .compose(SchedulerUtils.ioToMain())
    }
}