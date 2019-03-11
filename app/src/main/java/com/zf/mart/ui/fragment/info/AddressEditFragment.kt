package com.zf.mart.ui.fragment.info

import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import com.zf.mart.R
import com.zf.mart.base.BaseFragment

/**
 * 新建收货人
 */
class AddressEditFragment : BaseFragment() {

    override fun getLayoutId(): Int = R.layout.fragment_address_edit

    private fun initToolBar() {
        activity?.findViewById<ImageView>(R.id.rightIcon)?.setImageResource(R.drawable.delete)
        activity?.findViewById<LinearLayout>(R.id.rightLayout)?.visibility = View.VISIBLE
    }

    override fun initView() {
        initToolBar()

    }

    override fun lazyLoad() {
    }

    override fun initEvent() {


    }
}