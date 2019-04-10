package com.zf.mart.api


import com.zf.mart.base.BaseBean
import com.zf.mart.mvp.bean.*
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
    @GET("api/order/order_list")
    fun getOrderList(
        @Query("type") type: String,
        @Query("page") page: Int,
        @Query("perPage") perPage: Int
    ): Observable<BaseBean<List<OrderListBean>>>

    /**
     * 地址列表
     */
    @GET("api/user/address_list")
    fun getAddressList(): Observable<BaseBean<List<AddressBean>>>

    /**
     * 添加地址
     */
     @POST("api/User/add_address")
     fun setAddressList():Observable<BaseBean<List<AddressEditBean>>>

    /**
     * 删除地址
     */
    @GET("api/User/del_address")


    /**
     * 订单详情
     */
    @GET("api/order/order_detail")
    fun getOrderDetail(@Query("id") id: String): Observable<BaseBean<OrderDetailBean>>

    /**
     * 购物车列表
     */
    @GET("api/cart/cartlist")
    fun getCartList(): Observable<BaseBean<List<ShopList>>>

    /**
     * 分类左边标题
     */
    @GET("api/goods/categoryList")
    fun getClassifyList():Observable<BaseBean<List<ClassifyBean>>>

    /**
     * 分类右边商品列表
     */
    @GET("api/goods/Products")
    fun getClassifyProduct(@Query("cat_id") cat_id: String):Observable<BaseBean<List<ClassifyProductBean>>>
}