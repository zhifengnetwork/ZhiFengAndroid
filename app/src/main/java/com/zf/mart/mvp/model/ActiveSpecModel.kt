package com.zf.mart.mvp.model

import com.zf.mart.base.BaseBean
import com.zf.mart.mvp.bean.GoodsSpecBean
import com.zf.mart.mvp.bean.LoginBean
import com.zf.mart.mvp.bean.SpecBean
import com.zf.mart.net.RetrofitManager
import com.zf.mart.scheduler.SchedulerUtils
import io.reactivex.Observable

class ActiveSpecModel {

    fun requestSpec(id: String): Observable<BaseBean<List<List<SpecBean>>>> {
        return RetrofitManager.service.requestGoodsSpec(id)
                .compose(SchedulerUtils.ioToMain())
    }

    fun requestSpecInfo(key: String, goodsId: String): Observable<BaseBean<GoodsSpecBean>> {
        return RetrofitManager.service.requestSpecInfo(key, goodsId)
                .compose(SchedulerUtils.ioToMain())
    }
}