package com.zf.mart.mvp.model

import com.zf.mart.base.BaseBean
import com.zf.mart.mvp.bean.MyMemberBean
import com.zf.mart.net.RetrofitManager
import com.zf.mart.scheduler.SchedulerUtils
import io.reactivex.Observable

class MyMemberModel {
    fun getMyMember(page: Int, num: Int, next_user_id: String): Observable<BaseBean<List<MyMemberBean>>> {
        return RetrofitManager.service.getMyMember(page, num, next_user_id).compose(SchedulerUtils.ioToMain())
    }
}