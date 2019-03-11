package com.zf.mart.view

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.zf.mart.utils.LogUtils


class RecDecoration(private val space: Int) : RecyclerView.ItemDecoration() {


    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        super.getItemOffsets(outRect, view, parent, state)
        //不是第一个的格子都设一个左边和底部的间距
        outRect.left = space
//                outRect.bottom = space;
        outRect.right = space
        outRect.top = space
        //由于每行都只有3个，所以第一个都是3的倍数，把左边距设为0
//        if (parent.getChildLayoutPosition(view) % 5 === 0) {
//            outRect.left = 0
//        }

        //如果是一行两个，设置第二个的倍数的view左边距为0

        if (parent.getChildLayoutPosition(view) % 2 != 0) {

            outRect.left = 0
        }


    }

}
