package com.zf.mart.ui.fragment

import androidx.recyclerview.widget.LinearLayoutManager
import com.zf.mart.R
import com.zf.mart.base.BaseFragment
import com.zf.mart.ui.adapter.DiscountAdapter
import com.zf.mart.view.dialog.DiscountDialog
import kotlinx.android.synthetic.main.fragment_discount.*

/**
 * 优惠券中心
 */
class DiscountFragment : BaseFragment() {

    private var type = ""

    companion object {

        const val unUse = "unUse"
        const val haveUse = "haveUse"
        const val timeOut = "timeOut"

        fun newInstance(type: String): DiscountFragment {
            val fragment = DiscountFragment()
            fragment.type = type
            return fragment
        }
    }

    override fun getLayoutId(): Int = R.layout.fragment_discount

    private val adapter by lazy { DiscountAdapter(context, type) }

    override fun initView() {
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = adapter

    }


    override fun initEvent() {
        adapter.setOnClickListerner(object : DiscountAdapter.OnItemClickListener {
            override fun onClick() {
                DiscountDialog.showDialog(childFragmentManager)
            }
        })
    }

    override fun lazyLoad() {
    }
}