package com.zf.mart.mvp.model

import com.zf.mart.base.BaseBean
import com.zf.mart.mvp.bean.MaterialDetailBean
import com.zf.mart.net.RetrofitManager
import com.zf.mart.scheduler.SchedulerUtils
import io.reactivex.Observable

class MaterialDetailModel {
    fun getMaterialDetail(id: String): Observable<BaseBean<MaterialDetailBean>> {
        return RetrofitManager.service.getMaterialDetail(id).compose(SchedulerUtils.ioToMain())
    }
}