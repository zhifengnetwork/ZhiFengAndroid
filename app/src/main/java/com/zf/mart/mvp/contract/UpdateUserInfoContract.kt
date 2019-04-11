package com.zf.mart.mvp.contract

import com.zf.mart.base.BaseBean
import com.zf.mart.base.IBaseView
import com.zf.mart.base.IPresenter
import com.zf.mart.mvp.bean.ChangeUserBean
import okhttp3.MultipartBody

interface UpdateUserInfoContract {

    interface View : IBaseView {

        fun showError(msg: String, errorCode: Int)
        fun setHead(bean: BaseBean<String>)
        fun setChangeUserInfo(bean: BaseBean<ChangeUserBean>)
    }

    interface Presenter : IPresenter<View> {
        fun upLoadHead(head: MultipartBody.Part)
        fun changeUserInfo(
            nickname: String,
            mobile: String,
            sex: String,
            birthyear: String,
            birthmonth: String,
            birthday: String
        )
    }

}