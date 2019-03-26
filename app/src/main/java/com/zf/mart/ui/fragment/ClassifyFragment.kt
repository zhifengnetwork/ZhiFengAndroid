package com.zf.mart.ui.fragment

import android.widget.LinearLayout
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager.widget.PagerAdapter
import com.scwang.smartrefresh.layout.util.DensityUtil
import com.zf.mart.R
import com.zf.mart.base.BaseFragment
import com.zf.mart.ui.adapter.ClassifyPagerAdapter
import com.zf.mart.ui.adapter.ClassifyTitleAdapter
import com.zf.mart.view.recyclerview.RecyclerViewDivider
import com.zf.mart.view.verticalViewPager.DefaultTransformer
import kotlinx.android.synthetic.main.fragment_classify.*
import kotlinx.android.synthetic.main.frament_classify_recommend.*


class ClassifyFragment : BaseFragment() {
    companion object {
        fun getInstance(): ClassifyFragment {
            return ClassifyFragment()
        }
    }

    override fun getLayoutId(): Int = R.layout.fragment_classify

    private val data = ArrayList<String>()

    private var list = mutableListOf<String>()

    private val adapter by lazy { ClassifyTitleAdapter(context, data) }

    private var mPagerAdapter: PagerAdapter? = null

//    private val rightAdapter by lazy { ClassifyRightAdapter(context,list) }

    private val lefttitle: Array<String> = arrayOf(
        "为您服务",
        "品牌墙",
        "美容彩妆",
        "奶粉纸尿裤",
        "外部专区",
        "轻奢",
        "个人护理",
        "手表配饰",
        "数码家电",
        "家居生活",
        "环球美食",
        "服饰靴鞋",
        "家用家电",
        "医药保健",
        "汽车生活"
    )

    override fun initView() {

        /** 左边分类 */
        for (i in lefttitle.iterator()) {

            data.add(i)

        }


        leftRecyclerView.layoutManager = LinearLayoutManager(context)
        leftRecyclerView.adapter = adapter

        //给右边的viewpager设置adapter
        for (i in 1..5) {
            list.add("test$i")
        }
        mPagerAdapter=ClassifyPagerAdapter(childFragmentManager,list)
        rightViewPager.setPageTransformer(true, DefaultTransformer())
        rightViewPager.setNoScroll(true)
        rightViewPager.adapter=mPagerAdapter
//        for (i in 1..4) {
//            list.add(i)
//        }
//
//
//        rightRecyclerView.layoutManager = LinearLayoutManager(context)
//
//        rightRecyclerView.adapter = rightAdapter

//设置item里面的间隔 背景
//        context?.apply {
//            rightRecyclerView.addItemDecoration(
//                RecyclerViewDivider(
//                    context, LinearLayout.VERTICAL, DensityUtil.dp2px(10f)
//                    , ContextCompat.getColor(this, R.color.colorBackground)
//                )
//            )
//        }


    }

    override fun lazyLoad() {
    }

    override fun initEvent() {
        adapter.setOnItemClickListener(object : ClassifyTitleAdapter.OnItemClickListener {
            override fun onItemClick(position: Int) {
                rightViewPager.currentItem = position
                Toast.makeText(context,"点击了"+position, Toast.LENGTH_SHORT).show()
            }
        })
    }
}