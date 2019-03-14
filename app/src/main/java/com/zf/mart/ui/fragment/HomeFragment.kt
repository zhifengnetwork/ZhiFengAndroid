package com.zf.mart.ui.fragment

import android.graphics.Color
import android.os.CountDownTimer
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.core.view.get
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.scwang.smartrefresh.layout.util.DensityUtil
import com.zf.mart.R
import com.zf.mart.base.BaseFragment
import com.zf.mart.showToast
import com.zf.mart.ui.activity.ActionActivity
import com.zf.mart.ui.activity.SearchActivity
import com.zf.mart.ui.adapter.HomeFragmentRecommendAdapter
import com.zf.mart.ui.adapter.HomeSecKillAdapter
import com.zf.mart.utils.TimeUtils
import com.zf.mart.view.RecDecoration
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_home_top.*
import kotlinx.android.synthetic.main.home_quality.*
import kotlinx.android.synthetic.main.layout_news.*
import kotlinx.android.synthetic.main.layout_search.*
import me.foji.widget.AutoScrollBase
import me.foji.widget.AutoScrollPagerAdapter

class HomeFragment : BaseFragment() {

    companion object {
        fun getInstance(): HomeFragment {
            return HomeFragment()
        }
    }

    override fun getLayoutId(): Int = R.layout.fragment_home

    private val recommendData = ArrayList<String>()

    //推荐商品列表
    private val adapter by lazy { HomeFragmentRecommendAdapter(context, recommendData) }

    private var countDownTime: CountDownTimer? = null

    private fun initCountDown() {

        countDownTime = object : CountDownTimer(3700 * 1000, 1000) {
            override fun onFinish() {
            }

            override fun onTick(millisUntilFinished: Long) {
                countDown.text = TimeUtils.getCountTime(millisUntilFinished)
            }
        }
        countDownTime?.start()
    }

    override fun onDestroy() {
        countDownTime?.cancel()
        home_head_line_tvs?.stopAutoScroll()
        super.onDestroy()

    }

    override fun onResume() {
        super.onResume()
        home_head_line_tvs?.startAutoScroll()
    }

    override fun onPause() {
        super.onPause()
        home_head_line_tvs?.stopAutoScroll()
    }

    override fun initView() {

        //倒计时
        initCountDown()

        //推荐
        recommendData.addAll(arrayListOf("1","2","3"))
        val gridManager = GridLayoutManager(context, 2)
        home_recommend_recyclerview.layoutManager = gridManager
        home_recommend_recyclerview.addItemDecoration(RecDecoration(DensityUtil.dp2px(12f)))
        home_recommend_recyclerview.adapter = adapter

        //头条数据
        val headList = ArrayList<String>()
        headList.add("好看的连衣裙")
        headList.add("今日爆款男装")
        headList.add("春季瘦身衣")
        home_head_line_tvs.setTextList(headList)
        home_head_line_tvs.setText(14f, 5, Color.RED)
        home_head_line_tvs.setTextStillTime(4000) //停留时长
        home_head_line_tvs.setAnimTime(400) //进出间隔时间
        home_head_line_tvs.setOnItemClickListener { }
        home_head_line_tvs.startAutoScroll()

        //顶部滑动页 滑动指示器
        initViewPager()

        //品质生活pager
        initLife()

        //秒杀列表
        initSecKill()
    }

    private val secKillAdapter by lazy { HomeSecKillAdapter(context) }

    private fun initSecKill() {
        val layoutManager = LinearLayoutManager(context)
        layoutManager.orientation = LinearLayoutManager.HORIZONTAL
        home_ms_recycler.layoutManager = layoutManager
        home_ms_recycler.adapter = secKillAdapter
    }

    private fun initLife() {
        home_Ts_viewPager.setAdapter(object : AutoScrollPagerAdapter() {
            override fun onLayoutId(): Int = R.layout.image_view

            override fun onBindView(view: View?, pos: Int) {
                (view as ImageView).setImageResource(images[pos])
            }

            override fun getCount(): Int = images.size

        })
        home_Ts_viewPager.setIndictorVisible(false)
    }

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

        //搜索
        searchLayout.setOnClickListener {
            SearchActivity.actionStart(context)
        }

        //秒杀
        action.setOnClickListener {
            ActionActivity.actionStart(context, ActionActivity.SECKILL)
        }
    }
}