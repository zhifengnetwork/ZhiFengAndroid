package com.zf.mart.mvp.model

import com.zf.mart.base.BaseBean
import com.zf.mart.mvp.bean.AchievementDetailBean
import com.zf.mart.net.RetrofitManager
import com.zf.mart.scheduler.SchedulerUtils
import io.reactivex.Observable

class AchievementModel {
    fun getAchievement(): Observable<BaseBean<AchievementDetailBean>> {
        return RetrofitManager.service.getAchievement()
            .compose(SchedulerUtils.ioToMain())
    }
}