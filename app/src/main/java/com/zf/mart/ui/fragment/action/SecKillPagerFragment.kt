package com.zf.mart.ui.fragment.action

import androidx.recyclerview.widget.LinearLayoutManager
import com.zf.mart.R
import com.zf.mart.base.NotLazyBaseFragment
import com.zf.mart.ui.adapter.SecKillAdapter
import com.zf.mart.utils.GlideImageLoader
import com.zf.mart.utils.LogUtils
import kotlinx.android.synthetic.main.fragment_seckill_page.*

/**
 * 秒杀页面多个布局
 */
class SecKillPagerFragment : NotLazyBaseFragment() {

    var mPosition = 0

    companion object {
        fun newInstance(position: Int): SecKillPagerFragment {
            val fragment = SecKillPagerFragment()
            fragment.mPosition = position
            return fragment
        }
    }

    override fun getLayoutId(): Int = R.layout.fragment_seckill_page

    override fun initView() {
        LogUtils.e(">>>>SecKillPagerFragment initView: $mPosition")

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
        LogUtils.e(">>>>SecKillPagerFragment lazyLoad: $mPosition")
    }

    override fun initEvent() {
        banner.start()
    }
}