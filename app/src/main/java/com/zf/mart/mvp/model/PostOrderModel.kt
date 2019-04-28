package com.zf.mart.mvp.model

import com.zf.mart.base.BaseBean
import com.zf.mart.mvp.bean.PostOrderBean
import com.zf.mart.net.RetrofitManager
import com.zf.mart.scheduler.SchedulerUtils
import io.reactivex.Observable

class PostOrderModel {
    fun requestPostOrder(act: Int,
                         address_id: String,
                         invoice_title: String,
                         taxpayer: String,
                         invoice_desc: String,
                         coupon_id: String,
                         pay_points: String,
                         user_money: String,
                         user_note: String,
                         pay_pwd: String,
                         goods_id: String,
                         goods_num: String,
                         item_id: String,
                         action: String,
                         shop_id: String,
                         take_time: String,
                         consignee: String,
                         mobile: String): Observable<BaseBean<PostOrderBean>> {
        return RetrofitManager.service.requestPostOrder(act, address_id, invoice_title, taxpayer, invoice_desc, coupon_id, pay_points, user_money, user_note, pay_pwd, goods_id, goods_num, item_id, action, shop_id, take_time, consignee, mobile)
                .compose(SchedulerUtils.ioToMain())
    }
}