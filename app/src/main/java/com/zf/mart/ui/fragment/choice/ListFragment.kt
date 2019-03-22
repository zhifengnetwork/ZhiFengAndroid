package com.zf.mart.ui.fragment.choice

import com.zf.mart.R
import com.zf.mart.base.BaseFragment

class ListFragment:BaseFragment(){
    companion object {
        fun getInstance():ListFragment{
            return ListFragment()
        }
    }
    override fun getLayoutId(): Int = R.layout.fragment_choice_list

    override fun initView() {

    }

    override fun lazyLoad() {

    }

    override fun initEvent() {

    }

}