package com.zf.mart.ui.fragment.focus

import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.scwang.smartrefresh.layout.util.DensityUtil
import com.zf.mart.R
import com.zf.mart.base.BaseFragment
import com.zf.mart.mvp.bean.FollowShopList
import com.zf.mart.mvp.contract.MyFollowShopContract
import com.zf.mart.mvp.presenter.MyFollowShopPresenter
import com.zf.mart.ui.adapter.FocusShopAdapter
import com.zf.mart.ui.adapter.LoveShopAdapter
import com.zf.mart.view.recyclerview.RecyclerViewDivider
import com.zf.mart.view.recyclerview.SwipeItemLayout
import kotlinx.android.synthetic.main.fragment_focus_shop.*

class FocusShopFragment : BaseFragment(), MyFollowShopContract.View {
    override fun showError(msg: String, errorCode: Int) {

    }

    override fun getMyFollowShop(bean: List<FollowShopList>) {
        mData.addAll(bean)
        shopAdapter.notifyDataSetChanged()
        shopSum.text= mData.size.toString()
    }

    override fun showLoading() {

    }

    override fun dismissLoading() {

    }

    companion object {
        fun newInstance(): FocusShopFragment {
            return FocusShopFragment()
        }
    }

    override fun getLayoutId(): Int = R.layout.fragment_focus_shop

    // 关注的店铺
    private val shopAdapter by lazy { FocusShopAdapter(context,mData) }

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
        presenter.requestMyFollowShop()
    }

    override fun initEvent() {
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.detachView()
    }

}