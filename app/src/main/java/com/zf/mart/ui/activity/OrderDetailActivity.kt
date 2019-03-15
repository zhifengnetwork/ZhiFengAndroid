package com.zf.mart.ui.activity

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.view.Gravity
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.core.view.get
import androidx.core.widget.NestedScrollView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager.widget.ViewPager
import com.bumptech.glide.Glide
import com.flyco.tablayout.listener.CustomTabEntity
import com.google.android.material.appbar.AppBarLayout
import com.zf.mart.R
import com.zf.mart.base.BaseActivity
import com.zf.mart.base.BaseFragmentAdapter
import com.zf.mart.mvp.bean.TabEntity
import com.zf.mart.ui.adapter.DetailAskAdapter
import com.zf.mart.ui.adapter.DetailBrandAdapter
import com.zf.mart.ui.adapter.DetailEvaAdapter
import com.zf.mart.ui.adapter.GuideAdapter
import com.zf.mart.ui.fragment.graphic.GraphicFragment
import com.zf.mart.ui.fragment.same.DetailSameFragment
import com.zf.mart.utils.LogUtils
import com.zf.mart.utils.StatusBarUtilNotUse
import com.zf.mart.view.dialog.ShareSuccessDialog
import com.zf.mart.view.popwindow.ServicePopupWindow
import kotlinx.android.synthetic.main.activity_order_detail2.*
import kotlinx.android.synthetic.main.layout_detail_brand.*
import kotlinx.android.synthetic.main.layout_detail_eva.*
import kotlinx.android.synthetic.main.layout_detail_head.*
import kotlinx.android.synthetic.main.layout_detail_same.*
import kotlinx.android.synthetic.main.pop_detail_share.*
import kotlinx.android.synthetic.main.pop_detail_share.view.*

/**
 * 订单详情
 */
