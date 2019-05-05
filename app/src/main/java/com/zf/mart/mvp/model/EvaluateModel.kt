package com.zf.mart.mvp.model

import com.zf.mart.base.BaseBean
import com.zf.mart.mvp.bean.CommonBean
import com.zf.mart.net.RetrofitManager
import com.zf.mart.scheduler.SchedulerUtils
import io.reactivex.Observable
import okhttp3.MultipartBody

class EvaluateModel {


    fun requestEvaluate(info: String, orderId: String): Observable<BaseBean<Unit>> {
        return RetrofitManager.service.requestEvaluate(info, orderId)
            .compose(SchedulerUtils.ioToMain())
    }


    fun requestUploadImg(partList: MultipartBody.Part): Observable<BaseBean<CommonBean>> {
        return RetrofitManager.service.uploadCommonImg(partList)
            .compose(SchedulerUtils.ioToMain())
    }

}