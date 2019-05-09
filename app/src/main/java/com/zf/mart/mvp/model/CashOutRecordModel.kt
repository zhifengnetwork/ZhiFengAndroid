package com.zf.mart.mvp.model

import com.zf.mart.base.BaseBean
import com.zf.mart.mvp.bean.CashOutBean
import com.zf.mart.net.RetrofitManager
import com.zf.mart.scheduler.SchedulerUtils
import io.reactivex.Observable

class CashOutRecordModel {
    fun getCashOutRecordList(page: Int, num: Int): Observable<BaseBean<CashOutBean>> {
        return RetrofitManager.service.getCashOutList(page, num)
            .compose(SchedulerUtils.ioToMain())
    }
}