package com.zf.mart.mvp.model

import com.zf.mart.api.UriConstant
import com.zf.mart.base.BaseBean
import com.zf.mart.mvp.bean.GroupBean
import com.zf.mart.net.RetrofitManager
import com.zf.mart.scheduler.SchedulerUtils
import io.reactivex.Observable

class GroupModel {
    fun getGroup(page: Int): Observable<BaseBean<List<GroupBean>>> {
        return RetrofitManager.service.getGroupList(page, UriConstant.PER_PAGE)
            .compose(SchedulerUtils.ioToMain())
    }
}