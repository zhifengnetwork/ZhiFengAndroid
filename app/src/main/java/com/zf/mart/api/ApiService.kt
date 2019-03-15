package com.zf.mart.api


import com.zf.mart.base.BaseBean
import io.reactivex.Observable
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

/**
 * Api 接口
 */

interface ApiService {


//    @POST("cook/query?")
//    @FormUrlEncoded
//    fun getFood(@Field("key") key: String, @Field("menu") menu: String, @Query("pn") pn: Int): Observable<FoodBean>


    /**
     * 收款账户设置
     */
    @POST("/api/rider/account/update")
    @FormUrlEncoded
    fun setBankAccount(@Field("attribute") attribute: String): Observable<BaseBean<Unit>>



}