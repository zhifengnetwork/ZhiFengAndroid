package com.zf.mart.ui.fragment.action

import androidx.recyclerview.widget.GridLayoutManager
import com.scwang.smartrefresh.layout.util.DensityUtil
import com.zf.mart.R
import com.zf.mart.base.BaseFragment
import com.zf.mart.ui.adapter.GroupAdapter
import com.zf.mart.utils.LogUtils
import com.zf.mart.view.RecDecoration
import kotlinx.android.synthetic.main.fragment_group.*

/**
 * 拼团
 */
class GroupFragment : BaseFragment() {
    override fun getLayoutId(): Int = R.layout.fragment_group

    private val adapter by lazy { GroupAdapter(context) }

    override fun initView() {

        LogUtils.e(">>>>>initView  GroupFragment")

        val layoutManager = GridLayoutManager(context, 2)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = adapter
        recyclerView.addItemDecoration(RecDecoration(DensityUtil.dp2px(12f)))
    }

    override fun lazyLoad() {
        LogUtils.e(">>>>>lazyLoad  GroupFragment")
    }

    override fun initEvent() {
    }
}