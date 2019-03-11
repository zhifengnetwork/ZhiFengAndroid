package com.zf.mart.ui.fragment.same

import com.zf.mart.R
import com.zf.mart.base.BaseFragment

/**
 * 订单详情的热销排行
 */
class DetailRankFragment : BaseFragment() {

    companion object {
        fun newInstance(): DetailRankFragment {
            return DetailRankFragment()
        }
    }

    override fun getLayoutId(): Int = R.layout.fragment_detail_rank

    override fun initView() {
    }

    override fun lazyLoad() {
    }

    override fun initEvent() {
    }
}