package com.zf.mart.ui.fragment

import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.zf.mart.R
import com.zf.mart.base.BaseFragment
import com.zf.mart.ui.adapter.ClassifyRightAdapter
import com.zf.mart.ui.adapter.ClassifyTitleAdapter
import com.zf.mart.utils.LogUtils
import com.zf.mart.view.RecDecoration
import kotlinx.android.synthetic.main.fragment_classify.*
import java.text.FieldPosition

class ClassifyFragment : BaseFragment() {
    companion object {
        fun getInstance(): ClassifyFragment {
            return ClassifyFragment()
        }
    }

    override fun getLayoutId(): Int = R.layout.fragment_classify

    private val data = ArrayList<String>()
    private val adapter by lazy { ClassifyTitleAdapter(context, data) }

    private val rightAdapter by lazy { ClassifyRightAdapter(context) }

    private  val lefttitle:Array<String> = arrayOf("为您服务", "品牌墙", "美容彩妆", "奶粉纸尿裤", "外部专区", "轻奢", "个人护理", "手表配饰", "数码家电", "家居生活", "环球美食", "服饰靴鞋", "家用家电", "医药保健", "汽车生活")
    override fun initView() {

        /** 左边分类 */
      for((index,e) in lefttitle.withIndex()){

            data.add(e)

      }


        leftRecyclerView.layoutManager = LinearLayoutManager(context)
        leftRecyclerView.adapter = adapter

        /** 右边子分类 */
        rightRecyclerView.layoutManager = LinearLayoutManager(context)
        rightRecyclerView.adapter = rightAdapter
//        rightRecyclerView.addItemDecoration(RecDecoration(12))

    }

    override fun lazyLoad() {
    }

    override fun initEvent() {
        adapter.setOnItemClickListener(object :ClassifyTitleAdapter.OnItemClickListener{
            override fun onItemClick(position: Int) {
                if(position==0){
                    rightRecyclerView.layoutManager = LinearLayoutManager(context)
                    rightRecyclerView.adapter = rightAdapter
                    rightAdapter.notifyDataSetChanged()
                }else if(position==1){

                }

            }
        })
    }
}