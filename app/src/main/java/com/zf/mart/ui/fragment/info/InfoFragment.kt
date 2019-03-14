package com.zf.mart.ui.fragment.info

import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.navigation.fragment.findNavController
import com.zf.mart.R
import com.zf.mart.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_info.*


class InfoFragment : BaseFragment() {

    override fun getLayoutId(): Int = R.layout.fragment_info

    override fun initView() {
//        activity?.findViewById<TextView>(R.id.titleName)?.text = "infoFragment"
//        activity?.findViewById<LinearLayout>(R.id.rightLayout)?.visibility = View.INVISIBLE
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
            findNavController().navigate(R.id.addressAction)
        }
    }
}