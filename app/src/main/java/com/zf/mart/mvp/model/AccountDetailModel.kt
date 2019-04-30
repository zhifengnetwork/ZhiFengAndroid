package com.zf.mart.mvp.model

import com.zf.mart.base.BaseBean
import com.zf.mart.mvp.bean.AccountDetailBean
import com.zf.mart.net.RetrofitManager
import com.zf.mart.scheduler.SchedulerUtils
import io.reactivex.Observable

class AccountDetailModel {
    fun getAccountDetail(type: String, page: Int, num: Int): Observable<BaseBean<AccountDetailBean>> {
        return RetrofitManager.service.getAccountInfo(type, page, num)
            .compose(SchedulerUtils.ioToMain())
    }
}