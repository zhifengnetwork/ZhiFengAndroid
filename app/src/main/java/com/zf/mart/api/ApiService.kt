package com.zf.mart.api


import com.zf.mart.base.BaseBean
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
    @POST("upload/test")
    @Multipart
    fun uploadMemberIcon(@Part partList: MultipartBody.Part): Observable<Unit>

    /**
     * 登录
     */
    @POST("user/login")
    @FormUrlEncoded
    fun login(@Field("mobile") mobile: String, @Field("password") password: String): Observable<BaseBean<Unit>>

    /**
     * 注册
     */
    @POST("user/reg")
    @FormUrlEncoded
    fun register(@Field("mobile") mobile: String, @Field("password") password: String): Observable<BaseBean<Unit>>

}