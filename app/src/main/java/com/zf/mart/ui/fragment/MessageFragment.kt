package com.zf.mart.ui.fragment

import com.zf.mart.R
import com.zf.mart.base.BaseFragment

class MessageFragment:BaseFragment(){
    companion object {
        fun getInstance():MessageFragment{
            return MessageFragment()
        }
    }


                override fun getLayoutId(): Int = R.layout.fragment_message

    override fun initView() {

    }

    override fun lazyLoad() {

    }

    override fun initEvent() {

    }

}