package com.zf.mart.mvp.model

import com.zf.mart.api.UriConstant
import com.zf.mart.base.BaseBean
import com.zf.mart.mvp.bean.LoginBean
import com.zf.mart.mvp.bean.SearchBean
import com.zf.mart.mvp.bean.SearchList
import com.zf.mart.net.RetrofitManager
import com.zf.mart.scheduler.SchedulerUtils
import io.reactivex.Observable

class SearchModel {

    fun requestClassifySearch(
        id: String,
        brand_id: String,
        sort: String,
        sel: String,
        price: String,
        start_price: String,
        end_price: String,
        price_sort: String,
        page: Int
    ): Observable<BaseBean<SearchBean>> {
        return RetrofitManager.service.requestClassifySearch(
            id,
            brand_id,
            sort,
            sel,
            price,
            start_price,
            end_price,
            price_sort,
            page,
            UriConstant.PER_PAGE
        )
            .compose(SchedulerUtils.ioToMain())
    }

    fun requestSearch(
        q: String,
        id: String,
        brand_id: String,
        sort: String,
        sel: String,
        price: String,
        start_price: String,
        end_price: String,
        price_sort: String,
        page: Int
    ): Observable<BaseBean<SearchBean>> {
        return RetrofitManager.service.getSearchList(
            q,
            id,
            brand_id,
            sort,
            sel,
            price,
            start_price,
            end_price,
            price_sort,
            page,
            UriConstant.PER_PAGE
        )
            .compose(SchedulerUtils.ioToMain())
    }
}