package com.zf.mart.ui.adapter

import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.zf.mart.ui.fragment.classify.RecommendFragment

class ClassifyPagerAdapter(fm: FragmentManager, titleList: List<String>): FragmentPagerAdapter(fm) {
    private val fragmentList = arrayOfNulls<Fragment>(titleList.size)
    private var list = titleList
    override fun getItem(position: Int): Fragment {
        //启动对应的 Fragment
//        if(position==3){
//            return TwoFragment.buildFragment(list[position])
//        }

        return RecommendFragment.buildFragment(list[position])
    }

    override fun getCount(): Int {

        return fragmentList.size
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val fragment = super.instantiateItem(container, position) as Fragment
        fragmentList[position] = fragment
        return fragment
    }

    fun getFragments(): Array<Fragment?> {
        return fragmentList
    }
}