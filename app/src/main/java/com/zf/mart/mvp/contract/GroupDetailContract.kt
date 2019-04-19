package com.zf.mart.mvp.contract

import com.zf.mart.base.IBaseView
import com.zf.mart.base.IPresenter
import com.zf.mart.mvp.bean.GroupDetailBean
import com.zf.mart.mvp.bean.GroupMemberList

interface GroupDetailContract {

    interface View : IBaseView {

        fun showError(msg: String, errorCode: Int)

        fun setGroupDetail(bean: GroupDetailBean)

        fun setGroupMember(bean: List<GroupMemberList>)
    }

    interface Presenter : IPresenter<View> {
        fun requestGroupDetail(id: String)

        fun requestGroupMember(teamId: String)

    }

}