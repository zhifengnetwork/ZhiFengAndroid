package com.zf.mart.mvp.model

import com.zf.mart.base.BaseBean
import com.zf.mart.mvp.bean.SecKillTimeBean
import com.zf.mart.net.RetrofitManager
import com.zf.mart.scheduler.SchedulerUtils
import io.reactivex.Observable

class SecKillTimeModel {

    fun getSecKillTime(): Observable<BaseBean<SecKillTimeBean>> {
        return RetrofitManager.service.getSecKillTimeList()
                .compose(SchedulerUtils.ioToMain())
    }
}