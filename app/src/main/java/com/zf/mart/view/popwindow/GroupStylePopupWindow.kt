package com.zf.mart.view.popwindow

import android.app.Activity
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.widget.PopupWindow
import android.widget.TextView
import com.zf.mart.R
import com.zf.mart.mvp.bean.CartGoodsList
import com.zf.mart.mvp.bean.SpecList
import com.zhy.view.flowlayout.FlowLayout
import com.zhy.view.flowlayout.TagAdapter
import kotlinx.android.synthetic.main.pop_order_style.view.*

/**
 * 拼团的选择款式
 */
abstract class GroupStylePopupWindow(
        var context: Activity,
        layoutRes: Int,
        w: Int,
        h: Int,
        private val bean: CartGoodsList,
        private val specBean: List<SpecList>
) {
    val contentView: View
    val popupWindow: PopupWindow
    private var isShowing = false

    init {
        contentView = LayoutInflater.from(context).inflate(layoutRes, null, false)
        initView()
        popupWindow = PopupWindow(contentView, w, h, true)
        initWindow()
    }


    var onNumberListener: ((num: Int) -> Unit)? = null
    var onSpecListener: ((SpecList) -> Unit)? = null

    private fun initView() {
        contentView.apply {

            number.text = bean.goods_num.toString()


            val history = ArrayList<String>()

            var choosePos: Int? = null

            repeat(specBean.size) {
                history.add(specBean[it].item ?: "")
                if (bean.spec_key_name == specBean[it].item) {
                    choosePos = it
                }
            }

            hotLayout.adapter = object : TagAdapter<String>(history) {
                override fun getView(parent: FlowLayout?, position: Int, t: String?): View {
                    val tv: TextView = LayoutInflater.from(context).inflate(
                            R.layout.layout_textview_style, hotLayout, false
                    ) as TextView
                    tv.text = t
                    return tv
                }
            }

            //默认选中规格
            if (choosePos != null) {
                hotLayout.adapter.setSelectedList(setOf(choosePos))
            }

            hotLayout.setOnTagClickListener { _, position, _ ->
                onSpecListener?.invoke(specBean[position])
                return@setOnTagClickListener true
            }

            reduce.isSelected = number.text.toString().toInt() < 2
            reduce.setOnClickListener {
                if (number.text.toString().toInt() > 1) {
                    onNumberListener?.invoke(number.text.toString().toInt() - 1)
                    number.text = (number.text.toString().toInt() - 1).toString()
                }
            }

            increase.setOnClickListener {
                onNumberListener?.invoke(number.text.toString().toInt() + 1)
                number.text = (number.text.toString().toInt() + 1).toString()

            }

            number.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(s: Editable?) {
                }

                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    reduce.isSelected = s.toString().toInt() < 2
                }
            })

            confirm.setOnClickListener {
                onDismiss()
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