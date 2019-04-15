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

/**
 * 竞拍详情页
 */
class AuctionDetailActivity : BaseActivity(), AuctionDetailContract.View {

    override fun showError(msg: String, errorCode: Int) {
        showToast(msg)
    }

    override fun setAuctionDetail(bean: AuctionDetailBean) {

        if (bean.auction.start_time * 1000 > System.currentTimeMillis()) {
            startTime.text = "${TimeUtils.auctionTime(bean.auction.start_time)}准时开拍"
        } else if (bean.auction.start_time * 1000 < System.currentTimeMillis()) {
            //开启定时器
            val countDownTimer = object : CountDownTimer((10 * 1000), 1000) {
                override fun onFinish() {

                }

                override fun onTick(millisUntilFinished: Long) {

                }
            }
            countDownTimer.start()
        }

        goodsName.text = bean.auction.goods_name
        price.text = "¥${bean.auction.start_price}"
        GlideUtils.loadUrlImage(this, UriConstant.BASE_URL + bean.auction.original_img, goodsIcon)

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

    private val adapter by lazy { AuctionPeopleAdapter(this) }

    private val presenter by lazy { AuctionDetailPresenter() }

    override fun initView() {
        presenter.attachView(this)
        val manager = LinearLayoutManager(this)
        manager.orientation = LinearLayoutManager.HORIZONTAL
        recyclerView.layoutManager = manager
        recyclerView.adapter = adapter
    }

    override fun initEvent() {

        confirm.setOnClickListener {
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
        presenter.detachView()
    }

    override fun start() {
        presenter.requestAuctionDetail(mId)
    }
}