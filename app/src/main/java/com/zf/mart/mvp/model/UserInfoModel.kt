package com.zf.mart.mvp.model

import com.zf.mart.base.BaseBean
import com.zf.mart.mvp.bean.UserInfoBean
import com.zf.mart.net.RetrofitManager
import com.zf.mart.scheduler.SchedulerUtils
import io.reactivex.Observable

class UserInfoModel {
    fun requestUserInfo(): Observable<BaseBean<UserInfoBean>> {
        return RetrofitManager.service.getUserInfo()
            .compose(SchedulerUtils.ioToMain())
    }
}