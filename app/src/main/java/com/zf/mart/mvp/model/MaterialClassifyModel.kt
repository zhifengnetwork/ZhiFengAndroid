package com.zf.mart.mvp.model

import com.zf.mart.base.BaseBean
import com.zf.mart.mvp.bean.MaterialClassifyBean
import com.zf.mart.mvp.bean.MaterialListBean
import com.zf.mart.net.RetrofitManager
import com.zf.mart.scheduler.SchedulerUtils
import io.reactivex.Observable

class MaterialClassifyModel {

    fun getMaterialClassify(): Observable<BaseBean<MaterialClassifyBean>> {
        return RetrofitManager.service.getMaterialClassify().compose(SchedulerUtils.ioToMain())
    }
}