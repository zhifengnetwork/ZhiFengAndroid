package com.zf.mart.mvp.model

import com.zf.mart.api.UriConstant
import com.zf.mart.base.BaseBean
import com.zf.mart.mvp.bean.OrderListBean
import com.zf.mart.net.RetrofitManager
import com.zf.mart.scheduler.SchedulerUtils
import io.reactivex.Observable

class OrderListModel {
    fun requestOrderList(type: String, page: Int): Observable<BaseBean<List<OrderListBean>>> {
        return RetrofitManager.service.getOrderList(type, page, UriConstant.PER_PAGE)
            .compose(SchedulerUtils.ioToMain())
    }
}