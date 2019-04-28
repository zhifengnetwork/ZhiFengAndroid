package com.zf.mart.ui.activity

import android.content.Context
import android.content.Intent
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.zf.mart.R
import com.zf.mart.base.BaseActivity
import com.zf.mart.mvp.bean.MyMemberBean
import com.zf.mart.mvp.contract.MyMemberContract
import com.zf.mart.mvp.presenter.MyMemberPresenter
import com.zf.mart.ui.adapter.MyMemberAdapter
import kotlinx.android.synthetic.main.activity_my_member.*
import kotlinx.android.synthetic.main.layout_toolbar.*

/**
 * 我的会员
 */
class MyMemberActivity : BaseActivity(), MyMemberContract.View {
    override fun showError(msg: String, errorCode: Int) {

    }

    override fun getMyMember(bean: List<MyMemberBean>) {
        mData.addAll(bean)
        mAdapter.notifyDataSetChanged()
    }

    override fun showLoading() {

    }

    override fun dismissLoading() {

    }

    companion object {
        fun actionStart(context: Context?) {
            context?.startActivity(Intent(context, MyMemberActivity::class.java))
        }
    }

    override fun initToolBar() {
        back.setOnClickListener {
            finish()
        }
        titleName.text = "我的会员"
        rightLayout.visibility = View.INVISIBLE
    }

    override fun layoutId(): Int = R.layout.activity_my_member

    private val mAdapter by lazy { MyMemberAdapter(this,mData) }

    //网络接收数据
    private var mData = ArrayList<MyMemberBean>()

    private val presenter by lazy { MyMemberPresenter() }

    override fun initData() {

    }

    override fun initView() {
        presenter.attachView(this)

        member_rl.layoutManager = LinearLayoutManager(this)
        member_rl.adapter = mAdapter
    }

    override fun initEvent() {

    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.detachView()
    }

    override fun start() {
        presenter.requestMyMember()

    }

}