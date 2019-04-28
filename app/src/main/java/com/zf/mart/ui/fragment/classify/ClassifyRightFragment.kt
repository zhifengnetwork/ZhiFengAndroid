package com.zf.mart.ui.fragment.classify

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.zf.mart.R
import com.zf.mart.base.BaseFragment
import com.zf.mart.mvp.bean.ClassifyBean
import com.zf.mart.mvp.bean.ClassifyProductBean
import com.zf.mart.mvp.contract.ClassifyContract
import com.zf.mart.mvp.contract.ClassifyProductContract
import com.zf.mart.mvp.presenter.ClassifyPresenter
import com.zf.mart.mvp.presenter.ClassifyProductPresenter
import com.zf.mart.ui.adapter.ClassifyRightAdapter
import com.zf.mart.utils.LogUtils
import kotlinx.android.synthetic.main.frament_classify_recommend.*


class ClassifyRightFragment : BaseFragment(), ClassifyProductContract.View {
    override fun showError(msg: String, errorCode: Int) {

    }

    override fun setProduct(bean: List<ClassifyProductBean>) {
        LogUtils.e(">>>>:"+bean)
        classifyProductData.addAll(bean)
        rightAdapter.notifyDataSetChanged()
    }

    override fun showLoading() {

    }

    override fun dismissLoading() {

    }


    //在真正的开发中，每个界面的ID可能是不同的，所以这里会接收一个ID
    companion object {
        @JvmStatic
        fun buildFragment(id: String, name: String): ClassifyRightFragment {
            val fragment = ClassifyRightFragment()
            val bundle = Bundle()
            bundle.putString("name", name)
            bundle.putString("id", id)
            fragment.arguments = bundle
            return fragment
        }
    }

    //接收分类ID
    private var id: String = "110"

    //接收分类名字
    private var classifyname: String = ""
    private val rightAdapter by lazy { ClassifyRightAdapter(context, classifyProductData, classifyname) }

    //接收数据
    private val classifyProductData = ArrayList<ClassifyProductBean>()
    private val classifyProductPrediction by lazy { ClassifyProductPresenter() }

    override fun getLayoutId(): Int = R.layout.frament_classify_recommend

    override fun initView() {
        classifyProductPrediction.attachView(this)

        id = arguments?.getString("id").toString()

        classifyname = arguments?.getString("name").toString()

        rightRecyclerView.layoutManager = LinearLayoutManager(context)
        rightRecyclerView.adapter = rightAdapter
    }

    /**懒加载*/
    override fun lazyLoad() {

        classifyProductPrediction.requestClassifyProduct(id)
    }

    override fun initEvent() {
        see_more.setOnClickListener {

        }

    }

    override fun onDestroy() {
        super.onDestroy()
        classifyProductPrediction.detachView()
    }


}