package com.zf.mart.mvp.model

import com.zf.mart.base.BaseBean
import com.zf.mart.mvp.bean.MessageBean
import com.zf.mart.net.RetrofitManager
import com.zf.mart.scheduler.SchedulerUtils
import io.reactivex.Observable

class MessageModel {
    fun getMessage(page: Int, num: Int, type: String): Observable<BaseBean<MessageBean>> {
        return RetrofitManager.service.getMessage(page, num, type)
            .compose(SchedulerUtils.ioToMain())
    }
}