package com.zf.mart.mvp.model

import com.zf.mart.base.BaseBean
import com.zf.mart.mvp.bean.MyWalletBean
import com.zf.mart.net.RetrofitManager
import com.zf.mart.scheduler.SchedulerUtils
import io.reactivex.Observable

class MyWalletModel {
    fun getMyWallet(): Observable<BaseBean<MyWalletBean>> {
        return RetrofitManager.service.getMyWallet().compose(SchedulerUtils.ioToMain())
    }
}