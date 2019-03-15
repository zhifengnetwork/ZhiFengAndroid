package com.zf.mart.ui.activity

import android.content.Context
import android.content.Intent
import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.zf.mart.R
import com.zf.mart.base.BaseActivity
import com.zf.mart.ui.adapter.GuideAdapter
import com.zf.mart.utils.LogUtils
import kotlinx.android.synthetic.main.activity_bonus.*
import kotlinx.android.synthetic.main.layout_toolbar.*

class BonusActivity:BaseActivity(){
    companion object {
        fun actionStart(context: Context?){
            context?.startActivity(Intent(context,BonusActivity::class.java))
        }
    }

    override fun initToolBar() {
        titleName.text="微分销会员"
        back.setOnClickListener {

        }
        rightLayout.visibility= View.INVISIBLE
    }

    override fun layoutId(): Int = R.layout.activity_bonus


    override fun initData() {


    }

    override fun initView() {

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
          bonusViewPager.adapter = GuideAdapter(imageViews)



    }

    override fun initEvent() {

    }

    override fun start() {


    }

}