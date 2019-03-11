package com.zf.mart.ui.fragment.focus

import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.scwang.smartrefresh.layout.util.DensityUtil
import com.zf.mart.R
import com.zf.mart.base.BaseFragment
import com.zf.mart.ui.adapter.FocusShopAdapter
import com.zf.mart.ui.adapter.LoveShopAdapter
import com.zf.mart.view.recyclerview.RecyclerViewDivider
import kotlinx.android.synthetic.main.fragment_focus_shop.*

class FocusShopFragment : BaseFragment() {

    companion object {
        fun newInstance(): FocusShopFragment {
            return FocusShopFragment()
        }
    }

    override fun getLayoutId(): Int = R.layout.fragment_focus_shop

    private val adapter by lazy { FocusShopAdapter(context) }

    private val loveAdapter by lazy { LoveShopAdapter(context) }

    override fun initView() {

        /**  已关注店铺列表 */
        shopRecyclerView.layoutManager = LinearLayoutManager(context)
        shopRecyclerView.adapter = adapter
        shopRecyclerView.addItemDecoration(
            RecyclerViewDivider(
                context,
                LinearLayoutManager.VERTICAL,
                DensityUtil.dp2px(5f),
                ContextCompat.getColor(context!!, R.color.colorBackground)
            )
        )

        /** 猜你喜欢店铺列表 */
        loveRecyclerView.layoutManager = LinearLayoutManager(context)
        loveRecyclerView.adapter = loveAdapter
        loveRecyclerView.addItemDecoration(
            RecyclerViewDivider(
                context,
                LinearLayoutManager.VERTICAL,
                DensityUtil.dp2px(7f),
                ContextCompat.getColor(context!!, R.color.colorBackground)
            )
        )

    }

    override fun lazyLoad() {
    }

    override fun initEvent() {
    }
}