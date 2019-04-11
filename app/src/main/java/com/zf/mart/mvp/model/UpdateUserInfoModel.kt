package com.zf.mart.mvp.model

import com.zf.mart.base.BaseBean
import com.zf.mart.mvp.bean.ChangeUserBean
import com.zf.mart.net.RetrofitManager
import com.zf.mart.scheduler.SchedulerUtils
import io.reactivex.Observable
import okhttp3.MultipartBody

class UpdateUserInfoModel {

    fun uploadHead(head: MultipartBody.Part): Observable<BaseBean<String>> {
        return RetrofitManager.service.uploadMemberIcon(head)
            .compose(SchedulerUtils.ioToMain())
    }

    fun changeUserInfo(
        nickname: String,
        mobile: String,
        sex: String,
        birthyear: String,
        birthmonth: String,
        birthday: String
    ): Observable<BaseBean<ChangeUserBean>> {
        return RetrofitManager.service.updateUserInfo(nickname, mobile, sex, birthyear, birthmonth, birthday)
            .compose(SchedulerUtils.ioToMain())
    }

}
