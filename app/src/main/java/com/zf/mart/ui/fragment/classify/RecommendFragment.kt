package com.zf.mart.ui.fragment.classify

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.zf.mart.R
import com.zf.mart.base.BaseFragment

class RecommendFragment:BaseFragment() {
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
    override fun getLayoutId(): Int=R.layout.frament_classify_recommend

    override fun initView() {

    }

    override fun lazyLoad() {

    }

    override fun initEvent() {

    }

    //在真正的开发中，每个界面的ID可能是不同的，所以这里会接收一个ID



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