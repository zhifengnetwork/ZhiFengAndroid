package com.zf.mart.ui.fragment.action

import androidx.recyclerview.widget.LinearLayoutManager
import com.zf.mart.R
import com.zf.mart.base.BaseFragment
import com.zf.mart.ui.adapter.SecKillAdapter
import com.zf.mart.utils.GlideImageLoader
import kotlinx.android.synthetic.main.fragment_seckill_page.*

/**
 * 秒杀页面多个布局
 */
class SecKillPagerFragment : BaseFragment() {

    companion object {
        fun newInstance(): SecKillPagerFragment {
            return SecKillPagerFragment()
        }
    }

    override fun getLayoutId(): Int = R.layout.fragment_seckill_page

    override fun initView() {

        //商品列表
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = adapter

        //banner
        initBanner()
    }

    private fun initBanner() {
        banner.setImageLoader(GlideImageLoader())
        banner.setImages(images)
    }

    private val adapter by lazy { SecKillAdapter(context) }

    private val images = arrayListOf(R.mipmap.v1, R.mipmap.v2, R.mipmap.v3, R.mipmap.v4)

    override fun lazyLoad() {
    }

    override fun initEvent() {
        banner.start()
    }
}