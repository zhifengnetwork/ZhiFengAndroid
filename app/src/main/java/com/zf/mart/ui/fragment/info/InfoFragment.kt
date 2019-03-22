package com.zf.mart.ui.fragment.info

import androidx.navigation.fragment.findNavController
import com.zf.mart.R
import com.zf.mart.base.BaseFragment
import com.zf.mart.ui.activity.AddressActivity
import kotlinx.android.synthetic.main.fragment_info.*


class InfoFragment : BaseFragment() {

    override fun getLayoutId(): Int = R.layout.fragment_info

    override fun initView() {
    }

    override fun lazyLoad() {
    }

    override fun initEvent() {

        //个人信息
        userLayout.setOnClickListener {
            findNavController().navigate(R.id.userAction)
        }

        //地址管理
        addressLayout.setOnClickListener {
            AddressActivity.actionStart(context)
        }

    }
}