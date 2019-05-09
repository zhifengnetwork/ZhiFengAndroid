package com.zf.mart.mvp.model

import com.zf.mart.base.BaseBean
import com.zf.mart.mvp.bean.BonusBean
import com.zf.mart.net.RetrofitManager
import com.zf.mart.scheduler.SchedulerUtils
import io.reactivex.Observable

class BonusModel{
    fun getBonus():Observable<BaseBean<BonusBean>>{
        return RetrofitManager.service.getBonus().compose(SchedulerUtils.ioToMain())
    }
}