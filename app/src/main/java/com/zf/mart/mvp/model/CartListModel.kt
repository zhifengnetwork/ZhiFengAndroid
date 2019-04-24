package com.zf.mart.mvp.model

import com.zf.mart.api.UriConstant
import com.zf.mart.base.BaseBean
import com.zf.mart.mvp.bean.CartBean
import com.zf.mart.net.RetrofitManager
import com.zf.mart.scheduler.SchedulerUtils
import io.reactivex.Observable

class CartListModel {
    fun getCartList(page: Int): Observable<BaseBean<CartBean>> {
        return RetrofitManager.service.getCartList(page, UriConstant.PER_PAGE)
                .compose(SchedulerUtils.ioToMain())
    }
}