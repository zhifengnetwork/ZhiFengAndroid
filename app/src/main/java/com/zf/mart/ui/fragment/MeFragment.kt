package com.zf.mart.ui.fragment

import android.annotation.SuppressLint
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
import com.zf.mart.mvp.bean.AppSignBean
import com.zf.mart.mvp.bean.CommendBean
import com.zf.mart.mvp.bean.CommendList
import com.zf.mart.mvp.bean.MeBean
import com.zf.mart.mvp.contract.CommendContract
import com.zf.mart.mvp.presenter.CommendPresenter
import com.zf.mart.showToast
import com.zf.mart.ui.activity.*
import com.zf.mart.ui.adapter.ColumnAdapter
import com.zf.mart.ui.adapter.CommendAdapter
import com.zf.mart.utils.GlideUtils
import com.zf.mart.utils.Preference
import com.zf.mart.view.RecDecoration
import com.zf.mart.view.popwindow.RegionPopupWindow
import kotlinx.android.synthetic.main.fragment_me.*
import kotlinx.android.synthetic.main.layout_benefit.*
import kotlinx.android.synthetic.main.layout_info.*
import kotlinx.android.synthetic.main.layout_order.*
import kotlinx.android.synthetic.main.layout_wallet.*
import kotlinx.android.synthetic.main.layout_zhuanlan.*
import kotlinx.android.synthetic.main.pop_sign_success.view.*

class MeFragment : BaseFragment(), CommendContract.View {

    override fun setMe(bean: MeBean) {
        goodsNum.text = bean.goods_collect_num
        shopNum.text = bean.seller_goods_num
        footNum.text = bean.goods_visit_num
        waitPayNum.text = bean.waitPay
        waitSendNum.text = bean.waitSend
        waitReceiveNum.text = bean.waitReceive
        waitEvaNum.text = bean.uncomment_count
        points.text = bean.pay_points
        discount.text = bean.coupon_num
    }

    //签到
    override fun appSignSuccess(bean: AppSignBean) {
        signData = bean
        window = object : RegionPopupWindow(
                activity as Activity, R.layout.pop_sign_success,
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT
        ) {
            @SuppressLint("SetTextI18n")
            override fun initView() {
                contentView?.apply {
                    //当前总积分
                    points.text = signData?.points
                    //连续签到天数
                    continue_sign.text = "已经连续签到" + signData?.continue_sign + "天"
                    //签到加积分
                    add_point.text = "+" + signData?.add_point + "分"
                }
            }
        }
        window.updata()
        window.showAtLocation(parentLayout, Gravity.CENTER, 0, 0)
    }

    override fun setRefreshCommend(bean: CommendBean) {
        refreshLayout.setEnableLoadMore(true)
        commendData.clear()
        commendData.addAll(bean.goods_list)
        commendAdapter.notifyDataSetChanged()
    }

    override fun setLoadMoreCommend(bean: CommendBean) {
        commendData.addAll(bean.goods_list)
        commendAdapter.notifyDataSetChanged()
    }

    override fun setEmpty() {
        refreshLayout.setEnableLoadMore(false)
    }

    override fun setLoadComplete() {
        refreshLayout.finishLoadMoreWithNoMoreData()
    }

    override fun loadMoreError(msg: String, errorCode: Int) {
        showToast(msg)
    }

    override fun showError(msg: String, errorCode: Int) {
        showToast(msg)
    }


    override fun showLoading() {
    }

    override fun dismissLoading() {
        refreshLayout.finishRefresh()
        refreshLayout.finishLoadMore()
    }

    companion object {
        fun getInstance(): MeFragment {
            return MeFragment()
        }
    }

    override fun getLayoutId(): Int = R.layout.fragment_me

    private val columnAdapter by lazy { ColumnAdapter(context) }

    private val commendData = ArrayList<CommendList>()
    private var signData: AppSignBean? = null
    private val commendAdapter by lazy { CommendAdapter(context, commendData) }
    //pop弹窗
    private lateinit var window: RegionPopupWindow

    override fun initView() {

        commendPresenter.attachView(this)

        //我的专栏
        columnRecyclerView.layoutManager = GridLayoutManager(context, 4)
        columnRecyclerView.adapter = columnAdapter
        columnRecyclerView.addItemDecoration(RecDecoration(12))

        //推荐
        recommendRecyclerView.layoutManager = GridLayoutManager(context, 2)
        recommendRecyclerView.adapter = commendAdapter
        recommendRecyclerView.addItemDecoration(RecDecoration(DensityUtil.dp2px(15f)))
    }

    override fun lazyLoad() {
        refreshLayout.setEnableLoadMore(false)
        refreshLayout.setNoMoreData(false)
        commendPresenter.requestCommend("is_recommend", 1)
        commendPresenter.requestMe()
    }

    private val token by Preference(UriConstant.TOKEN, "")
    private val commendPresenter by lazy { CommendPresenter() }

    override fun initEvent() {

        UserInfoLiveData.observe(viewLifecycleOwner, Observer { userInfo ->
            userInfo?.apply {
                userName.text = nickname
                GlideUtils.loadUrlImage(context, head_pic, avatar)
            }
        })

        refreshLayout.setOnRefreshListener {
            lazyLoad()
        }

        refreshLayout.setOnLoadMoreListener {
            commendPresenter.requestCommend("is_recommend", null)
        }

        //签到
        sign.setOnClickListener {
            //请求签到接口
            commendPresenter.requestAppSign()
        }

        //十天签到领礼品
        layout_info_gift.setOnClickListener {
            SignInGiftActivity.actionStart(context)
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

    override fun onDestroy() {
        super.onDestroy()
        commendPresenter.detachView()
    }
}