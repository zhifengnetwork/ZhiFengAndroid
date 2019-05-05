package com.zf.mart.mvp.model

import com.zf.mart.base.BaseBean
import com.zf.mart.mvp.bean.MyMemberOrderBean
import com.zf.mart.net.RetrofitManager
import com.zf.mart.scheduler.SchedulerUtils
import io.reactivex.Observable

class MemberOrderModel {
    fun getMemberOrder(page: Int, num: Int): Observable<BaseBean<MyMemberOrderBean>> {
        return RetrofitManager.service.getMyMemberOrder(page, num).compose(SchedulerUtils.ioToMain())
    }
}