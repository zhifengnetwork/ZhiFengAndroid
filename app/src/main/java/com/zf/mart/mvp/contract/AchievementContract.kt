package com.zf.mart.mvp.contract

import com.zf.mart.base.IBaseView
import com.zf.mart.base.IPresenter
import com.zf.mart.mvp.bean.AchievementList

interface AchievementContract{
    interface View:IBaseView{
        fun showError(msg: String, errorCode: Int)

        fun getAchievement(bean: List<AchievementList>)
    }
    interface Presenter:IPresenter<View>{
        fun requestAchievementDetail()
    }
}