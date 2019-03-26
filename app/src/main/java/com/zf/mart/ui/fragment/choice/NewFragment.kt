package com.zf.mart.ui.fragment.choice

import androidx.recyclerview.widget.DefaultItemAnimator
import com.zf.mart.R
import com.zf.mart.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_choice_new.*
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.lishide.recyclerview.scroll.SpaceItemDecoration
import com.zf.mart.ui.adapter.ChoiceGoodsAdapter


class NewFragment:BaseFragment(){
    companion object {
        fun getInstance():NewFragment{
            return NewFragment()
        }
    }
    override fun getLayoutId(): Int = com.zf.mart.R.layout.fragment_choice_new

    override fun initView() {

        //设置动画
        scroll_recycler_view.setItemAnimator(DefaultItemAnimator())
        // 设置布局管理器：瀑布流式
        scroll_recycler_view.layoutManager = StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.HORIZONTAL)
        // 根据需要设置间距等
        val right = resources.getDimension(R.dimen.dp_13).toInt()  //向右
        val bottom = resources.getDimension(R.dimen.dp_25).toInt()  //向下
        // 根据需要设置间距等
        val spacingInPixel = SpaceItemDecoration(right, bottom)
        scroll_recycler_view.addItemDecoration(spacingInPixel)

        //绑定适配器
        val adapter = ChoiceGoodsAdapter(context)
        scroll_recycler_view.adapter=adapter
    }

    override fun lazyLoad() {

    }

    override fun initEvent() {
    }

}