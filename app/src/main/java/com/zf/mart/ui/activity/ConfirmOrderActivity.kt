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
import kotlinx.android.synthetic.main.layout_order_other.*
import kotlinx.android.synthetic.main.layout_order_price.*
import kotlinx.android.synthetic.main.layout_toolbar.*

class ConfirmOrderActivity : BaseActivity(), PostOrderContract.View {


    override fun initToolBar() {
        StatusBarUtils.darkMode(this, ContextCompat.getColor(this, R.color.colorSecondText), 0.3f)
        back.setOnClickListener { finish() }
        titleName.text = "确定订单"
        rightLayout.visibility = View.INVISIBLE
    }

    /** 提交订单成功*/
    override fun setConfirmOrder(bean: PostOrderBean) {
        val window = object : OrderPayPopupWindow(
                this, R.layout.pop_order_pay,
                LinearLayout.LayoutParams.MATCH_PARENT, DensityUtil.dp2px(320f), mTotalPrice
        ) {}
        window.showAtLocation(parentLayout, Gravity.BOTTOM, 0, 0)
        window.onDismissListener = {
            MyOrderActivity.actionStart(this, "")
            finish()
        }
    }


    /** 结算 */
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
            mTotalPrice = it.total_amount
        }

        bean.address.let {
            userName.text = it.consignee
            userPhone.text = it.mobile
            userAddress.text = "${it.province_name}${it.city_name}${it.district_name}${it.address}"
            mAddressId = it.address_id
        }

    }

    override fun showError(msg: String, errorCode: Int) {
        showToast(msg)
    }

    override fun showLoading() {
        showLoadingDialog()
    }

    override fun dismissLoading() {
        dismissLoadingDialog()
    }

    companion object {
        const val mRequestCode = 10
        const val FROM_ORDER = "fromOrder" //选择送货地址标记
        fun actionStart(context: Context?, promType: Int,
                        action: String, goods_id: String,
                        goods_num: String, item_id: String, promId: String) {
            val intent = Intent(context, ConfirmOrderActivity::class.java)
            intent.putExtra("prom", promType) //prom: 0默认,1秒杀,2团购,3优惠促销,4预售,5虚拟(5其实没用),6拼团,7搭配购,8竞拍
            intent.putExtra("action", action) //立即购买	1或0,1是，0否，默认0
            intent.putExtra("goodId", goods_id)
            intent.putExtra("goodNum", goods_num)
            intent.putExtra("itemId", item_id)
            intent.putExtra("promId", promId)
            context?.startActivity(intent)
        }
    }

    override fun layoutId(): Int = R.layout.activity_confirm_order

    private val presenter by lazy { PostOrderPresenter() }

    override fun initData() {
        mPromType = intent.getIntExtra("prom", 0)
        mAction = intent.getStringExtra("action")
        mGoodId = intent.getStringExtra("goodId")
        mGoodNum = intent.getStringExtra("goodNum")
        mGoodItemId = intent.getStringExtra("itemId")
        mPromId = intent.getStringExtra("promId")
    }

    //购物车适配器
    private var goodsData = ArrayList<Goods>()
    private val adapter by lazy { EnGoodsAdapter(this, goodsData) }


    private var mAddressId = ""
    private var mTotalPrice = ""
    private var mPromType = 0
    private var mAction = ""
    private var mGoodId = ""
    private var mGoodNum = ""
    private var mGoodItemId = ""
    private var mPromId = ""

    override fun start() {
        presenter.requestPostOrder(
                0, mPromType, mAddressId, "", "",
                "", "", "", "",
                "", "", mGoodId, mGoodNum,
                mGoodItemId, mAction, "",
                "", "", "", mPromId
        )
    }

    override fun initEvent() {
        addressLayout.setOnClickListener {
            val intent = Intent(this, AddressActivity::class.java)
            intent.putExtra(FROM_ORDER, FROM_ORDER)
            startActivityForResult(intent, mRequestCode)
        }

        //提交订单
        settle.setOnClickListener {
            presenter.requestPostOrder(
                    1, mPromType, mAddressId, "",
                    "", "", "", "",
                    "", remark.text.toString(), "",
                    mGoodId, mGoodNum, mGoodItemId, mAction, "",
                    "", "", "", mPromId
            )
        }
    }

    override fun initView() {
        presenter.attachView(this)
        goodsRecyclerView.layoutManager = LinearLayoutManager(this)
        goodsRecyclerView.adapter = adapter
        goodsRecyclerView.addItemDecoration(rvDivider)
    }

    //选择地址回调
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (mRequestCode == requestCode && AddressActivity.mResultCode == resultCode) {
            val addressBean = data?.getSerializableExtra(AddressActivity.ADDRESS_FLAG) as AddressBean
            userName.text = addressBean.consignee
            userPhone.text = addressBean.mobile
            userAddress.text =
                    "${addressBean.province_name}${addressBean.city_name}${addressBean.district_name}${addressBean.address}"
            mAddressId = addressBean.address_id
        }
    }

    private val rvDivider by lazy {
        RecyclerViewDivider(
                this,
                LinearLayoutManager.VERTICAL,
                DensityUtil.dp2px(1f),
                ContextCompat.getColor(this, R.color.colorBackground)
        )
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.detachView()
    }

}