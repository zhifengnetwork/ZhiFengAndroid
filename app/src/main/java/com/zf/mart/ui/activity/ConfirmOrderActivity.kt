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
        StatusBarUtils.darkMode(this,ContextCompat.getColor(this,R.color.colorSecondText),0.3f)
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

    override fun initView() {
        cartData = getCartData()
        goodsRecyclerView.layoutManager = LinearLayoutManager(this)
        goodsRecyclerView.adapter = adapter
        goodsRecyclerView.addItemDecoration(
            RecyclerViewDivider(
                this,
                LinearLayoutManager.VERTICAL,
                DensityUtil.dp2px(12f),
                ContextCompat.getColor(this, R.color.colorBackground)
            )
        )
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
        list.addAll(
            arrayListOf(
                ShopList(
                    "小米旗舰店", arrayListOf(
//                        CartGoodsList(1, "小米Note"),
//                        CartGoodsList(2, "小米5"),
//                        CartGoodsList(3, "小米8"),
                        CartGoodsList(4, "小米8se")
                    )
                ),
//                ShopList(
//                    "华为旗舰店", arrayListOf(
//                        CartGoodsList(5, "华为mate"),
//                        CartGoodsList(6, "华为荣耀")
//                    )
//                ),
//                ShopList(
//                    "格力空调", arrayListOf(
//                        CartGoodsList(7, "1匹"),
//                        CartGoodsList(8, "2匹"),
//                        CartGoodsList(9, "3匹")
//                    )
//                ),
//                ShopList(
//                    "oppo手机", arrayListOf(
//                        CartGoodsList(10, "oppo r1"),
//                        CartGoodsList(11, "oppo r2"),
//                        CartGoodsList(12, "oppo r3")
//                    )
//                ),
//                ShopList(
//                    "戴尔", arrayListOf(
//                        CartGoodsList(13, "笔记本"),
//                        CartGoodsList(14, "台式机"),
//                        CartGoodsList(15, "屏幕")
//                    )
//                ),
                ShopList(
                    "索尼", arrayListOf(
                        CartGoodsList(16, "索尼手机"),
                        CartGoodsList(17, "索尼相机"),
                        CartGoodsList(18, "索尼其他东西")
                    )
                )


            )
        )
        return list
    }
}