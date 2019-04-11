package com.zf.mart.ui.activity

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.Paint
import android.view.Gravity
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.core.widget.NestedScrollView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager.widget.ViewPager
import com.bumptech.glide.Glide
import com.zf.mart.R
import com.zf.mart.base.BaseActivity
import com.zf.mart.mvp.bean.GroupDetailBean
import com.zf.mart.mvp.contract.GroupDetailContract
import com.zf.mart.mvp.presenter.GroupDetailPresenter
import com.zf.mart.showToast
import com.zf.mart.ui.adapter.GroupEvaAdapter
import com.zf.mart.ui.adapter.GroupUserAdapter
import com.zf.mart.ui.adapter.GuideAdapter
import com.zf.mart.utils.LogUtils
import com.zf.mart.utils.StatusBarUtils
import com.zf.mart.view.dialog.GroupUserDialog
import com.zf.mart.view.popwindow.GroupStylePopupWindow
import com.zf.mart.view.recyclerview.RecyclerViewDivider
import kotlinx.android.synthetic.main.activity_group_detail.*
import kotlinx.android.synthetic.main.layout_detail_head.*
import kotlinx.android.synthetic.main.layout_group_bottom.*

/**
 * 拼团 详情
 */
class GroupDetailActivity : BaseActivity(), GroupDetailContract.View {

    override fun showError(msg: String, errorCode: Int) {
        showToast(msg)
    }

    override fun setGroupDetail(bean: GroupDetailBean) {

    }

    override fun showLoading() {
    }

    override fun dismissLoading() {
    }

    private var mTeamId = ""

    companion object {

        fun actionStart(context: Context?, teamId: String) {
            val intent = Intent(context, GroupDetailActivity::class.java)
            intent.putExtra("teamId", teamId)
            context?.startActivity(intent)
        }
    }

    override fun initToolBar() {
        StatusBarUtils.darkMode(
            this,
            ContextCompat.getColor(this, R.color.colorSecondText),
            0.3f
        )
    }

    override fun layoutId(): Int = R.layout.activity_group_detail

    override fun initData() {
        mTeamId = intent.getStringExtra("teamId")
    }

    private val groupDetailPresenter by lazy { GroupDetailPresenter() }

    //评价
    private val evaAdapter by lazy { GroupEvaAdapter(this) }

    //正在拼单的团
    private val userAdapter by lazy { GroupUserAdapter(this) }

    override fun start() {
        groupDetailPresenter.requestGroupDetail(mTeamId)
    }

    override fun initView() {

        groupDetailPresenter.attachView(this)

        originalPrice.paint.flags = Paint.STRIKE_THRU_TEXT_FLAG


        //标题栏
        initScrollHead()

        //banner
        initBanner()

        //评价
        initEva()

        //全部评价
        evaluation.setOnClickListener {
            EvaluationActivity.actionStart(this)
        }

        //正在拼单的团
        initGroup()
    }

    private fun initGroup() {
        userRecyclerView.layoutManager = LinearLayoutManager(this)
        userRecyclerView.adapter = userAdapter
        userRecyclerView.addItemDecoration(
            RecyclerViewDivider(
                this,
                LinearLayoutManager.VERTICAL,
                1,
                ContextCompat.getColor(this, R.color.colorBackground)
            )
        )
    }

    private fun initEva() {
        evaRecyclerView.layoutManager = LinearLayoutManager(this)
        evaRecyclerView.adapter = evaAdapter
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
                LogUtils.e(">>>>>>点击了第：$pos")
            }
        }
        bannerViewPager.adapter = GuideAdapter(imageViews)
        bannerNum.text = "1/${images.size}"

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

        buySelfLayout.setOnClickListener {
            val popWindow = object : GroupStylePopupWindow(
                this,
                R.layout.pop_order_style,
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            ) {}
            popWindow.showAtLocation(parentLayout, Gravity.BOTTOM, 0, 0)
        }


        //更多拼单的人
        moreUserLayout.setOnClickListener {
            GroupUserDialog.showDialog(supportFragmentManager)
        }

        backLayout.setOnClickListener {
            finish()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        groupDetailPresenter.detachView()
    }


}