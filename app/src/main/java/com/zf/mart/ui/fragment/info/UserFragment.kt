package com.zf.mart.ui.fragment.info

import androidx.navigation.fragment.findNavController
import com.zf.mart.R
import com.zf.mart.base.BaseFragment
import com.zf.mart.ui.activity.TestUpHeadActivity
import kotlinx.android.synthetic.main.fragment_user.*

class UserFragment : BaseFragment() {

    override fun getLayoutId(): Int = R.layout.fragment_user

    override fun initView() {
    }

    override fun lazyLoad() {
    }

    override fun initEvent() {

        headLayout.setOnClickListener {
            TestUpHeadActivity.actionStart(context)
        }

        //修改用户名
        nameLayout.setOnClickListener {
            findNavController().navigate(R.id.changeNameAction)
        }
    }
}