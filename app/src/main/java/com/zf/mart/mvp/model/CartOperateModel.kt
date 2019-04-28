package com.zf.mart.mvp.model

import com.zf.mart.base.BaseBean
import com.zf.mart.mvp.bean.CartSelectBean
import com.zf.mart.mvp.bean.SpecBean
import com.zf.mart.net.RetrofitManager
import com.zf.mart.scheduler.SchedulerUtils
import io.reactivex.Observable
import okhttp3.RequestBody

class CartOperateModel {

    fun setCount(id: String, num: Int): Observable<BaseBean<CartSelectBean>> {
        return RetrofitManager.service.cartCount(id, num)
                .compose(SchedulerUtils.ioToMain())
    }

    fun setCartSelect(cart: RequestBody): Observable<BaseBean<CartSelectBean>> {
        return RetrofitManager.service.requestCartSelect(cart)
                .compose(SchedulerUtils.ioToMain())
    }

    fun setCheckAll(type: Int): Observable<BaseBean<CartSelectBean>> {
        return RetrofitManager.service.requestCartCheckAll(type)
                .compose(SchedulerUtils.ioToMain())
    }

    fun setDeleteCart(id: RequestBody): Observable<BaseBean<CartSelectBean>> {
        return RetrofitManager.service.requestDeleteCart(id)
                .compose(SchedulerUtils.ioToMain())
    }

    fun requestSpec(id: String): Observable<BaseBean<SpecBean>> {
        return RetrofitManager.service.requestGoodsSpec(id)
                .compose(SchedulerUtils.ioToMain())
    }

    fun requestChangeSpec(cartId: String, itemId: String): Observable<BaseBean<CartSelectBean>> {
        return RetrofitManager.service.requestChangeSpec(cartId, itemId)
                .compose(SchedulerUtils.ioToMain())
    }

}