package com.zf.mart.ui.fragment.action

import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.core.view.get
import androidx.recyclerview.widget.LinearLayoutManager
import com.zf.mart.R
import com.zf.mart.base.BaseFragment
import com.zf.mart.showToast
import com.zf.mart.ui.adapter.SecKillAdapter
import kotlinx.android.synthetic.main.fragment_seckill_page.*
import me.foji.widget.AutoScrollBase
import me.foji.widget.AutoScrollPagerAdapter

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


        //滑动banner
        initViewPager()
    }

    private val adapter by lazy { SecKillAdapter(context) }


    private val images = intArrayOf(R.mipmap.v1, R.mipmap.v2, R.mipmap.v3, R.mipmap.v4)


    private fun initViewPager() {

        val scrollAdapter = object : AutoScrollPagerAdapter() {
            override fun onLayoutId(): Int = R.layout.image_view

            override fun onBindView(view: View?, pos: Int) {
                (view as ImageView).setImageResource(images[pos])
            }

            override fun getCount(): Int = images.size

        }

        viewPager.setAdapter(scrollAdapter)
        viewPager.setIndictorVisible(false)

        repeat(images.size) {
            val view = View(context)
            view.background = ContextCompat.getDrawable(context!!, R.drawable.viewpager_indictor)
            val lp = LinearLayout.LayoutParams(30, 30)
            lp.setMargins(10, 0, 10, 0)

            indictor_root.addView(view, lp)
            indictor_root[0].isSelected = true

        }

        viewPager.setOnPageChangeListener(object : AutoScrollBase.OnPageChangeListener {
            override fun onPageScrollStateChanged(p0: Int) {
            }

            override fun onPageScrolled(p0: Int, p1: Float, p2: Int) {
            }

            override fun onPageSelected(pos: Int) {
                repeat(images.size) {
                    indictor_root[it].isSelected = pos == it
                }
            }
        })

        viewPager.setOnItemClickListener { i, _ ->
            showToast("is $i")
        }

    }

    override fun lazyLoad() {
    }

    override fun initEvent() {
    }
}