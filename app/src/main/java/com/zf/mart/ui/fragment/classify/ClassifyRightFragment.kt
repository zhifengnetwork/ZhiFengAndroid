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
import kotlinx.android.synthetic.main.frament_classify_recommend.*








class ClassifyRightFragment:BaseFragment(), ClassifyProductContract.View {
    override fun showError(msg: String, errorCode: Int) {

    }

    override fun setProduct(bean: List<ClassifyProductBean>) {
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
        fun buildFragment(id: String,name:String): ClassifyRightFragment {
            val fragment = ClassifyRightFragment()
            val bundle = Bundle()
            bundle.putString("name",name)
            bundle.putString("id", id)
            fragment.arguments = bundle
            return fragment
        }
    }

    private var list= ArrayList<Int>()
    //接收分类ID
    private var id:String="110"
    //接收分类名字
    private var classifyname:String=""
    private val rightAdapter by lazy { ClassifyRightAdapter(context,classifyProductData,classifyname) }

    //接收数据
    private val classifyProductData = ArrayList<ClassifyProductBean>()
    private val classifyProductPrediction by lazy { ClassifyProductPresenter() }

    override fun getLayoutId(): Int=R.layout.frament_classify_recommend

    override fun initView() {
        classifyProductPrediction.attachView(this)


        id= arguments!!.getString("id")
        classifyname=arguments!!.getString("name")
//        if(id=="为您推荐"){
//            list.clear()
//            for(i in 1..4){
//                list.add(i)
//            }
//        }else if(id=="美容彩妆"){
//            list.clear()
//            for(i in 1..2){
//                list.add(i)
//            }
//        }
        for(i in 1..4){
                 list.add(i)
            }
        rightRecyclerView.layoutManager=LinearLayoutManager(context)
        rightRecyclerView.adapter=rightAdapter
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

    override fun onStart() {
        super.onStart()

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