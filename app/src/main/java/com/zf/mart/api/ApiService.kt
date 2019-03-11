package com.zf.mart.api


import com.zf.mart.base.BaseBean
import io.reactivex.Observable
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

/**
 * Api 接口
 */

interface ApiService {

    /**
     * 获取菜谱
     * http://apis.juhe.cn/cook/query?key=a4fea2cd25b0b75244d16f459e8ef31f&menu=%E8%A5%BF%E7%BA%A2%E6%9F%BF&rn=10&pn=3
     */
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