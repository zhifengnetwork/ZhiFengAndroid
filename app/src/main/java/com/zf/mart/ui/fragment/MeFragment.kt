package com.zf.mart.ui.fragment

import androidx.recyclerview.widget.GridLayoutManager
import com.scwang.smartrefresh.layout.util.DensityUtil
import com.zf.mart.R
import com.zf.mart.base.BaseFragment
import com.zf.mart.ui.activity.*
import com.zf.mart.ui.adapter.HomeFragmentRecommendAdapter
import com.zf.mart.ui.adapter.MeSpecailAdapter
import com.zf.mart.view.RecDecoration
import kotlinx.android.synthetic.main.fragment_me.*
import kotlinx.android.synthetic.main.layout_info.*
import kotlinx.android.synthetic.main.layout_order.*
import kotlinx.android.synthetic.main.layout_wallet.*
import kotlinx.android.synthetic.main.layout_zhuanlan.*

class MeFragment : BaseFragment() {

    companion object {
        fun getInstance(): MeFragment {
            return MeFragment()
        }
    }

    override fun getLayoutId(): Int = R.layout.fragment_me

    private val adapter by lazy { MeSpecailAdapter(context) }

    private val recommendData = ArrayList<String>()
    private val recommendAdapter by lazy { HomeFragmentRecommendAdapter(context, recommendData) }

    override fun initView() {
        //我的专栏
        me_specail_rec.layoutManager = GridLayoutManager(context, 4)
        me_specail_rec.adapter = adapter
        me_specail_rec.addItemDecoration(RecDecoration(12))

        //推荐
        recommendData.addAll(arrayListOf("1", "2", "3"))
        me_specail_recycler.layoutManager = GridLayoutManager(context, 2)
        me_specail_recycler.adapter = recommendAdapter
        me_specail_recycler.addItemDecoration(RecDecoration(DensityUtil.dp2px(12f)))
    }

    override fun lazyLoad() {
    }

    override fun initEvent() {
        //十天签到领礼品
        layout_info_gift.setOnClickListener {
           SinginGiftActivity.actionStart(context)
        }
        //积分
        integralLayout.setOnClickListener {
            IntegralActivity.actionStart(context)
        }

        //优惠券中心
        discountLayout.setOnClickListener {
            DiscountActivity.actionStart(context)
        }

        //我的钱包
        myWallet.setOnClickListener {
            WalletActivity.actionStart(context)
        }

        //个人资料
        headLayout.setOnClickListener {
            InfoActivity.actionStart(context)
        }

        //待付款
        waitPay.setOnClickListener {
            MyOrderActivity.actionStart(context, MyOrderActivity.waitPay)
        }

        //待发货
        waiSend.setOnClickListener {
            MyOrderActivity.actionStart(context, MyOrderActivity.waiSend)
        }

        //待收货
        waitTake.setOnClickListener {
            MyOrderActivity.actionStart(context, MyOrderActivity.waitTake)
        }

        //待评价
        waitEva.setOnClickListener {
            MyOrderActivity.actionStart(context, MyOrderActivity.waitEva)
        }

        //所有订单
        allOrder.setOnClickListener {
            MyOrderActivity.actionStart(context, MyOrderActivity.all)
        }

        //商品关注
        goodsFocus.setOnClickListener {
            FocusActivity.actionStart(context, FocusActivity.GOODS)
        }

        //店铺关注
        shopFocus.setOnClickListener {
            FocusActivity.actionStart(context, FocusActivity.SHOP)
        }

        //足迹
        footPrint.setOnClickListener {
            FootActivity.actionStart(context)
        }
    }
}