package com.zf.mart.ui.fragment

import android.graphics.Color
import android.os.CountDownTimer
import androidx.core.content.ContextCompat
import androidx.core.widget.NestedScrollView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.scwang.smartrefresh.layout.util.DensityUtil
import com.zf.mart.R
import com.zf.mart.base.BaseFragment
import com.zf.mart.ui.activity.ActionActivity
import com.zf.mart.ui.activity.ChoiceActivity
import com.zf.mart.ui.activity.MessageActivity
import com.zf.mart.ui.activity.SearchActivity
import com.zf.mart.ui.adapter.HomeFragmentRecommendAdapter
import com.zf.mart.ui.adapter.HomeSecKillAdapter
import com.zf.mart.utils.GlideImageLoader
import com.zf.mart.utils.TimeUtils
import com.zf.mart.view.RecDecoration
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_home_top.*
import kotlinx.android.synthetic.main.layout_news.*
import kotlinx.android.synthetic.main.layout_prefecture.*
import kotlinx.android.synthetic.main.layout_search.*
import kotlinx.android.synthetic.main.layout_seckill.*

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
        recommendData.addAll(arrayListOf("1", "2", "3"))
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


        /** 滑动指示器 banner */
        initBanner()

        //品质生活pager
//        initLife()

        //秒杀列表
        initSecKill()


    }

    private fun initBanner() {
        /** 在最后需要start()  start()在点击事件之后 */
        topBanner.setImageLoader(GlideImageLoader())
        topBanner.setImages(images)
    }

    private val secKillAdapter by lazy { HomeSecKillAdapter(context) }

    private fun initSecKill() {
        val layoutManager = LinearLayoutManager(context)
        layoutManager.orientation = LinearLayoutManager.HORIZONTAL
        secKillRecyclerView.layoutManager = layoutManager
        secKillRecyclerView.adapter = secKillAdapter
    }

//    private fun initLife() {
//        qualityBanner.setBannerStyle(BannerConfig.NOT_INDICATOR)
//        qualityBanner.setImageLoader(GlideImageLoader())
//        qualityBanner.setImages(images)
//
//    }

    private val images = arrayListOf(R.mipmap.v1, R.mipmap.v2, R.mipmap.v3, R.mipmap.v4)

    private fun changeAlpha(color: Int, fraction: Float): Int {
        val red = Color.red(color)
        val green = Color.green(color)
        val blue = Color.blue(color)
        val alpha = (Color.alpha(color) * fraction).toInt()
        return Color.argb(alpha, red, green, blue)
    }

    override fun initEvent() {



        home_nestedscroll.setOnScrollChangeListener { _: NestedScrollView?, _: Int, scrollY: Int, _: Int, _: Int ->
            var alpha = scrollY / 100 * 0.7f
            if (alpha >= 1.0) {
                alpha = 1.0f
            }
            home_title.setBackgroundColor(
                changeAlpha(
                    ContextCompat.getColor(context!!, R.color.head_bg)
                    , alpha
                )
            )
        }

        //站内消息
        homeMessage.setOnClickListener {
            MessageActivity.actionStart(context)
        }

        //搜索
        searchLayout.setOnClickListener {
            SearchActivity.actionStart(context, "")
        }

        //秒杀
        action.setOnClickListener {
            ActionActivity.actionStart(context, ActionActivity.SECKILL)
        }

        //顶部banner开始轮播
        topBanner.start()
        //品质生活banner
//        qualityBanner.start()


        //精选
        choice.setOnClickListener {
            ChoiceActivity.actionStart(context)
        }
    }


    override fun lazyLoad() {
    }


}