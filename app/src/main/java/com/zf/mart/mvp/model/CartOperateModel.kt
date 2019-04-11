package com.zf.mart.mvp.model

import com.zf.mart.base.BaseBean
import com.zf.mart.net.RetrofitManager
import com.zf.mart.scheduler.SchedulerUtils
import io.reactivex.Observable

class CartOperateModel {

    fun setCount(id: String, num: String): Observable<BaseBean<Unit>> {
        return RetrofitManager.service.cartCount(id, num)
            .compose(SchedulerUtils.ioToMain())
    }
}