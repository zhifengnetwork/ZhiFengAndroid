package com.zf.mart.mvp.model

import com.zf.mart.base.BaseBean
import com.zf.mart.mvp.bean.MaterialListBean
import com.zf.mart.net.RetrofitManager
import com.zf.mart.scheduler.SchedulerUtils
import io.reactivex.Observable

class MaterialListModel {
    fun getMaterialList(cid: String, page: Int, num: Int): Observable<BaseBean<MaterialListBean>> {
        return RetrofitManager.service.getMaterialList(cid, page, num).compose(SchedulerUtils.ioToMain())
    }
}