package com.zf.mart.api


import com.zf.mart.base.BaseBean
import com.zf.mart.mvp.bean.LoginBean
import com.zf.mart.mvp.bean.OrderListBean
import com.zf.mart.mvp.bean.UserInfoBean
import io.reactivex.Observable
import okhttp3.MultipartBody
import retrofit2.http.*


/**
 * Api 接口
 */

interface ApiService {

    /**
     * 上传头像
     */
    @POST("api/user/update_head_pic")
    @Multipart
    fun uploadMemberIcon(@Part partList: MultipartBody.Part): Observable<BaseBean<String>>

    /**
     * 登录
     */
    @POST("/api/user/login")
    @FormUrlEncoded
    fun login(
        @Field("mobile") mobile: String,
        @Field("password") password: String
    ): Observable<BaseBean<LoginBean>>

    /**
     * 注册
     */
    @POST("user/reg")
    @FormUrlEncoded
    fun register(@Field("mobile") mobile: String, @Field("password") password: String): Observable<BaseBean<Unit>>

    /**
     * 用户信息
     */
    @GET("/api/user/userinfo")
    fun getUserInfo(): Observable<BaseBean<UserInfoBean>>

    /**
     * 订单列表
     */
    @GET("api/user/register")
    fun getOrderList(): Observable<BaseBean<OrderListBean>>

}