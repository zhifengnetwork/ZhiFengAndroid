package com.zf.mart.mvp.model

import com.zf.mart.base.BaseBean
import com.zf.mart.net.RetrofitManager
import com.zf.mart.scheduler.SchedulerUtils
import io.reactivex.Observable

class BindZfbModel {
    fun setBindZfb(zfb_account: String, realname: String): Observable<BaseBean<Unit>> {
        return RetrofitManager.service.setBindZfb(zfb_account, realname)
            .compose(SchedulerUtils.ioToMain())
    }
}