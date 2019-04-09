package com.zf.mart.mvp.model

import com.zf.mart.base.BaseBean
import com.zf.mart.mvp.bean.ClassifyBean
import com.zf.mart.net.RetrofitManager
import com.zf.mart.scheduler.SchedulerUtils
import io.reactivex.Observable

class ClassifyModel{
    fun requestClassify(): Observable<BaseBean<List<ClassifyBean>>>{
        return RetrofitManager.service.getClassifyList()
            .compose(SchedulerUtils.ioToMain())
    }
}