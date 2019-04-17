package com.zf.mart.mvp.model

import com.zf.mart.api.UriConstant
import com.zf.mart.base.BaseBean
import com.zf.mart.mvp.bean.SecKillListBean
import com.zf.mart.net.RetrofitManager
import com.zf.mart.scheduler.SchedulerUtils
import io.reactivex.Observable

class SecKillListModel {
    fun getSecKillList(startTime: String, endTime: String, page: Int): Observable<BaseBean<SecKillListBean>> {
        return RetrofitManager.service.getSecKillList(startTime, endTime, page, UriConstant.PER_PAGE)
                .compose(SchedulerUtils.ioToMain())
    }
}