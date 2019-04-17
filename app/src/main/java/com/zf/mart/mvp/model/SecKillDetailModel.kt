package com.zf.mart.mvp.model

import com.zf.mart.base.BaseBean
import com.zf.mart.mvp.bean.LoginBean
import com.zf.mart.mvp.bean.SecKillDetailBean
import com.zf.mart.net.RetrofitManager
import com.zf.mart.scheduler.SchedulerUtils
import io.reactivex.Observable

class SecKillDetailModel {
    fun getSecKillDetail(id: String): Observable<BaseBean<SecKillDetailBean>> {
        return RetrofitManager.service.getSecKillDetail(id)
            .compose(SchedulerUtils.ioToMain())
    }
}