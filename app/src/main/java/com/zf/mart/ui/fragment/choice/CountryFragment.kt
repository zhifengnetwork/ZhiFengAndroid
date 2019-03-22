package com.zf.mart.ui.fragment.choice

import com.zf.mart.R
import com.zf.mart.base.BaseFragment
import com.zf.mart.ui.fragment.MessageFragment

class CountryFragment:BaseFragment(){

    companion object {
        fun getInstance():CountryFragment{
            return CountryFragment()
        }
    }

    override fun getLayoutId(): Int = R.layout.fragment_choice_country

    override fun initView() {

    }

    override fun lazyLoad() {

    }

    override fun initEvent() {

    }

}