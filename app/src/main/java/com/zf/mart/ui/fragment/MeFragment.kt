package com.zf.mart.ui.fragment

import android.app.Activity
import android.view.Gravity
import android.widget.LinearLayout
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.scwang.smartrefresh.layout.util.DensityUtil
import com.zf.mart.R
import com.zf.mart.api.UriConstant
import com.zf.mart.base.BaseFragment
import com.zf.mart.livedata.UserInfoLiveData
import com.zf.mart.ui.activity.*
import com.zf.mart.ui.adapter.ColumnAdapter
import com.zf.mart.ui.adapter.HomeFragmentRecommendAdapter
import com.zf.mart.utils.GlideUtils
import com.zf.mart.utils.Preference
import com.zf.mart.view.RecDecoration
import com.zf.mart.view.popwindow.SignSuccessPopupWindow
import kotlinx.android.synthetic.main.fragment_me.*
import kotlinx.android.synthetic.main.layout_benefit.*
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

    private val columnAdapter by lazy { ColumnAdapter(context) }

    private val recommendData = ArrayList<String>()
    private val recommendAdapter by lazy { HomeFragmentRecommendAdapter(context, recommendData) }

    override fun initView() {
        //我的专栏
        columnRecyclerView.layoutManager = GridLayoutManager(context, 4)
        columnRecyclerView.adapter = columnAdapter
        columnRecyclerView.addItemDecoration(RecDecoration(12))

        //推荐
        recommendData.addAll(arrayListOf("1", "2", "3"))
        recommendRecyclerView.layoutManager = GridLayoutManager(context, 2)
        recommendRecyclerView.adapter = recommendAdapter
        recommendRecyclerView.addItemDecoration(RecDecoration(DensityUtil.dp2px(12f)))
    }

    override fun lazyLoad() {
        UserInfoLiveData.observe(viewLifecycleOwner, Observer { userInfo ->
            userInfo?.apply {
                userName.text = nickname
                GlideUtils.loadUrlImage(context, head_pic, avatar)
            }
        })
    }

    private val token by Preference(UriConstant.TOKEN, "")

    override fun initEvent() {
        //签到
        sign.setOnClickListener {
            val window = object : SignSuccessPopupWindow(
                    activity as Activity, R.layout.pop_sign_success,
                    LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT
            ) {
                override fun initView() {

                }
            }
            window.showAtLocation(parentLayout, Gravity.CENTER, 0, 0)
        }

        //十天签到领礼品
        layout_info_gift.setOnClickListener {
            SigninGiftActivity.actionStart(context)
        }
        //积分
        integralLayout.setOnClickListener {
            IntegralActivity.actionStart(context)
        }

        //公益
        benefit.setOnClickListener {
            BenefitActivity.actionStart(context)
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
            if (token.isNotEmpty()) {
                InfoActivity.actionStart(context)
            } else {
                LoginActivity.actionStart(context)
            }
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