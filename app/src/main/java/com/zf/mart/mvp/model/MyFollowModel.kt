package com.zf.mart.mvp.model

import com.zf.mart.base.BaseBean
import com.zf.mart.mvp.bean.CommendBean
import com.zf.mart.mvp.bean.MyFollowBean
import com.zf.mart.net.RetrofitManager
import com.zf.mart.scheduler.SchedulerUtils
import io.reactivex.Observable

class MyFollowModel {
    fun getMyFollow(page: Int, num: Int): Observable<BaseBean<MyFollowBean>> {
        return RetrofitManager.service.getMyFollow(page, num)
            .compose(SchedulerUtils.ioToMain())
    }

    fun delCollectGoods(goods_id: String): Observable<BaseBean<Unit>> {
        return RetrofitManager.service.delCollectGoods(goods_id)
            .compose(SchedulerUtils.ioToMain())
    }

    fun getLoveGoods(type: String, page: Int, num: Int): Observable<BaseBean<CommendBean>> {
        return RetrofitManager.service.getLoveGoods(type, page, num)
            .compose(SchedulerUtils.ioToMain())
    }
}