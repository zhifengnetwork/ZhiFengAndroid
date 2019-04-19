package com.zf.mart.mvp.model

import com.zf.mart.api.UriConstant
import com.zf.mart.base.BaseBean
import com.zf.mart.mvp.bean.GoodEvaBean
import com.zf.mart.net.RetrofitManager
import com.zf.mart.scheduler.SchedulerUtils
import io.reactivex.Observable

class GoodEvaModel {

    fun getGoodEva(goodId: String, type: Int, page: Int): Observable<BaseBean<GoodEvaBean>> {
        return RetrofitManager.service.getGoodEva(goodId, type, page, UriConstant.PER_PAGE)
                .compose(SchedulerUtils.ioToMain())
    }
}