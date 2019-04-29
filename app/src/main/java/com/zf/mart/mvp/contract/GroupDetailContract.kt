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

        fun setAddCollect()

        fun setDelCollect()

    }

    interface Presenter : IPresenter<View> {
        fun requestGroupDetail(id: String)

        fun requestGroupMember(teamId: String)

        fun requestAddCollect(goods_id: String)

        fun requestDelCollect(goods_id: String)
    }

}