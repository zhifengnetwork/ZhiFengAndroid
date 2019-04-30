package com.zf.mart.ui.activity

import android.content.Context
import android.content.Intent
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.zf.mart.R
import com.zf.mart.base.BaseActivity
import com.zf.mart.mvp.bean.OrderListBean
import com.zf.mart.mvp.contract.EvaluateContract
import com.zf.mart.mvp.presenter.EvaluatePresenter
import com.zf.mart.ui.adapter.EvaluateAdapter
import com.zf.mart.utils.LogUtils
import kotlinx.android.synthetic.main.activity_evaluate.*
import kotlinx.android.synthetic.main.layout_toolbar.*

/**
 * 评价订单
 */
class EvaluateActivity : BaseActivity(), EvaluateContract.View {
    override fun setEvaluate() {

    }

    override fun showLoading() {
    }

    override fun dismissLoading() {
    }

    override fun showError(msg: String, errorCode: Int) {
    }

    override fun initToolBar() {
        back.setOnClickListener { finish() }
        titleName.text = "评价晒单"
        rightLayout.visibility = View.INVISIBLE
    }

    companion object {
        fun actionStart(context: Context?, orderBean: OrderListBean) {
            val intent = Intent(context, EvaluateActivity::class.java)
            intent.putExtra("orderBean", orderBean)
            context?.startActivity(intent)
        }
    }

    override fun layoutId(): Int = R.layout.activity_evaluate

    private var mOrderBean: OrderListBean? = null

    override fun initData() {
        mOrderBean = intent.getSerializableExtra("orderBean") as OrderListBean
    }

    private val adapter by lazy { EvaluateAdapter(this, mOrderBean?.goods) }

    override fun initView() {
        presenter.attachView(this)
        goodsRecyclerView.layoutManager = LinearLayoutManager(this)
        goodsRecyclerView.adapter = adapter

    }

    private val presenter by lazy { EvaluatePresenter() }


    override fun initEvent() {

        confirm.setOnClickListener {
            val list = ArrayList<HashMap<String, Any>>()
            adapter.data?.forEach {
                val map = HashMap<String, Any>()
                map["id"] = it.goods_id
                map["content"] = it.evaluateContent
                map["img"] = it.imgList
                map["deliver_rank"] = it.deliverRank
                map["goods_rank"] = it.goodsRank
                map["service_rank"] = it.serviceRank
                map["is_anonymous"] = it.is_anonymous
                list.add(map)
            }
            //转成json字符串给后台
            presenter.requestEvaluate(Gson().toJson(list))

            //            val finalData = ArrayList<Map<String, String>>()
//            val list = HashMap<String, String>()
//            list["id"] = "12"
//            list["content"] = "tom"
//            finalData.add(list)
//            list["id"] = "14"
//            list["content"] = "cat"
//            finalData.add(list)
//            val gson = Gson().toJson(finalData)

        }

    }


    override fun start() {
    }

    override fun onDestroy() {
        presenter.detachView()
        super.onDestroy()

    }
}