package com.zf.mart.ui.fragment.choice

import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import com.zf.mart.base.BaseFragment
import com.zf.mart.ui.adapter.ChoiceGoodsAdapter
import kotlinx.android.synthetic.main.fragment_choice_new.*


class NewFragment:BaseFragment(){
    companion object {
        fun getInstance():NewFragment{
            return NewFragment()
        }
    }
    override fun getLayoutId(): Int = com.zf.mart.R.layout.fragment_choice_new

    private val adapter by lazy { ChoiceGoodsAdapter(context) }

    override fun initView() {

        val layoutManager = LinearLayoutManager(context)
        layoutManager.orientation = LinearLayout.HORIZONTAL
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = adapter


    }

    override fun lazyLoad() {

    }

    override fun initEvent() {
    }

}