package com.zf.mart.mvp.model

import com.zf.mart.base.BaseBean
import com.zf.mart.mvp.bean.AddressEditBean
import com.zf.mart.net.RetrofitManager
import com.zf.mart.scheduler.SchedulerUtils
import io.reactivex.Observable

class AddressEditModel{
    fun requestAddressEdit(): Observable<BaseBean<List<AddressEditBean>>> {
        return RetrofitManager.service.setAddressList()
            .compose(SchedulerUtils.ioToMain())
    }
}