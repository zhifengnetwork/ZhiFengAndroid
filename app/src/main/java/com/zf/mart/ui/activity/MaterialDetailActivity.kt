package com.zf.mart.ui.activity

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.View
import com.zf.mart.R
import com.zf.mart.base.BaseActivity
import com.zf.mart.mvp.bean.MaterialDetailBean
import com.zf.mart.mvp.contract.MaterialDetailContract
import com.zf.mart.mvp.presenter.MaterialDetailPresnter
import com.zf.mart.showToast
import com.zf.mart.utils.TimeUtils
import com.zzhoujay.richtext.RichText
import kotlinx.android.synthetic.main.activity_material_detail.*
import kotlinx.android.synthetic.main.layout_toolbar.*

class MaterialDetailActivity : BaseActivity(), MaterialDetailContract.View {
    override fun showError(msg: String, errorCode: Int) {
        showToast(msg)
    }

    override fun getMaterialDetail(bean: MaterialDetailBean) {
        loadData(bean)
    }

    override fun showLoading() {

    }

    override fun dismissLoading() {

    }

    private var mId = ""

    companion object {
        fun actionStart(context: Context?, id: String) {
            val intent = Intent(context, MaterialDetailActivity::class.java)
            intent.putExtra("id", id)
            context?.startActivity(intent)
        }
    }

    override fun initToolBar() {
        back.setOnClickListener { finish() }
        titleName.text = "素材详情"
        rightLayout.visibility = View.INVISIBLE
    }

    override fun layoutId(): Int = R.layout.activity_material_detail

    private val presenter by lazy { MaterialDetailPresnter() }

    override fun initData() {
        mId = intent.getStringExtra("id")
    }

    override fun initView() {
        presenter.attachView(this)

        RichText.initCacheDir(this)

    }

    override fun initEvent() {

    }

    override fun onDestroy() {
        super.onDestroy()
        RichText.clear(this)
    }

    override fun start() {
        presenter.requestMaterialDetail(mId)
    }

    @SuppressLint("SetTextI18n")
    fun loadData(data: MaterialDetailBean) {

        titles.text = data.info.title

        add_time.text = "时间:" + TimeUtils.myOrderTime(data.info.add_time)

        click.text = "浏览量:" + data.info.click

        RichText.from(data.content).into(content)
    }
}