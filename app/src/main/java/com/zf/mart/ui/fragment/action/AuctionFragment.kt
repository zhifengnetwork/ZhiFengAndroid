package com.zf.mart.ui.fragment.action

import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.zf.mart.R
import com.zf.mart.base.BaseFragment
import com.zf.mart.ui.activity.AuctionDetailActivity
import com.zf.mart.ui.adapter.AuctionAdapter
import com.zf.mart.utils.LogUtils
import kotlinx.android.synthetic.main.fragment_auction.*

/**
 * 竞拍
 */
class AuctionFragment : BaseFragment() {

    override fun getLayoutId(): Int = R.layout.fragment_auction

    private val adapter by lazy { AuctionAdapter(context) }

    override fun initView() {


        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = adapter
    }

    override fun lazyLoad() {
    }

    override fun initEvent() {
        adapter.setOnclickListener(object : AuctionAdapter.OnItemClickListener {
            override fun onClick() {
                AuctionDetailActivity.actionStart(context)
            }
        })
    }

}