package com.zf.mart.ui.fragment


import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager.widget.PagerAdapter
import com.zf.mart.R
import com.zf.mart.base.BaseFragment
import com.zf.mart.ui.activity.SearchActivity
import com.zf.mart.ui.adapter.ClassifyPagerAdapter
import com.zf.mart.ui.adapter.ClassifyTitleAdapter
import com.zf.mart.view.verticalViewPager.DefaultTransformer
import kotlinx.android.synthetic.main.fragment_classify.*
import kotlinx.android.synthetic.main.layout_classify_title.*


class ClassifyFragment : BaseFragment() {
    companion object {
        fun getInstance(): ClassifyFragment {
            return ClassifyFragment()
        }
    }

    override fun getLayoutId(): Int = R.layout.fragment_classify

    private val data = ArrayList<String>()

    private val adapter by lazy { ClassifyTitleAdapter(context, data) }

    private var mPagerAdapter: PagerAdapter? = null



    private val lefttitle: Array<String> = arrayOf(
        "为您推荐",
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

        mPagerAdapter=ClassifyPagerAdapter(childFragmentManager,lefttitle)
        rightViewPager.setPageTransformer(true, DefaultTransformer())
        rightViewPager.setNoScroll(true)
        rightViewPager.adapter=mPagerAdapter



    }

    override fun lazyLoad() {
    }

    override fun initEvent() {

        //搜索框
        searchLayout.setOnClickListener {
            SearchActivity.actionStart(context, "")
        }

        //左边适配器点击事件
        adapter.setOnItemClickListener(object : ClassifyTitleAdapter.OnItemClickListener {
            override fun onItemClick(position: Int) {
                //分类右边页面跳转
                rightViewPager.currentItem = position
                //分类左边选中item字体变化
                adapter.setThisPosition(position)
                //更新适配器
                adapter.notifyDataSetChanged()
            }
        })
    }
}