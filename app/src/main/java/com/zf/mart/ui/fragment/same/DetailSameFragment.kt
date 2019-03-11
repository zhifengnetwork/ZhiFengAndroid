package com.zf.mart.ui.fragment.same

import android.view.View
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.core.view.get
import androidx.viewpager.widget.ViewPager
import com.scwang.smartrefresh.layout.util.DensityUtil
import com.zf.mart.R
import com.zf.mart.base.BaseFragment
import com.zf.mart.showToast
import com.zf.mart.ui.adapter.LoveShopGoodsAdapter
import com.zf.mart.utils.LogUtils
import com.zf.mart.view.recyclerview.HorizontalPageLayoutManager
import com.zf.mart.view.recyclerview.PagingScrollHelper
import kotlinx.android.synthetic.main.fragment_detail_same.*

/**
 * 订单详情里面的相似推荐
 */
class DetailSameFragment : BaseFragment() {

    companion object {
        fun newInstance(): DetailSameFragment {
            LogUtils.e(">>>>DetailSameFragment newInstance")
            return DetailSameFragment()
        }
    }

    override fun getLayoutId(): Int = R.layout.fragment_detail_same


    private val adapter by lazy { LoveShopGoodsAdapter(context) }
    private val scrollHelper = PagingScrollHelper()

    override fun initView() {

        LogUtils.e(">>>>>>>DetailSameFragment initView")

        //indicatorLayout可能出现空
        val manager = HorizontalPageLayoutManager(2, 3)
        recyclerView.layoutManager = manager
        recyclerView.adapter = adapter
        scrollHelper.setUpRecycleView(recyclerView)
        scrollHelper.updateLayoutManger()
        scrollHelper.setOnPageChangeListener { pos ->
            repeat(4) {
                indicatorLayout.apply {
                    this[it].isSelected = pos == it
                }
            }
        }

        //滑动指示器
        //先写四页，后面再改
        repeat(4) {
            val view = View(context)
            view.background = ContextCompat.getDrawable(context!!, R.drawable.selector_same_indicator)
            val lp = LinearLayout.LayoutParams(DensityUtil.dp2px(5f), DensityUtil.dp2px(5f))
            lp.setMargins(DensityUtil.dp2px(3f), 0, DensityUtil.dp2px(3f), 0)
            indicatorLayout.addView(view, lp)
            indicatorLayout[0].isSelected = true
        }


    }


    override fun lazyLoad() {
    }

    override fun initEvent() {
    }
}