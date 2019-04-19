package com.zf.mart.ui.activity

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.CountDownTimer
import android.view.View
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.core.widget.NestedScrollView
import androidx.viewpager.widget.ViewPager
import com.bumptech.glide.Glide
import com.zf.mart.R
import com.zf.mart.api.UriConstant
import com.zf.mart.base.BaseActivity
import com.zf.mart.mvp.bean.SecKillDetailBean
import com.zf.mart.mvp.contract.SecKillDetailContract
import com.zf.mart.mvp.presenter.SecKillDetailPresenter
import com.zf.mart.showToast
import com.zf.mart.ui.adapter.GuideAdapter
import com.zf.mart.utils.StatusBarUtils
import com.zf.mart.utils.TimeUtils
import com.zf.mart.utils.UnicodeUtil
import com.zzhoujay.richtext.RichText
import kotlinx.android.synthetic.main.activity_seckill_detail.*
import kotlinx.android.synthetic.main.layout_detail_head.*

/**
 * 秒杀的订单详情
 */
class SecKillDetailActivity : BaseActivity(), SecKillDetailContract.View {
    override fun showError(msg: String, errorCode: Int) {
        showToast(msg)
    }

    private var timer: CountDownTimer? = null

    override fun setSecKillDetail(bean: SecKillDetailBean) {

        goodsName.text = bean.info.title
        name.text = bean.info.goods_name

        markPrice.text = "¥" + bean.info.shop_price
        secPrice.text = "¥" + bean.info.price
        //库存
        inventory.text = bean.info.store_count
        //销量
        sellNum.text = bean.info.sales_sum
        //图文详情
        RichText.fromHtml(UnicodeUtil.translation(bean.info.goods_content)).into(detail)
        //banner
        initBanner(bean.info.goods_images)
        //倒计时
        initDownTime(bean)

    }

    override fun showLoading() {
        showLoadingDialog()
    }

    override fun dismissLoading() {
        dismissLoadingDialog()
    }

    private var mId = ""

    companion object {
        fun actionStart(context: Context?, id: String) {
            val intent = Intent(context, SecKillDetailActivity::class.java)
            intent.putExtra("id", id)
            context?.startActivity(intent)
        }
    }

    override fun initToolBar() {
        StatusBarUtils.darkMode(
            this,
            ContextCompat.getColor(this, R.color.colorSecondText),
            0.3f
        )

        shareLayout.visibility = View.INVISIBLE
    }

    override fun layoutId(): Int = R.layout.activity_seckill_detail

    override fun initData() {
        mId = intent.getStringExtra("id")
    }

    override fun initView() {
        RichText.initCacheDir(this)
        RichText.debugMode = true
        presenter.attachView(this)


        //标题栏
        initScrollHead()

    }

    override fun initEvent() {

        operation.setOnClickListener {
            SecKillPushActivity.actionStart(this)
        }

        backLayout.setOnClickListener {
            finish()
        }
    }

    private val presenter by lazy { SecKillDetailPresenter() }

    override fun start() {
        presenter.requestSecKillDetail(mId)
    }

    private fun initBanner(list: List<String>) {
        val imageViews = ArrayList<ImageView>()
        repeat(list.size) { pos ->
            val img = ImageView(this)
            Glide.with(this).load(UriConstant.BASE_URL + list[pos]).into(img)
            img.scaleType = ImageView.ScaleType.CENTER_CROP
            imageViews.add(img)
            img.setOnClickListener {
                showToast("图片:$pos")
            }
        }
        bannerViewPager.adapter = GuideAdapter(imageViews)
        bannerNum.text = "1/${list.size}"

        bannerViewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {
            }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
            }

            override fun onPageSelected(position: Int) {
                bannerNum.text = "${position + 1}/${list.size}"
            }
        })
    }

    private fun initDownTime(bean: SecKillDetailBean) {
        if ((bean.info.start_time * 1000 > System.currentTimeMillis())) {
            timeTxt.text = "距离秒杀开始"
            timer?.cancel()
            val time: Long = (bean.info.start_time * 1000) - System.currentTimeMillis()
            timer = object : CountDownTimer((time), 1000) {
                override fun onFinish() {
                }

                override fun onTick(millisUntilFinished: Long) {
                    downTime.text = TimeUtils.getCountTime2(millisUntilFinished)
                }
            }.start()
        } else if ((bean.info.start_time * 1000 < System.currentTimeMillis())
            && (bean.info.end_time * 1000 > System.currentTimeMillis())
        ) {
            timeTxt.text = "距离秒杀结束"
            timer?.cancel()
            val time: Long = (bean.info.end_time * 1000) - System.currentTimeMillis()
            timer = object : CountDownTimer((time), 1000) {
                override fun onFinish() {
                }

                override fun onTick(millisUntilFinished: Long) {
                    downTime.text = TimeUtils.getCountTime2(millisUntilFinished)
                }
            }.start()
        } else {
            timeTxt.text = "秒杀已结束"
            downTime.visibility = View.INVISIBLE
        }
    }

    private fun initScrollHead() {
        scrollView.setOnScrollChangeListener { _: NestedScrollView?, _: Int, scrollY: Int, _: Int, _: Int ->
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


    override fun onDestroy() {
        super.onDestroy()
        presenter.detachView()
        RichText.clear(this)
        timer?.cancel()
    }


    private fun changeAlpha(color: Int, fraction: Float): Int {
        val red = Color.red(color)
        val green = Color.green(color)
        val blue = Color.blue(color)
        val alpha = (Color.alpha(color) * fraction).toInt()
        return Color.argb(alpha, red, green, blue)
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