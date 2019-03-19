package com.zf.mart.view.popwindow

import android.app.Activity
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.widget.PopupWindow
import android.widget.TextView
import android.widget.Toast
import com.zf.mart.R
import com.zf.mart.showToast
import com.zf.mart.utils.LogUtils
import com.zhy.view.flowlayout.FlowLayout
import com.zhy.view.flowlayout.TagAdapter
import kotlinx.android.synthetic.main.pop_order_style.view.*

/**
 * 拼团的选择款式
 */
abstract class GroupStylePopupWindow(var context: Activity, layoutRes: Int, w: Int, h: Int) {
    val contentView: View
    val popupWindow: PopupWindow
    private var isShowing = false

    init {
        contentView = LayoutInflater.from(context).inflate(layoutRes, null, false)
        initView()
        popupWindow = PopupWindow(contentView, w, h, true)
        initWindow()
    }

    private var mListener: OnItemClickListener? = null

    fun setOnItemClickListener(listener: OnItemClickListener) {
        mListener = listener
    }

    interface OnItemClickListener {
        fun onBack(address: String)
    }

    private fun initView() {
        contentView.apply {

            val history = arrayOf("红色", "黑色", "黑色", "黑色", "黑色", "黑色", "黑色", "黑色", "黑色")
            hotLayout.adapter = object : TagAdapter<String>(history) {
                override fun getView(parent: FlowLayout?, position: Int, t: String?): View {
                    val tv: TextView = LayoutInflater.from(context).inflate(
                        R.layout.layout_textview_style, hotLayout, false
                    ) as TextView
                    tv.text = t
                    return tv
                }
            }

            hotLayout.setOnSelectListener {
                LogUtils.e(">>>>:" + it.size + "  " + it)
            }

            hotLayout.setOnTagClickListener { view, position, _ ->
                //                view.isSelected = ! view.isSelected
                Toast.makeText(context, history[position], Toast.LENGTH_LONG).show()
                return@setOnTagClickListener true
            }

        }
    }

    private fun initWindow() {
        popupWindow.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        popupWindow.isOutsideTouchable = true
        popupWindow.isTouchable = true
        /** 设置出入动画  */
        popupWindow.animationStyle = R.style.pop_translate
    }

    fun showAtLocation(parent: View, gravity: Int, x: Int, y: Int) {
        popupWindow.showAtLocation(parent, gravity, x, y)
        isShowing = true
        popupWindow.setOnDismissListener {
            //隐藏后显示背景为透明
            val lp = context.window.attributes
            lp.alpha = 1.0f
            context.window.clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND)
            context.window.attributes = lp
        }

        //显示时候设置背景为灰色
        val lp = context.window.attributes
        lp.alpha = 0.7f
        context.window.addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND)
        context.window.attributes = lp
    }

    fun onDismiss() {
        popupWindow.dismiss()
        isShowing = false

    }

}