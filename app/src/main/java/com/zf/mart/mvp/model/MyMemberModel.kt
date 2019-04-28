package com.zf.mart.mvp.model

import com.zf.mart.base.BaseBean
import com.zf.mart.mvp.bean.MyMemberBean
import com.zf.mart.net.RetrofitManager
import com.zf.mart.scheduler.SchedulerUtils
import io.reactivex.Observable

class MyMemberModel {
    fun getMyMember(): Observable<BaseBean<List<MyMemberBean>>> {
        return RetrofitManager.service.getMyMember().compose(SchedulerUtils.ioToMain())
    }
}