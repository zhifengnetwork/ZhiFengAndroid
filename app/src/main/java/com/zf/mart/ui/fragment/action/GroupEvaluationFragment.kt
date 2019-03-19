package com.zf.mart.ui.fragment.action

import androidx.recyclerview.widget.LinearLayoutManager
import com.zf.mart.R
import com.zf.mart.base.BaseFragment
import com.zf.mart.ui.adapter.EvaluationAdapter
import kotlinx.android.synthetic.main.fragment_evaluation.*

/**
 * 评价fragment
 */
class GroupEvaluationFragment : BaseFragment() {

    companion object {
        fun newInstance(): GroupEvaluationFragment {
            return GroupEvaluationFragment()
        }
    }

    override fun getLayoutId(): Int = R.layout.fragment_evaluation

    private val adapter by lazy { EvaluationAdapter(context) }

    override fun initView() {
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = adapter
    }

    override fun lazyLoad() {
    }

    override fun initEvent() {
    }
}