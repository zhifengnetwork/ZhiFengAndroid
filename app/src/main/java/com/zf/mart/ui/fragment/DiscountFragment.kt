package com.zf.mart.ui.fragment

import androidx.recyclerview.widget.LinearLayoutManager
import com.zf.mart.R
import com.zf.mart.base.BaseFragment
import com.zf.mart.mvp.bean.DiscountBean
import com.zf.mart.mvp.contract.DiscountContract
import com.zf.mart.mvp.presenter.DiscountPresenter
import com.zf.mart.net.exception.ErrorStatus
import com.zf.mart.showToast
import com.zf.mart.ui.adapter.DiscountAdapter
import com.zf.mart.view.dialog.DiscountDialog
import kotlinx.android.synthetic.main.fragment_discount.*

/**
 * 优惠券中心
 */
class DiscountFragment : BaseFragment(), DiscountContract.View {

    override fun setEmpty() {
        mLayoutStatusView?.showEmpty()
    }

    override fun showError(msg: String, errorCode: Int) {
        if (errorCode == ErrorStatus.NETWORK_ERROR) {
            mLayoutStatusView?.showNoNetwork()
        } else {
            mLayoutStatusView?.showError()
            showToast(msg)
        }
    }

    override fun setDiscount(bean: List<DiscountBean>) {
        mLayoutStatusView?.showContent()
        data.clear()
        data.addAll(bean)
        adapter.notifyDataSetChanged()
    }

    override fun showLoading() {
    }

    override fun dismissLoading() {
    }

    private var type = ""

    companion object {

        const val unUse = "unUse"
        const val haveUse = "haveUse"
        const val timeOut = "timeOut"

        fun newInstance(type: String): DiscountFragment {
            val fragment = DiscountFragment()
            fragment.type = type
            return fragment
        }
    }

    override fun getLayoutId(): Int = R.layout.fragment_discount

    private val data = ArrayList<DiscountBean>()
    private val adapter by lazy { DiscountAdapter(context, type, data) }

    private val presenter by lazy { DiscountPresenter() }

    override fun initView() {
        mLayoutStatusView = multipleStatusView
        presenter.attachView(this)
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = adapter

    }


    override fun initEvent() {
        adapter.onClickListener={
            DiscountDialog.showDialog(childFragmentManager,it)
        }

    }

    override fun lazyLoad() {
        if (data.isEmpty()) {
            mLayoutStatusView?.showLoading()
        }
        presenter.requestDiscount(
                when (type) {
                    unUse -> "0"
                    haveUse -> "1"
                    timeOut -> "2"
                    else -> ""
                }
        )
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.detachView()
    }

}