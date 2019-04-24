package com.zf.mart.mvp.model

import com.zf.mart.base.BaseBean
import com.zf.mart.mvp.bean.MyVipBean
import com.zf.mart.net.RetrofitManager
import com.zf.mart.scheduler.SchedulerUtils
import io.reactivex.Observable

class MyVipModel {
    fun getMyVip(): Observable<BaseBean<List<MyVipBean>>> {
        return RetrofitManager.service.getMyVip().compose(SchedulerUtils.ioToMain())
    }
}