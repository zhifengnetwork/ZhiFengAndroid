package com.zf.mart.mvp.model

import com.zf.mart.base.BaseBean
import com.zf.mart.mvp.bean.CommendBean
import com.zf.mart.net.RetrofitManager
import com.zf.mart.scheduler.SchedulerUtils
import io.reactivex.Observable

class ShareGoodsModel {
    fun getShareGoods(is_distribut: Int, page: Int, num: Int): Observable<BaseBean<CommendBean>> {
        return RetrofitManager.service.getShareGoods(is_distribut, page, num)
            .compose(SchedulerUtils.ioToMain())
    }
}