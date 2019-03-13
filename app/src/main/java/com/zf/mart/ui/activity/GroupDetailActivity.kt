package com.zf.mart.ui.activity

import android.content.Context
import android.content.Intent
import android.widget.ImageView
import androidx.viewpager.widget.ViewPager
import com.bumptech.glide.Glide
import com.zf.mart.R
import com.zf.mart.base.BaseActivity
import com.zf.mart.ui.adapter.GuideAdapter
import com.zf.mart.utils.LogUtils
import kotlinx.android.synthetic.main.activity_group_detail.*

/**
 * 拼团 详情
 */
class GroupDetailActivity : BaseActivity() {

    companion object {
        fun actionStart(context: Context?) {
            context?.startActivity(Intent(context, GroupDetailActivity::class.java))
        }
    }

    override fun initToolBar() {

    }

    override fun layoutId(): Int = R.layout.activity_group_detail

    override fun initData() {
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

    override fun initView() {

        //banner
        initBanner()
    }

    override fun initEvent() {
    }

    override fun start() {
    }
}