package com.zf.mart.ui.fragment.choice

import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.zf.mart.R
import com.zf.mart.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_choice_new.*
import com.zf.mart.ui.adapter.ChoiceGoodsAdapter


class NewFragment:BaseFragment(){
    companion object {
        fun getInstance():NewFragment{
            return NewFragment()
        }
    }
    private val adapter by lazy{ChoiceGoodsAdapter(context)}
    override fun getLayoutId(): Int = com.zf.mart.R.layout.fragment_choice_new

    override fun initView() {
       //设置横向RecyclerView
        val ms=LinearLayoutManager(context)
        ms.setOrientation(LinearLayoutManager.HORIZONTAL)

        scroll_recycler_view.layoutManager = ms

        scroll_recycler_view.adapter=adapter
    }

    override fun lazyLoad() {

    }

    override fun initEvent() {
    }

}