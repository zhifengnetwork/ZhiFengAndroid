package com.zf.mart.mvp.model

import com.zf.mart.base.BaseBean
import com.zf.mart.mvp.bean.AddressBean
import com.zf.mart.net.RetrofitManager
import com.zf.mart.scheduler.SchedulerUtils
import io.reactivex.Observable

class AddressModel {
    fun requestAddress(): Observable<BaseBean<List<AddressBean>>> {
        return RetrofitManager.service.getAddressList()
            .compose(SchedulerUtils.ioToMain())
    }
}