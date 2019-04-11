package com.zf.mart.mvp.model

import android.util.Log
import com.zf.mart.base.BaseBean
import com.zf.mart.mvp.bean.AddAddressBean
import com.zf.mart.mvp.bean.RegionBean
import com.zf.mart.net.RetrofitManager
import com.zf.mart.scheduler.SchedulerUtils
import io.reactivex.Observable

class AddressEditModel{
    fun requestAddressEdit(consignee:String,mobile:String,province:String,city:String,district:String,address:String,is_default:String): Observable<BaseBean<List<AddAddressBean>>> {
        return RetrofitManager.service.setAddressList(consignee,mobile,province,city,district,address,is_default)
            .compose(SchedulerUtils.ioToMain())
    }

    fun requestDelAddressModel(id:String): Observable<BaseBean<Unit>> {
        return RetrofitManager.service.delAddress(id)
            .compose(SchedulerUtils.ioToMain())
    }

    fun requestRegion(id:String): Observable<BaseBean<List<RegionBean>>> {

        return RetrofitManager.service.getRegion(id)
            .compose(SchedulerUtils.ioToMain())
    }

}