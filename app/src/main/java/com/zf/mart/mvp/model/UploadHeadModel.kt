package com.zf.mart.mvp.model

import com.zf.mart.net.RetrofitManager
import com.zf.mart.scheduler.SchedulerUtils
import io.reactivex.Observable
import okhttp3.MultipartBody

class UploadHeadModel {

    fun uploadHead(head: MultipartBody.Part): Observable<Unit> {
        return RetrofitManager.service.uploadMemberIcon(head)
            .compose(SchedulerUtils.ioToMain())
    }

}
