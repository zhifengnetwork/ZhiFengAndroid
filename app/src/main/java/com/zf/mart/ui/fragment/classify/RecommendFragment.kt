package com.zf.mart.ui.fragment.classify

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.zf.mart.R
import com.zf.mart.base.BaseFragment
import com.zf.mart.ui.adapter.ClassifyRightAdapter
import kotlinx.android.synthetic.main.frament_classify_recommend.*

class RecommendFragment:BaseFragment() {
    //在真正的开发中，每个界面的ID可能是不同的，所以这里会接收一个ID
    companion object {
        @JvmStatic
        fun buildFragment(id: String): RecommendFragment {
            var fragment = RecommendFragment()
            var bundle = Bundle()
            bundle.putString("id", id)
            fragment.arguments = bundle
            return fragment
        }
    }

    private var list= ArrayList<Int>()
    private var id=""
    private val rightAdapter by lazy { ClassifyRightAdapter(context,list,id) }

    override fun getLayoutId(): Int=R.layout.frament_classify_recommend

    override fun initView() {
        id= arguments!!.getString("id")
        if(id=="为您推荐"){
            list.clear()
            for(i in 1..4){
                list.add(i)
            }
        }else if(id=="美容彩妆"){
            list.clear()
            for(i in 1..2){
                list.add(i)
            }
        }
        rightRecyclerView.layoutManager=LinearLayoutManager(context)
        rightRecyclerView.adapter=rightAdapter
    }

    override fun lazyLoad() {

    }

    override fun initEvent() {

    }





//    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
//        return super.onCreateView(inflater, container, savedInstanceState)
//        return inflater.inflate(R.layout.frament_classify_recommend,container, false)
//    }
//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//        //        test_tv.text = "我是分类界面" + arguments?.getString("id")
//    }
}