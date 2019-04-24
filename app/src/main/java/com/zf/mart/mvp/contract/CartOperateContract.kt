package com.zf.mart.mvp.contract

import com.zf.mart.base.IBaseView
import com.zf.mart.base.IPresenter
import com.zf.mart.mvp.bean.CartPrice
import okhttp3.RequestBody
import org.json.JSONArray
import org.json.JSONObject

interface CartOperateContract {

    interface View : IBaseView {

        fun cartOperateError(msg: String, errorCode: Int)

        //修改数量
        fun setCount()

        //修改选中状态
        fun setSelect(bean: CartPrice)
    }

    interface Presenter : IPresenter<View> {

        fun requestCount(id: String, num: String)

        fun requestSelect(cart: RequestBody)

    }

}