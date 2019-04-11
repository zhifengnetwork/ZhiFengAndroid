package com.zf.mart.mvp.model

import com.zf.mart.base.BaseBean
import com.zf.mart.mvp.bean.GroupDetailBean
import com.zf.mart.net.RetrofitManager
import com.zf.mart.scheduler.SchedulerUtils
import io.reactivex.Observable

class GroupDetailModel {
    fun getGroupDetail(id: String): Observable<BaseBean<GroupDetailBean>> {
        return RetrofitManager.service.getGroupDetail(id)
            .compose(SchedulerUtils.ioToMain())
    }
}