package com.zf.mart.ui.activity

import android.content.Context
import android.content.Intent
import android.os.CountDownTimer
import android.view.Gravity
import android.view.View
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.zf.mart.R
import com.zf.mart.api.UriConstant
import com.zf.mart.base.BaseActivity
import com.zf.mart.mvp.bean.AuctionDetailBean
import com.zf.mart.mvp.bean.AuctionPriceBean
import com.zf.mart.mvp.bean.PriceList
import com.zf.mart.mvp.contract.AuctionDetailContract
import com.zf.mart.mvp.presenter.AuctionDetailPresenter
import com.zf.mart.showToast
import com.zf.mart.ui.adapter.AuctionPeopleAdapter
import com.zf.mart.utils.GlideUtils
import com.zf.mart.utils.StatusBarUtils
import com.zf.mart.utils.TimeUtils
import com.zf.mart.view.dialog.AuctionSuccessDialog
import com.zf.mart.view.popwindow.ServicePopupWindow
import kotlinx.android.synthetic.main.activity_auction_detail.*
import kotlinx.android.synthetic.main.layout_toolbar.*
import kotlinx.android.synthetic.main.pop_push_order.view.*
import java.math.BigDecimal
import java.text.DecimalFormat

/**
 * 竞拍详情页
 */
class AuctionDetailActivity : BaseActivity(), AuctionDetailContract.View {

    override fun showError(msg: String, errorCode: Int) {
        showToast(msg)
    }

    private var countDownTimer: CountDownTimer? = null

    //出价成功
    override fun setBid() {

    }

    //出价列表
    override fun setAuctionPrice(bean: AuctionPriceBean) {
        //出价最高
        if (bean.max_price.isNotEmpty()) {
            highPriceLayout.visibility = View.VISIBLE
            GlideUtils.loadUrlImage(this, bean.max_price[0].head_pic, avatar)
            name.text = bean.max_price[0].user_name
            highPrice.text = "¥" + bean.max_price[0].offer_price
        } else {
            highPriceLayout.visibility = View.INVISIBLE
        }
        //出价人列表
        data.clear()
        data.addAll(bean.max_price)
        adapter.notifyDataSetChanged()
    }

    private var mBean: AuctionDetailBean? = null

    //竞拍详情
    override fun setAuctionDetail(bean: AuctionDetailBean) {
        mBean = bean
        //时间
        if ((bean.auction.start_time * 1000 > System.currentTimeMillis())) {
            startTime.text = "${TimeUtils.auctionTime(bean.auction.start_time)}准时开拍"
        } else if ((bean.auction.start_time * 1000 < System.currentTimeMillis())
                && (bean.auction.end_time * 1000 > System.currentTimeMillis())
        ) {
            countDownTimer?.cancel()
            val time: Long = (bean.auction.end_time * 1000) - System.currentTimeMillis()
            countDownTimer = object : CountDownTimer((time), 1000) {
                override fun onFinish() {
                }

                override fun onTick(millisUntilFinished: Long) {
                    startTime.text = "距离结束还有${TimeUtils.getCountTime2(millisUntilFinished)}"
                }
            }.start()
        } else {
            startTime.text = "已经结束"
            operationLayout.visibility = View.GONE
        }

        //other
        goodsName.text = bean.auction.goods_name
        price.text = "¥${bean.auction.start_price}"
        GlideUtils.loadUrlImage(this, UriConstant.BASE_URL + bean.auction.original_img, goodsIcon)
        bid.text = bean.auction.start_price

        confirm.isEnabled = true
        increase.isEnabled = true
        reduce.isEnabled = true
    }

    override fun showLoading() {
        showLoadingDialog()
    }

    override fun dismissLoading() {
        dismissLoadingDialog()
    }

    private var mId = ""

    companion object {
        fun actionStart(context: Context?, actionId: String) {
            val intent = Intent(context, AuctionDetailActivity::class.java)
            intent.putExtra("mId", actionId)
            context?.startActivity(intent)
        }
    }

    override fun initToolBar() {
        StatusBarUtils.darkMode(this, ContextCompat.getColor(this, R.color.colorSecondText), 0.3f)
        back.setOnClickListener { finish() }
        titleName.text = "竞拍"
        rightLayout.visibility = View.INVISIBLE
    }

    override fun layoutId(): Int = R.layout.activity_auction_detail

    override fun initData() {
        mId = intent.getStringExtra("mId")
    }

    private val data = ArrayList<PriceList>()

    private val adapter by lazy { AuctionPeopleAdapter(this, data) }

    private val presenter by lazy { AuctionDetailPresenter() }

    override fun initView() {
        presenter.attachView(this)
        val manager = LinearLayoutManager(this)
        manager.orientation = LinearLayoutManager.HORIZONTAL
        recyclerView.layoutManager = manager
        recyclerView.adapter = adapter
    }

    override fun initEvent() {

        //减价
        reduce.setOnClickListener { _ ->
            mBean?.let {
                if (bid.text.toString().toDouble() <= it.auction.start_price.toDouble()) {
                    return@setOnClickListener
                }
                bid.text = DecimalFormat("#.00").format(BigDecimal(bid.text.toString()).subtract(BigDecimal(it.auction.increase_price)))
            }
        }

        //加价
        increase.setOnClickListener {
            mBean?.let {
                bid.text = DecimalFormat("#.00").format(BigDecimal(bid.text.toString()).add(BigDecimal(it.auction.increase_price)))
            }
        }


        /** 出价 */
        confirm.setOnClickListener {

            presenter.requestBid(mId, bid.text.toString())

            AuctionSuccessDialog.showDialog(supportFragmentManager)
                    .setOnItemClickListener(object : AuctionSuccessDialog.OnItemClickListener {
                        override fun onItemClick() {
                            val window = object : ServicePopupWindow(
                                    this@AuctionDetailActivity, R.layout.pop_push_order,
                                    LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT
                            ) {
                                override fun initView() {
                                    contentView.apply {
                                        submit.setOnClickListener {
                                            onDismiss()
                                        }
                                    }
                                }
                            }
                            window.showAtLocation(parentLayout, Gravity.BOTTOM, 0, 0)
                        }
                    })
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        countDownTimer?.cancel()
        presenter.detachView()
    }

    override fun start() {
        presenter.requestAuctionDetail(mId)
        presenter.requestAuctionPrice(mId)
    }
}