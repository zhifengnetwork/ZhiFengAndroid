package com.zf.mart.ui.fragment.action

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager.widget.ViewPager
import com.zf.mart.R
import com.zf.mart.base.BaseFragment
import com.zf.mart.base.BaseFragmentAdapter
import com.zf.mart.base.NotLazyBaseFragment
import com.zf.mart.ui.adapter.TopTimeAdapter
import com.zf.mart.utils.LogUtils
import kotlinx.android.synthetic.main.fragment_seckill.*

/**
 * 秒杀
 */
class SecKillFragment : BaseFragment() {

    override fun getLayoutId(): Int = R.layout.fragment_seckill


    private val topTimeAdapter by lazy { TopTimeAdapter(context, data) }

    private val data = ArrayList<String>()

    override fun initView() {
        LogUtils.e(">>>>>>SecKillFragment initView")

        data.addAll(arrayListOf("07:00", "08:00", "09:00", "10:00", "11:00", "12:00", "13:00"))

        //时间滑动条
        val layoutManager = LinearLayoutManager(context)
        layoutManager.orientation = LinearLayoutManager.HORIZONTAL
        topRecyclerView.layoutManager = layoutManager
        topRecyclerView.adapter = topTimeAdapter

        topTimeAdapter.setOnClickListener(object : TopTimeAdapter.OnItemClickListener {
            override fun onClick(pos: Int) {
                viewPager.currentItem = pos
            }
        })

        val fgms = ArrayList<NotLazyBaseFragment>()

        repeat(data.size) {
            fgms.add(SecKillPagerFragment.newInstance(it))
        }

        val adapter = BaseFragmentAdapter(childFragmentManager, fgms, arrayListOf())
        viewPager.adapter = adapter
        viewPager.offscreenPageLimit = 1
        viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
            }

            override fun onPageSelected(position: Int) {
                topRecyclerView.smoothScrollToPosition(position)
                topTimeAdapter.setCheck(position)
            }

            override fun onPageScrollStateChanged(state: Int) {
            }
        })

    }


    override fun lazyLoad() {

        LogUtils.e(">>>>>>SecKillFragment lazyLoad ")

        /**
         * 测试选中第三个
         */
        topTimeAdapter.setCheck(3)
        viewPager.currentItem = 3

    }

    override fun initEvent() {
    }

}