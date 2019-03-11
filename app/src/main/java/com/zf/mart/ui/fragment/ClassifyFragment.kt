package com.zf.mart.ui.fragment

import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.zf.mart.R
import com.zf.mart.base.BaseFragment
import com.zf.mart.ui.adapter.ClassifyRightAdapter
import com.zf.mart.ui.adapter.ClassifyTitleAdapter
import com.zf.mart.view.RecDecoration
import kotlinx.android.synthetic.main.fragment_classify.*

class ClassifyFragment : BaseFragment() {
    companion object {
        fun getInstance(): ClassifyFragment {
            return ClassifyFragment()
        }
    }

    override fun getLayoutId(): Int = R.layout.fragment_classify

    private val data = ArrayList<String>()
    private val adapter by lazy { ClassifyTitleAdapter(context, data) }

    private val rightAdapter by lazy { ClassifyRightAdapter(context) }

    override fun initView() {

        /** 左边分类 */
        repeat(20) {
            data.add("分类$it")
        }

        leftRecyclerView.layoutManager = LinearLayoutManager(context)
        leftRecyclerView.adapter = adapter

        /** 右边子分类 */
        rightRecyclerView.layoutManager = GridLayoutManager(context, 2)
        rightRecyclerView.adapter = rightAdapter
        rightRecyclerView.addItemDecoration(RecDecoration(12))

    }

    override fun lazyLoad() {
    }

    override fun initEvent() {
    }
}