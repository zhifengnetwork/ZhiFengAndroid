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
import com.zf.mart.mvp.bean.AddressBean
import com.zf.mart.mvp.bean.Goods
import com.zf.mart.mvp.bean.PostOrderBean
import com.zf.mart.mvp.contract.PostOrderContract
import com.zf.mart.mvp.presenter.PostOrderPresenter
import com.zf.mart.showToast
import com.zf.mart.ui.adapter.EnGoodsAdapter
import com.zf.mart.utils.StatusBarUtils
import com.zf.mart.view.popwindow.OrderPayPopupWindow
import com.zf.mart.view.recyclerview.RecyclerViewDivider
import kotlinx.android.synthetic.main.activity_confirm_order.*
import kotlinx.android.synthetic.main.layout_en_order_address.*
import kotlinx.android.synthetic.main.layout_order_price.*
import kotlinx.android.synthetic.main.layout_toolbar.*

class ConfirmOrderActivity : BaseActivity(), PostOrderContract.View {

    override fun showError(msg: String, errorCode: Int) {
        showToast(msg)
    }

    override fun setPostOrder(bean: PostOrderBean) {
        goodsData.clear()
        goodsData.addAll(bean.goodsinfo)
        adapter.notifyDataSetChanged()

        bean.price.let {
            orderAmount.text = "¥ ${it.order_prom_amount}"
            shopPrice.text = "¥ ${it.goods_price}"
            shippingPrice.text = "¥ ${it.shipping_price}"
            signPrice.text = "¥ ${it.sign_price}"
            marginPrice.text = "¥ ${it.deposit}"
            userMoney.text = "¥ ${it.user_money}"
            totalPrice.text = "¥ ${it.total_amount}"
        }

        bean.address.let {
            userName.text = it.consignee
            userPhone.text = it.mobile
            userAddress.text = "${it.province_name}${it.city_name}${it.district_name}${it.address}"
        }


    }

    override fun showLoading() {
    }

    override fun dismissLoading() {
    }

    companion object {

        const val mRequestCode = 10

        const val FROM_ORDER = "fromOrder"

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

    private val presenter by lazy { PostOrderPresenter() }

    override fun initData() {
    }

    //购物车适配器
    private var goodsData = ArrayList<Goods>()
    private val adapter by lazy { EnGoodsAdapter(this, goodsData) }

    private val rvDivider by lazy {
        RecyclerViewDivider(
                this,
                LinearLayoutManager.VERTICAL,
                DensityUtil.dp2px(1f),
                ContextCompat.getColor(this, R.color.colorBackground)
        )
    }

    override fun initView() {
        presenter.attachView(this)
        goodsRecyclerView.layoutManager = LinearLayoutManager(this)
        goodsRecyclerView.adapter = adapter
        goodsRecyclerView.addItemDecoration(rvDivider)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (mRequestCode == requestCode && AddressActivity.mResultCode == resultCode) {
            val addressBean = data?.getSerializableExtra(AddressActivity.ADDRESS_FLAG) as AddressBean
            userName.text = addressBean.consignee
            userPhone.text = addressBean.mobile
            userAddress.text =
                    "${addressBean.province_name}${addressBean.city_name}${addressBean.district_name}${addressBean.address}"
        }
    }

    override fun initEvent() {

        addressLayout.setOnClickListener {
            val intent = Intent(this, AddressActivity::class.java)
            intent.putExtra(FROM_ORDER, FROM_ORDER)
            startActivityForResult(intent, mRequestCode)
        }

        settle.setOnClickListener {
            val window = object : OrderPayPopupWindow(
                    this, R.layout.pop_order_pay,
                    LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT
            ) {}
            window.showAtLocation(parentLayout, Gravity.BOTTOM, 0, 0)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.detachView()
    }

    override fun start() {
        presenter.requestPostOrder(0, "", "", "",
                "", "", "", "",
                "", "", "", "",
                "", "", "",
                "", "", "")
    }

//    private fun getCartData(): ArrayList<ShopList> {
//        val list = ArrayList<ShopList>()
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
//        return list
//    }
}