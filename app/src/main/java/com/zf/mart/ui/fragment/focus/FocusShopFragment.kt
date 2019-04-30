package com.zf.mart.ui.fragment.focus

import android.util.Log
import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.scwang.smartrefresh.layout.util.DensityUtil
import com.zf.mart.R
import com.zf.mart.base.BaseFragment
import com.zf.mart.mvp.bean.FollowShopList
import com.zf.mart.mvp.bean.MyFollowShopBean
import com.zf.mart.mvp.contract.MyFollowShopContract
import com.zf.mart.mvp.presenter.MyFollowShopPresenter
import com.zf.mart.net.exception.ErrorStatus
import com.zf.mart.showToast
import com.zf.mart.ui.adapter.FocusShopAdapter
import com.zf.mart.ui.adapter.LoveShopAdapter
import com.zf.mart.view.recyclerview.RecyclerViewDivider
import com.zf.mart.view.recyclerview.SwipeItemLayout
import kotlinx.android.synthetic.main.fragment_focus_shop.*

class FocusShopFragment : BaseFragment(), MyFollowShopContract.View {

    override fun showError(msg: String, errorCode: Int) {
        refreshLayout.setEnableLoadMore(false)
        if (errorCode == ErrorStatus.NETWORK_ERROR) {
            mLayoutStatusView?.showNoNetwork()
        } else {
            mLayoutStatusView?.showError()
            showToast(msg)
        }
    }

    //删除或添加关注
    override fun delMyFollowShop(msg: String) {
        showToast(msg)
        lazyLoad()
        love_shop_ly.visibility = View.GONE
        refreshLayout.setNoMoreData(false)
    }

    //获得第一页关注店铺列表
    override fun getMyFollowShop(bean: MyFollowShopBean) {
        mData.clear()
        mData.addAll(bean.list)
        shopSum.text = bean.count
        shopAdapter.notifyDataSetChanged()
    }

    //当第一页数据为空时
    override fun freshEmpty() {
        shopSum.text = "0"
        mData.clear()
        shopAdapter.notifyDataSetChanged()

    }

    //获得之后页数据
    override fun setLoadMore(bean: List<FollowShopList>) {
        mData.addAll(bean)
        shopAdapter.notifyDataSetChanged()
    }

    //实际获得数据小于一页最大数据时  加载完成
    override fun setLoadComplete() {
        refreshLayout.finishLoadMoreWithNoMoreData()
        //显示推荐店铺布局
        love_shop_ly.visibility=View.VISIBLE
    }

    //下拉加载错误
    override fun loadMoreError(msg: String, errorCode: Int) {
        showToast(msg)
    }

    override fun setBuyError(msg: String) {

    }

    override fun showLoading() {

    }

    override fun dismissLoading() {
        refreshLayout.finishRefresh()
        refreshLayout.finishLoadMore()
    }

    companion object {
        fun newInstance(): FocusShopFragment {
            return FocusShopFragment()
        }
    }

    override fun getLayoutId(): Int = R.layout.fragment_focus_shop

    // 关注的店铺
    private val shopAdapter by lazy { FocusShopAdapter(context, mData) }

    // 推荐的店铺
    private val loveAdapter by lazy { LoveShopAdapter(context) }

    //网络接收数据
    private var mData = ArrayList<FollowShopList>()

    private val presenter by lazy { MyFollowShopPresenter() }

    override fun initView() {
        presenter.attachView(this)


        /**  已关注店铺列表 */
        shopRecyclerView.layoutManager = LinearLayoutManager(context)
        shopRecyclerView.adapter = shopAdapter
        shopRecyclerView.addOnItemTouchListener(SwipeItemLayout.OnSwipeItemTouchListener(context))

        /** 猜你喜欢店铺列表 */
        loveRecyclerView.layoutManager = LinearLayoutManager(context)
        loveRecyclerView.adapter = loveAdapter


    }

    override fun lazyLoad() {
        presenter.requestMyFollowShop(1, 6)
    }

    override fun initEvent() {
        /**上拉加载*/
        refreshLayout.setOnLoadMoreListener {
            presenter.requestMyFollowShop(null, 6)
        }
        /**下拉刷新*/
        refreshLayout.setOnRefreshListener {
            lazyLoad()
        }
        //删除店铺
        shopAdapter.mClickListener = {
            presenter.requestDelMyFollowShop(it.seller_id, "0", it.collect_id)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.detachView()
    }

}