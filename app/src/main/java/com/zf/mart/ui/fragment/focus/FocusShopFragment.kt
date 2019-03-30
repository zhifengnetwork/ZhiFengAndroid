package com.zf.mart.ui.fragment.focus

import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.scwang.smartrefresh.layout.util.DensityUtil
import com.zf.mart.R
import com.zf.mart.base.BaseFragment
import com.zf.mart.ui.adapter.FocusShopAdapter
import com.zf.mart.ui.adapter.LoveShopAdapter
import com.zf.mart.view.recyclerview.RecyclerViewDivider
import com.zf.mart.view.recyclerview.SwipeItemLayout
import kotlinx.android.synthetic.main.fragment_focus_shop.*

class FocusShopFragment : BaseFragment() {

    companion object {
        fun newInstance(): FocusShopFragment {
            return FocusShopFragment()
        }
    }

    override fun getLayoutId(): Int = R.layout.fragment_focus_shop

    // 关注的店铺
    private val shopAdapter by lazy { FocusShopAdapter(context) }

    // 推荐的店铺
    private val loveAdapter by lazy { LoveShopAdapter(context) }

    override fun initView() {

        /**  已关注店铺列表 */
        shopRecyclerView.layoutManager = LinearLayoutManager(context)
        shopRecyclerView.adapter = shopAdapter
        shopRecyclerView.addOnItemTouchListener(SwipeItemLayout.OnSwipeItemTouchListener(context))

        /** 猜你喜欢店铺列表 */
        loveRecyclerView.layoutManager = LinearLayoutManager(context)
        loveRecyclerView.adapter = loveAdapter


    }

    override fun lazyLoad() {
    }

    override fun initEvent() {
    }
}