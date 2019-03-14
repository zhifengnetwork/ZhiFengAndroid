package com.zf.mart.ui.activity

import android.content.Context
import android.content.Intent
import android.view.Gravity
import android.view.View
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.zf.mart.R
import com.zf.mart.base.BaseActivity
import com.zf.mart.ui.adapter.AuctionPeopleAdapter
import com.zf.mart.utils.StatusBarUtilNotUse
import com.zf.mart.view.dialog.AuctionSuccessDialog
import com.zf.mart.view.popwindow.ServicePopupWindow
import kotlinx.android.synthetic.main.activity_auction_detail.*
import kotlinx.android.synthetic.main.layout_toolbar.*
import kotlinx.android.synthetic.main.pop_push_order.view.*

/**
 * 竞拍详情页
 */
class AuctionDetailActivity : BaseActivity() {

    companion object {
        fun actionStart(context: Context?) {
            context?.startActivity(Intent(context, AuctionDetailActivity::class.java))
        }
    }

    override fun initToolBar() {
        StatusBarUtilNotUse.darkMode(this, ContextCompat.getColor(this, R.color.colorSecondText), 0.3f)
        back.setOnClickListener { finish() }
        titleName.text = "竞拍"
        rightLayout.visibility = View.INVISIBLE

    }

    override fun layoutId(): Int = R.layout.activity_auction_detail

    override fun initData() {
    }

    private val adapter by lazy { AuctionPeopleAdapter(this) }


    override fun initView() {
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

    override fun start() {
    }
}