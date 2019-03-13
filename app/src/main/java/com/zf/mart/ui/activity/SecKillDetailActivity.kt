package com.zf.mart.ui.activity

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.view.View
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.core.widget.NestedScrollView
import androidx.viewpager.widget.ViewPager
import com.bumptech.glide.Glide
import com.zf.mart.R
import com.zf.mart.base.BaseActivity
import com.zf.mart.ui.adapter.GuideAdapter
import com.zf.mart.utils.LogUtils
import com.zzhoujay.richtext.RichText
import kotlinx.android.synthetic.main.activity_seckill_detail.*
import kotlinx.android.synthetic.main.layout_detail_head.*

/**
 * 秒杀的订单详情
 */
class SecKillDetailActivity : BaseActivity() {

    companion object {
        fun actionStart(context: Context?) {
            context?.startActivity(Intent(context, SecKillDetailActivity::class.java))
        }
    }

    override fun initToolBar() {
        shareLayout.visibility = View.INVISIBLE
    }

    override fun layoutId(): Int = R.layout.activity_seckill_detail

    override fun initData() {
    }


    override fun initView() {

        //图文详情
        RichText.fromHtml(htmlTxt).into(detail)

        //标题栏
        initScrollHead()


        //banner
        initBanner()
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

    private fun initScrollHead() {
        scrollView.setOnScrollChangeListener { v: NestedScrollView?, scrollX: Int, scrollY: Int, oldScrollX: Int, oldScrollY: Int ->
            LogUtils.e(">>>>scroll:$scrollY     $oldScrollY")
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

    override fun initEvent() {

        //立即购买
        operation.setOnClickListener {
            SecKillPushActivity.actionStart(this)
        }

        backLayout.setOnClickListener {
            finish()
        }
    }

    override fun start() {
    }

    val htmlTxt = "<!DOCTYPE html>\n" +
            "<html>\n" +
            "<head>\n" +
            "<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" />\n" +
            "<title>这是图文详情-----------------------------------</title>\n" +
            "<meta name=\"keywords\" content=\"这里填写关键词\" />\n" +
            "<meta name=\"description\" content=\"这里填写说明内容\" />\n" +
            "\n" +
            "<script language=\"JavaScript\" type=\"text/javascript\">\n" +
            "<!--JS代码位置-->\n" +
            "</script>\n" +
            "\n" +
            "<style type=\"text/css\">\n" +
            "<!--CSS样式代码位置-->\n" +
            "</style>\n" +
            "\n" +
            "</head>\n" +
            "\n" +
            "<body>\n" +
            "\n" +
            "这里填写HTML代码\n" +
            "\n" +
            "<p>\n" +
            "这是段落\n" +
            "</p>\n" +
            "\n" +
            "\n" +
            "<p>\n" +
            "这是段落\n" +
            "</p>\n" +
            "\n" +
            "<p>\n" +
            "这是段落\n" +
            "</p>\n" +
            "\n" +
            "<p>\n" +
            "这是段落\n" +
            "</p>\n" +
            "\n" +
            "<p>\n" +
            "这是段落\n" +
            "</p>\n" +
            "\n" +
            "<p>\n" +
            "这是段落\n" +
            "</p>\n" +
            "\n" +
            "<p>\n" +
            "这是段落\n" +
            "</p>\n" +
            "\n" +
            "\n" +
            "<p>\n" +
            "这是段落\n" +
            "</p>\n" +
            "\n" +
            "\n" +
            "<p>\n" +
            "这是段落\n" +
            "</p>\n" +
            "<p>\n" +
            "这是段落\n" +
            "</p>\n" +
            "<p>\n" +
            "这是段落\n" +
            "</p>\n" +
            "<p>\n" +
            "这是段落\n" +
            "</p>\n" +
            "<p>\n" +
            "这是段落\n" +
            "</p>\n" +
            "<p>\n" +
            "这是段落\n" +
            "</p>\n" +
            "\n" +
            "<p>\n" +
            "这是段落\n" +
            "</p>\n" +
            "\n" +
            "<p>\n" +
            "这是段落\n" +
            "</p> \n" +
            "\n" +
            "<p>\n" +
            "\n" +
            "<img src=\"http://www.pptok.com/wp-content/uploads/2012/08/xunguang-4.jpg\" width=\"258\" height=\"39\" />\n" +
            "</p>\n" +
            "\n" +
            "<p>\n" +
            "\n" +
            "<img src=\"http://www.pptok.com/wp-content/uploads/2012/08/xunguang-4.jpg\" width=\"258\" height=\"39\" />\n" +
            "</p>\n" +
            "\n" +
            "<p>\n" +
            "\n" +
            "<img src=\"http://www.pptok.com/wp-content/uploads/2012/08/xunguang-4.jpg\" width=\"258\" height=\"39\" />\n" +
            "</p>\n" +
            "\n" +
            "<p>\n" +
            "\n" +
            "<img src=\"http://pic3.huitu.com/res/20120531/721_20120531125839525174_1.jpg\" width=\"258\" height=\"39\" />\n" +
            "</p>\n" +
            "\n" +
            "<p>\n" +
            "\n" +
            "<img src=\"http://www.pptok.com/wp-content/uploads/2012/08/xunguang-4.jpg\" width=\"258\" height=\"39\" />\n" +
            "</p>\n" +
            "\n" +
            "<p>\n" +
            "\n" +
            "<img src=\"http://www.pptok.com/wp-content/uploads/2012/08/xunguang-4.jpg\" width=\"258\" height=\"39\" />\n" +
            "</p>\n" +
            "<p>\n" +
            "\n" +
            "<img src=\"http://img.zcool.cn/community/01dc60554bf752000001bf72fa836b.jpg@1280w_1l_2o_100sh.png\" width=\"258\" height=\"39\" />\n" +
            "</p>\n" +
            "\n" +
            "<p>\n" +
            "\n" +
            "<img src=\"http://www.pptok.com/wp-content/uploads/2012/08/xunguang-4.jpg\" width=\"258\" height=\"39\" />\n" +
            "</p>\n" +
            "<img src=\"http://www.pptok.com/wp-content/uploads/2012/08/xunguang-4.jpg\" width=\"258\" height=\"39\" />\n" +
            "\n" +
            "<img src=\"http://img2.imgtn.bdimg.com/it/u=3496345838,732839400&fm=26&gp=0.jpg\" width=\"258\" height=\"39\" />\n" +
            "\n" +
            "\n" +
            "<img src=\"http://pic11.nipic.com/20101203/6066308_121059019434_2.jpg\" width=\"258\" height=\"39\" />\n" +
            "\n" +
            "\n" +
            "<img src=\"http://www.pptok.com/wp-content/uploads/2012/08/xunguang-4.jpg\" width=\"258\" height=\"39\" />\n" +
            "\n" +
            "\n" +
            "<img src=\"http://www.pptok.com/wp-content/uploads/2012/08/xunguang-4.jpg\" width=\"258\" height=\"39\" />\n" +
            "\n" +
            "很长\n" +
            "</body>\n" +
            "</html>\n"
}