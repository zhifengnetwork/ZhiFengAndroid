package com.zf.mart.mvp.model

import com.zf.mart.base.BaseBean
import com.zf.mart.mvp.bean.MyFollowShop
import com.zf.mart.net.RetrofitManager
import com.zf.mart.scheduler.SchedulerUtils
import io.reactivex.Observable

class MyFollowShopModel {
    fun getMyFollowShop(): Observable<BaseBean<MyFollowShop>> {
        return RetrofitManager.service.getMyFollowShop().compose(SchedulerUtils.ioToMain())
    }
}