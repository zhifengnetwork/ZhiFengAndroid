package com.zf.mart.mvp.model

import com.zf.mart.base.BaseBean
import com.zf.mart.mvp.bean.AdvertBean
import com.zf.mart.mvp.bean.ClassifyProductBean
import com.zf.mart.net.RetrofitManager
import com.zf.mart.scheduler.SchedulerUtils
import io.reactivex.Observable

class ClassifyProductModel {
    fun requestClassifyProduct(cat_id: String): Observable<BaseBean<List<ClassifyProductBean>>> {
        return RetrofitManager.service.getClassifyProduct(cat_id)
            .compose(SchedulerUtils.ioToMain())
    }

    fun getAdList(pid: String): Observable<BaseBean<AdvertBean>> {
        return RetrofitManager.service.getAdList(pid).compose(SchedulerUtils.ioToMain())
    }
}