package com.zf.mart.ui.activity

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.Paint
import android.os.CountDownTimer
import android.view.View
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.core.widget.NestedScrollView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager.widget.ViewPager
import com.zf.mart.R
import com.zf.mart.api.UriConstant
import com.zf.mart.base.BaseActivity
import com.zf.mart.mvp.bean.*
import com.zf.mart.mvp.contract.GroupDetailContract
import com.zf.mart.mvp.presenter.GroupDetailPresenter
import com.zf.mart.showToast
import com.zf.mart.ui.adapter.EvaImageAdapter
import com.zf.mart.ui.adapter.GroupUserAdapter
import com.zf.mart.ui.adapter.GuideAdapter
import com.zf.mart.utils.GlideUtils
import com.zf.mart.utils.StatusBarUtils
import com.zf.mart.utils.TimeUtils
import com.zf.mart.view.dialog.GroupUserDialog
import com.zf.mart.view.recyclerview.RecyclerViewDivider
import kotlinx.android.synthetic.main.activity_group_detail.*
import kotlinx.android.synthetic.main.layout_detail_head.*
import kotlinx.android.synthetic.main.layout_group_bottom.*
import kotlinx.android.synthetic.main.layout_group_eva.*

/**
 * 拼团 详情
 */
class GroupDetailActivity : BaseActivity(), GroupDetailContract.View {

    //拼团的前5人
    override fun setGroupMember(bean: List<GroupMemberList>) {
        GroupUserDialog.showDialog(supportFragmentManager, bean)
    }

    override fun showError(msg: String, errorCode: Int) {
        showToast(msg)
    }

    //数据
    override fun setGroupDetail(bean: GroupDetailBean) {
        //banner
        initBanner(bean.goodsImg)
        goodsName.text = bean.info.goods_name
        groupName.text = when {
            bean.info.cluster_type == "1" -> "小团"
            bean.info.cluster_type == "2" -> "大团"
            bean.info.cluster_type == "3" -> "阶梯团"
            else -> ""
        }
        currentPrice.text = bean.info.shop_price
        originalPrice.text = bean.info.market_price
        groupNum.text = "已团${bean.info.sales_sum}件"
        goodEva.text = "好评(${bean.info.comment_fr.high_sum})"
        middleEva.text = "中评(${bean.info.comment_fr.center_sum})"
        badEva.text = "差评(${bean.info.comment_fr.low_sum})"
        showEva.text = "晒单(${bean.info.comment_fr.img_sum})"
        goodRate.text = "${bean.info.comment_fr.high_rate}%"
        evaluation.text = "共${bean.info.comment_fr.total_sum}条评论"
        memberNum.text = bean.team_found_num + "人正在拼团，可直接参与"
        collect.isChecked = bean.collect != 0
        singlePrice.text = "¥${bean.info.shop_price}"
        groupPrice.text = "¥${bean.info.group_price}"

        //正在拼团的人
        memberList.clear()
        memberList.addAll(bean.team_found)
        userAdapter.notifyDataSetChanged()

        //活动倒计时
        if ((bean.info.start_time * 1000 > System.currentTimeMillis())) {
            countDownTime.text = "${TimeUtils.auctionTime(bean.info.start_time)}准时开始"
        } else if ((bean.info.start_time * 1000 < System.currentTimeMillis())
                && (bean.info.end_time * 1000 > System.currentTimeMillis())
        ) {
            val time: Long = (bean.info.end_time * 1000) - System.currentTimeMillis()
            countTime = object : CountDownTimer((time), 1000) {
                override fun onFinish() {
                }

                override fun onTick(millisUntilFinished: Long) {
                    countDownTime.text = "距离结束还有 ${TimeUtils.getCountTime2(millisUntilFinished)}"
                }
            }
            countTime?.start()
        } else {
            countDownTime.text = "活动已结束"
        }

        //评价
        initEva(bean.info.commentinfo)

    }

    private var countTime: CountDownTimer? = null

    //评价
    private fun initEva(comment: CommentInfo?) {
        if (comment != null) {
            userName.text = comment.username
            addTime.text = TimeUtils.myOrderTime(comment.add_time)
            starView.setRate((comment.deliver_rank + comment.goods_rank + comment.service_rank) / 3 * 2)
            content.text = comment.content
            val adapter = EvaImageAdapter(this, comment.img)
            val manager = LinearLayoutManager(this)
            manager.orientation = LinearLayoutManager.HORIZONTAL
            recyclerView.layoutManager = manager
            recyclerView.adapter = adapter
        } else {
            commentLayout.visibility = View.GONE
        }
    }

    override fun showLoading() {
        showLoadingDialog()
    }

    override fun dismissLoading() {
        dismissLoadingDialog()
    }

    private var mBean: GroupBean? = null

    companion object {

        fun actionStart(context: Context?, bean: GroupBean) {
            val intent = Intent(context, GroupDetailActivity::class.java)
            intent.putExtra("groupBean", bean)
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
        mBean = intent.getSerializableExtra("groupBean") as GroupBean
    }

    private val groupDetailPresenter by lazy { GroupDetailPresenter() }

    private val memberList = ArrayList<GroupMemberList>()
    //正在拼单的团
    private val userAdapter by lazy { GroupUserAdapter(this, memberList) }

    override fun start() {
        groupDetailPresenter.requestGroupDetail(mBean?.team_id ?: "")
    }

    override fun initView() {

        groupDetailPresenter.attachView(this)

        originalPrice.paint.flags = Paint.STRIKE_THRU_TEXT_FLAG

        //标题栏
        initScrollHead()

        //全部评价
        evaluation.setOnClickListener {
            EvaluationActivity.actionStart(this, mBean?.goods_id ?: "")
        }

        //正在拼单的团
        initGroup()
    }

    override fun initEvent() {

        buySelfLayout.setOnClickListener {
            //单独购买


            //            val popWindow = object : GroupStylePopupWindow(
//                    this,
//                    R.layout.pop_order_style,
//                    LinearLayout.LayoutParams.MATCH_PARENT,
//                    LinearLayout.LayoutParams.WRAP_CONTENT,
//            ) {}
//            popWindow.showAtLocation(parentLayout, Gravity.BOTTOM, 0, 0)
        }

        //更多拼单的人
        moreUserLayout.setOnClickListener {
            groupDetailPresenter.requestGroupMember(mBean?.team_id ?: "")

        }

        backLayout.setOnClickListener {
            finish()
        }
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


    private fun initBanner(imgList: List<GroupDetailImg>) {
        val imageViews = ArrayList<ImageView>()
        repeat(imgList.size) { pos ->
            val img = ImageView(this)
            GlideUtils.loadUrlImage(this, UriConstant.BASE_URL + imgList[pos].image_url, img)
            img.scaleType = ImageView.ScaleType.CENTER_CROP
            imageViews.add(img)
            img.setOnClickListener {
                //                LogUtils.e(">>>>>>点击了第：$pos")
            }
        }
        bannerViewPager.adapter = GuideAdapter(imageViews)
        bannerNum.text = "1/${imgList.size}"

        bannerViewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {
            }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
            }

            override fun onPageSelected(position: Int) {
                bannerNum.text = "${position + 1}/${imgList.size}"
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


    override fun onDestroy() {
        super.onDestroy()
        groupDetailPresenter.detachView()
        userAdapter.finishCountDown()
        countTime?.cancel()
    }


}