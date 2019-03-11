package com.zf.mart.ui.fragment.same

import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.zf.mart.R
import com.zf.mart.base.BaseFragment
import com.zf.mart.ui.adapter.HomeFragmentRecommendAdapter
import kotlinx.android.synthetic.main.fragment_rv.*

class PageRvFragment : BaseFragment() {

    companion object {
        fun newInstans(): PageRvFragment {
            return PageRvFragment()
        }
    }

    private val adapter by lazy { HomeFragmentRecommendAdapter(context) }

    override fun getLayoutId(): Int = R.layout.fragment_rv

    override fun initView() {
        recyclerView.layoutManager = GridLayoutManager(context, 2)
        recyclerView.adapter = adapter
    }

    override fun lazyLoad() {
    }

    override fun initEvent() {
    }
}