package com.zf.mart.mvp.model

import com.zf.mart.base.BaseBean
import com.zf.mart.mvp.bean.MessageInfoBean
import com.zf.mart.net.RetrofitManager
import com.zf.mart.scheduler.SchedulerUtils
import io.reactivex.Observable

class MessageInfoModel {
    fun getMessageInfo(rec_id: String): Observable<BaseBean<MessageInfoBean>> {
        return RetrofitManager.service.getMessageInfo(rec_id)
            .compose(SchedulerUtils.ioToMain())
    }
}