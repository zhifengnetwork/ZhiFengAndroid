package com.zf.mart.ui.activity

import android.content.Context
import android.content.Intent
import android.view.Gravity
import android.view.View
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.scwang.smartrefresh.layout.util.DensityUtil
import com.zf.mart.R
import com.zf.mart.base.BaseActivity
import com.zf.mart.mvp.bean.CartGoodsList
import com.zf.mart.mvp.bean.Goods
import com.zf.mart.mvp.bean.ShopList
import com.zf.mart.ui.adapter.EnShopAdapter
import com.zf.mart.utils.StatusBarUtils
import com.zf.mart.view.popwindow.OrderPayPopupWindow
import com.zf.mart.view.recyclerview.RecyclerViewDivider
import kotlinx.android.synthetic.main.activity_confirm_order.*
import kotlinx.android.synthetic.main.layout_toolbar.*

class ConfirmOrderActivity : BaseActivity() {

    companion object {
        fun actionStart(context: Context?) {
            context?.startActivity(Intent(context, ConfirmOrderActivity::class.java))
        }
    }

    override fun initToolBar() {
        StatusBarUtils.darkMode(this, ContextCompat.getColor(this, R.color.colorSecondText), 0.3f)
        back.setOnClickListener { finish() }
        titleName.text = "确定订单"
        rightLayout.visibility = View.INVISIBLE

    }

    override fun layoutId(): Int = R.layout.activity_confirm_order

    override fun initData() {
    }

    //购物车适配器
    private var cartData = ArrayList<ShopList>()
    private val adapter by lazy { EnShopAdapter(this, cartData) }

    private val rvDivider by lazy {
        RecyclerViewDivider(
            this,
            LinearLayoutManager.VERTICAL,
            DensityUtil.dp2px(12f),
            ContextCompat.getColor(this, R.color.colorBackground)
        )
    }

    override fun initView() {
        cartData = getCartData()
        goodsRecyclerView.layoutManager = LinearLayoutManager(this)
        goodsRecyclerView.adapter = adapter
        goodsRecyclerView.addItemDecoration(rvDivider)
    }

    override fun initEvent() {

        addressLayout.setOnClickListener {
            AddressActivity.actionStart(this)
        }

        settle.setOnClickListener {
            val window = object : OrderPayPopupWindow(
                this, R.layout.pop_order_pay,
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT
            ) {}

            window.showAtLocation(parentLayout, Gravity.BOTTOM, 0, 0)
        }
    }

    override fun start() {
    }

    private fun getCartData(): ArrayList<ShopList> {
        val list = ArrayList<ShopList>()
//        list.addAll(
//            arrayListOf(
//                ShopList(
//                    "小米旗舰店", arrayListOf(
//                        CartGoodsList(4, false, Goods("小米8se", ""))
//                    )
//                ),
//                ShopList(
//                    "索尼", arrayListOf(
//                        CartGoodsList(16, false, Goods("索尼8se", "")),
//                        CartGoodsList(17, false, Goods("索尼8se", "")),
//                        CartGoodsList(18, false, Goods("索尼8se", ""))
//                    )
//                )
//            )
//        )
        return list
    }
}