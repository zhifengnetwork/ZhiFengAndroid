package com.zf.mart.mvp.model

import com.zf.mart.base.BaseBean
import com.zf.mart.mvp.bean.ShopList
import com.zf.mart.net.RetrofitManager
import com.zf.mart.scheduler.SchedulerUtils
import io.reactivex.Observable

class CartListModel {
    fun getCartList(): Observable<BaseBean<List<ShopList>>> {
        return RetrofitManager.service.getCartList()
            .compose(SchedulerUtils.ioToMain())
    }
}