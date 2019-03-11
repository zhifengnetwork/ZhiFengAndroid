package com.zf.mart.ui.fragment

import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.scwang.smartrefresh.layout.util.DensityUtil
import com.zf.mart.R
import com.zf.mart.base.BaseFragment
import com.zf.mart.ui.adapter.CartShopAdapter
import com.zf.mart.ui.adapter.HomeFragmentRecommendAdapter
import com.zf.mart.view.RecDecoration
import com.zf.mart.view.recyclerview.RecyclerViewDivider
import kotlinx.android.synthetic.main.fragment_shoping_cart.*

/**
 * 购物车页面
 */
class ShoppingCartFragment : BaseFragment() {

    companion object {
        fun getInstance(): ShoppingCartFragment {
            return ShoppingCartFragment()
        }
    }

    override fun getLayoutId(): Int = R.layout.fragment_shoping_cart

    //猜喜欢适配器
    private val recommendAdapter by lazy { HomeFragmentRecommendAdapter(context) }
    //购物车适配器
    private val cartAdapter by lazy { CartShopAdapter(context) }

    private fun initCart() {
        cartRecyclerView.layoutManager = LinearLayoutManager(context)
        cartRecyclerView.adapter = cartAdapter
        cartRecyclerView.addItemDecoration(
            RecyclerViewDivider(
                context,
                LinearLayoutManager.VERTICAL,
                DensityUtil.dp2px(10f),
                ContextCompat.getColor(context!!, R.color.colorBackground)
            )
        )
    }

    private fun initCommend() {
        shop_cat_specail_rec.layoutManager = GridLayoutManager(context, 2)
        shop_cat_specail_rec.adapter = recommendAdapter
        shop_cat_specail_rec.addItemDecoration(RecDecoration(DensityUtil.dp2px(12f)))
    }

    override fun initView() {

        //购物车
        initCart()

        //猜喜欢
        initCommend()


    }

    override fun lazyLoad() {
    }

    override fun initEvent() {
    }
}