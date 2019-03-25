package com.zf.mart.ui.fragment

import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.zf.mart.R
import com.zf.mart.base.BaseFragment
import com.zf.mart.ui.adapter.MessageAdapter
import com.zf.mart.view.recyclerview.RecyclerViewDivider
import kotlinx.android.synthetic.main.fragment_message.*

class MessageFragment : BaseFragment() {

    companion object {
        fun newInstance(): MessageFragment {
            return MessageFragment()
        }
    }

    override fun getLayoutId(): Int = R.layout.fragment_message

    private val adapter by lazy { MessageAdapter(context) }


    override fun initView() {
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = adapter
    }

    override fun lazyLoad() {

    }

    override fun initEvent() {

    }

}