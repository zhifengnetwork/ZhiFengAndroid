package com.zf.mart.ui.fragment.info

import androidx.navigation.fragment.findNavController
import com.zf.mart.R
import com.zf.mart.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_user.*

class UserFragment : BaseFragment() {

    override fun getLayoutId(): Int = R.layout.fragment_user

    override fun initView() {
    }

    override fun lazyLoad() {
    }

    override fun initEvent() {
        //修改用户名
        nameLayout.setOnClickListener {
            findNavController().navigate(R.id.changeNameAction)
        }
    }
}