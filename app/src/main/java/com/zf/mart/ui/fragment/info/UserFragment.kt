package com.zf.mart.ui.fragment.info

import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import com.zf.mart.R
import com.zf.mart.base.BaseFragment

class UserFragment : BaseFragment() {

    override fun getLayoutId(): Int = R.layout.fragment_user

    override fun initView() {
        activity?.findViewById<TextView>(R.id.titleName)?.text = "userFragment"
        activity?.findViewById<LinearLayout>(R.id.rightLayout)?.visibility = View.INVISIBLE
    }

    override fun lazyLoad() {
    }

    override fun initEvent() {
    }
}