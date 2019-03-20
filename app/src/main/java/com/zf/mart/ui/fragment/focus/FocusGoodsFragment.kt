package com.zf.mart.ui.fragment.focus

import android.view.Gravity
import android.widget.LinearLayout
import android.widget.RadioButton
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.scwang.smartrefresh.layout.util.DensityUtil
import com.zf.mart.R
import com.zf.mart.base.BaseFragment
import com.zf.mart.ui.adapter.FocusGoodsAdapter
import com.zf.mart.ui.adapter.FocusLoveGoodsAdapter
import com.zf.mart.utils.LogUtils
import com.zf.mart.view.RecDecoration
import com.zf.mart.view.recyclerview.RecyclerViewDivider
import kotlinx.android.synthetic.main.fragment_focus_goods.*

class FocusGoodsFragment : BaseFragment() {

    companion object {
        fun newInstance(): FocusGoodsFragment {
            return FocusGoodsFragment()
        }
    }

    override fun getLayoutId(): Int = R.layout.fragment_focus_goods

    //已关注的商品列表
    private val adapter by lazy { FocusGoodsAdapter(context) }

    //猜你喜欢的商品列表
    private val loveAdapter by lazy { FocusLoveGoodsAdapter(context) }

    override fun initView() {

        //已关注商品的列表
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = adapter
        recyclerView.addItemDecoration(
            RecyclerViewDivider(
                context,
                LinearLayoutManager.VERTICAL,
                DensityUtil.dp2px(5f),
                ContextCompat.getColor(
                    context!!,
                    R.color.colorBackground
                )
            )
        )



        //猜你喜欢的商品
        val manager = GridLayoutManager(context, 2)
        loveGoodsRecyclerView.layoutManager = manager
        loveGoodsRecyclerView.adapter = loveAdapter
        loveGoodsRecyclerView.addItemDecoration(RecDecoration(DensityUtil.dp2px(12f)))


        /** 动态添加筛选按钮 */
        repeat(10) {
            val radioBtn = RadioButton(context)
            radioBtn.text = "有券$it"
            /** 通过设置tag来区分选中哪个按钮 */
            radioBtn.tag = it
            radioBtn.buttonDrawable = null
            radioBtn.textSize = 12f
            //改变radioButton选中颜色用getColorStateList
            radioBtn.setTextColor(ContextCompat.getColorStateList(context!!, R.color.selector_focus_condition))
            radioBtn.background = ContextCompat.getDrawable(context!!, R.drawable.selector_focus_condition)
            radioBtn.gravity = Gravity.CENTER
            radioBtn.setPadding(
                DensityUtil.dp2px(8f), DensityUtil.dp2px(2f),
                DensityUtil.dp2px(8f), DensityUtil.dp2px(2f)
            )
            val lp = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            lp.setMargins(0, 0, DensityUtil.dp2px(20f), 0)
            radioBtn.layoutParams = lp
            radioGroup.addView(radioBtn)
        }

        /** 筛选按钮点击事件 */
        radioGroup.setOnCheckedChangeListener { group, checkedId ->
            val radioBtn = group.findViewById<RadioButton>(checkedId)
            LogUtils.e("tag:" + radioBtn.tag)
        }

    }

    override fun lazyLoad() {
    }

    override fun initEvent() {
    }
}