class OrderDetailActivity : BaseActivity()
//    , AppBarLayout.OnOffsetChangedListener
{

    companion object {
        fun actionStart(context: Context?) {
            context?.startActivity(Intent(context, OrderDetailActivity::class.java))
        }
    }

    override fun initToolBar() {

        StatusBarUtilNotUse.darkMode(
            this,
            ContextCompat.getColor(this, R.color.colorSecondText),
            0.3f
        )


        //分享
        shareLayout.setOnClickListener {
            val popUpWindow = object : ServicePopupWindow(
                this, R.layout.pop_detail_share,
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            ) {
                override fun initView() {
                    contentView.apply {

                        weChat.setOnClickListener {
                            onDismiss()
                            ShareSuccessDialog.showDialog(supportFragmentManager)
                        }

                        cancel.setOnClickListener {
                            onDismiss()
                        }
                    }
                }
            }
            popUpWindow.showAtLocation(shareLayout, Gravity.BOTTOM, 0, 0)
        }


        backLayout.setOnClickListener {
            finish()
        }
    }

    override fun layoutId(): Int = R.layout.activity_order_detail2


    override fun initData() {
    }

    override fun initView() {

        //全部评价
        allEvaluation.setOnClickListener {
            EvaluationActivity.actionStart(this)
        }

        //banner
        initBanner()

        //商品评价
        initEvaluation()

        //问大家
        initAsk()

        //商家品牌推荐
        initBrand()

        //相似推荐
        initSame()

        //图文详情
        initGraphic()

        //滑动监听，如果滑动到一定高度，就将标题栏设置为白色
//        scrollView.setOnScrollChangeListener { v: NestedScrollView?, scrollX: Int, scrollY: Int, oldScrollX: Int, oldScrollY: Int ->
//            LogUtils.e(">>>>>>>>scroll::::" + scrollY + "   " + oldScrollY)
//        }
//        appBarLayout.addOnOffsetChangedListener(this)

        //滑动改变标题栏的透明度
        initScrollHead()

    }

    private fun initScrollHead() {
        scrollView.setOnScrollChangeListener { v: NestedScrollView?, scrollX: Int, scrollY: Int, oldScrollX: Int, oldScrollY: Int ->
            //
            var alpha = scrollY / 100 * 0.7f
            if (alpha >= 1.0) {
                alpha = 1.0f
            }
            orderDetailHead.setBackgroundColor(
                changeAlpha(
                    ContextCompat.getColor(this, R.color.whit)
                    , alpha
                )
            )
        }
    }

    private fun changeAlpha(color: Int, fraction: Float): Int {
        val red = Color.red(color)
        val green = Color.green(color)
        val blue = Color.blue(color)
        val alpha = (Color.alpha(color) * fraction).toInt()
        return Color.argb(alpha, red, green, blue)
    }

//    override fun onOffsetChanged(appBarLayout: AppBarLayout, verticalOffset: Int) {
//        LogUtils.e(">>>>>:::$verticalOffset" + "      " + "    " + ((Math.abs(verticalOffset * 1.0f) / appBarLayout.totalScrollRange) * 8f))
//        var alpha = ((Math.abs(verticalOffset * 1.0f) / appBarLayout.totalScrollRange) * 9.2f)
//        if (alpha >= 1.0) {
//            alpha = 1.0f
//        }
//        orderDetailHead.setBackgroundColor(
//            changeAlpha(
//                ContextCompat.getColor(this, R.color.whit)
//                , alpha
//            )
//        )
//    }

    private fun initGraphic() {
        val titles = arrayOf("图文详情", "答疑")
        val fgms = arrayListOf(GraphicFragment.newInstance() as Fragment, GraphicFragment.newInstance() as Fragment)
        segmentTabLayout.setTabData(titles, this, R.id.graphicFragment, fgms)

    }


    //商品评价adapter
    private val evaAdapter by lazy { DetailEvaAdapter(this) }

    private fun initEvaluation() {
        val manager = LinearLayoutManager(this)
        manager.orientation = LinearLayoutManager.HORIZONTAL
        evaRecyclerView.layoutManager = manager
        evaRecyclerView.adapter = evaAdapter
    }

    private val askAdapter by lazy { DetailAskAdapter(this) }

    private fun initAsk() {
        askRecyclerView.layoutManager = LinearLayoutManager(this)
        askRecyclerView.adapter = askAdapter
    }

    private val brandAdapter by lazy { DetailBrandAdapter(this) }

    private fun initBrand() {
        val manager = LinearLayoutManager(this)
        manager.orientation = LinearLayoutManager.HORIZONTAL
        brandRecyclerView.layoutManager = manager
        brandRecyclerView.adapter = brandAdapter
    }

    /**
     * 相似推荐
     * pageRecyclerView
     */
    private fun initSame() {

        val fgms = arrayListOf(
            DetailSameFragment.newInstance() as Fragment
            , DetailSameFragment.newInstance() as Fragment
        )

        val entitys = ArrayList<CustomTabEntity>()
        entitys.add(TabEntity("相似推荐", 0, 0))
        entitys.add(TabEntity("同类热销排行", 0, 0))
        sameTabLayout.setTabData(entitys, this, R.id.frameLayout, fgms)


    }

    private fun initBanner() {

        val images = listOf(R.mipmap.v1, R.mipmap.v2, R.mipmap.v3, R.mipmap.v4)
        val imageViews = ArrayList<ImageView>()
        repeat(images.size) { pos ->
            val img = ImageView(this)
            Glide.with(this).load(images[pos]).into(img)
            img.scaleType = ImageView.ScaleType.CENTER_CROP
            imageViews.add(img)
            img.setOnClickListener {
                /**  这里设置图片的点击事件 */
                LogUtils.e(">>>>>>点击了第：$pos")
            }
        }
        bannerViewPager.adapter = GuideAdapter(imageViews)
        bannerNum.text = "1/${images.size}"

        /** 滑动改变指示器 */
        bannerViewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {
            }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
            }

            override fun onPageSelected(position: Int) {
                bannerNum.text = "${position + 1}/${images.size}"
            }
        })
    }

    override fun initEvent() {


    }

    override fun start() {
    }
}