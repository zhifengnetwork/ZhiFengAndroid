package com.zf.mart.mvp.model

import com.zf.mart.base.BaseBean
import com.zf.mart.mvp.bean.BonusBean
import com.zf.mart.net.RetrofitManager
import com.zf.mart.scheduler.SchedulerUtils
import io.reactivex.Observable

class CashOutModel {
    fun requestCashOut(
        paypwd: String,
        money: String,
        bank_name: String,
        bank_card: String,
        realname: String
    ): Observable<BaseBean<Unit>> {
        return RetrofitManager.service.requestCashOut(paypwd, money, bank_name, bank_card, realname)
            .compose(SchedulerUtils.ioToMain())
    }

    fun getBonus(): Observable<BaseBean<BonusBean>> {
        return RetrofitManager.service.getBonus().compose(SchedulerUtils.ioToMain())
    }
}