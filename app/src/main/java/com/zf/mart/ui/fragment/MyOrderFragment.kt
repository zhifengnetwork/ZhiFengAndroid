package com.zf.mart.ui.fragment

import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.scwang.smartrefresh.layout.util.DensityUtil
import com.zf.mart.R
import com.zf.mart.base.BaseFragment
import com.zf.mart.ui.activity.MyOrderActivity
import com.zf.mart.ui.adapter.MyOrderAdapter
import com.zf.mart.view.recyclerview.RecyclerViewDivider
import kotlinx.android.synthetic.main.fragment_myorder.*

class MyOrderFragment : BaseFragment() {

    private var type = ""

    companion object {

        fun newInstance(type: String): MyOrderFragment {
            val fragment = MyOrderFragment()
            fragment.type = type
            return fragment
        }
    }

    override fun getLayoutId(): Int = R.layout.fragment_myorder


    private val adapter by lazy { MyOrderAdapter(context) }

    override fun initView() {

        //判断type,如果是待发货，显示空页面
        if (MyOrderActivity.waitPay == type) {
            multipleStatusView.showEmpty()
        }

        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = adapter
    }

    override fun lazyLoad() {
    }

    override fun initEvent() {
    }
}