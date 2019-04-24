package com.zf.mart.mvp.model

import com.zf.mart.base.BaseBean
import com.zf.mart.mvp.bean.MyFollowBean
import com.zf.mart.net.RetrofitManager
import com.zf.mart.scheduler.SchedulerUtils
import io.reactivex.Observable

class MyFollowModel{
    fun getMyFollow():Observable<BaseBean<List<MyFollowBean>>>{
        return RetrofitManager.service.getMyFollow().compose(SchedulerUtils.ioToMain())
    }
}