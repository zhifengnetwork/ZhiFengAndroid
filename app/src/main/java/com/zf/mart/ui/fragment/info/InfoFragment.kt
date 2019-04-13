package com.zf.mart.ui.fragment.info

import android.annotation.SuppressLint
import android.content.Intent
import androidx.lifecycle.Observer
import com.zf.mart.R
import com.zf.mart.api.UriConstant
import com.zf.mart.base.NotLazyBaseFragment
import com.zf.mart.livedata.UserInfoLiveData
import com.zf.mart.ui.activity.AddressActivity
import com.zf.mart.ui.activity.MainActivity
import com.zf.mart.ui.activity.UserActivity
import com.zf.mart.utils.GlideUtils
import com.zf.mart.utils.Preference
import kotlinx.android.synthetic.main.fragment_info.*


class InfoFragment : NotLazyBaseFragment() {

    override fun getLayoutId(): Int = R.layout.fragment_info

    override fun initView() {
    }

    @SuppressLint("SetTextI18n")
    override fun lazyLoad() {
        UserInfoLiveData.observe(viewLifecycleOwner, Observer { userInfo ->
            userInfo?.apply {
                nickName.text = nickname
                userName.text = "用户名:$realname"
                GlideUtils.loadUrlImage(context, UriConstant.BASE_URL + head_pic, userIcon)
            }
        })
    }

    override fun initEvent() {

        logOut.setOnClickListener {
            //退出登录->清空token、删除用户信息缓存
            Preference.clearPreference(UriConstant.TOKEN)
            UserInfoLiveData.value = null
            val intent = Intent(context, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK
            context?.startActivity(intent)
        }

        //个人信息
        userLayout.setOnClickListener {
            UserActivity.actionStart(context)
        }

        //地址管理
        addressLayout.setOnClickListener {
            AddressActivity.actionStart(context)
        }

    }
}