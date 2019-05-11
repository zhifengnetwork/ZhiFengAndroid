package com.zf.mart.ui.fragment

import androidx.recyclerview.widget.LinearLayoutManager
import com.zf.mart.R
import com.zf.mart.base.BaseFragment
import com.zf.mart.mvp.bean.MaterialList
import com.zf.mart.mvp.contract.MaterialListContract
import com.zf.mart.mvp.presenter.MaterialListPresenter
import com.zf.mart.showToast
import com.zf.mart.ui.activity.MaterialDetailActivity
import com.zf.mart.ui.adapter.MaterialAdapter
import kotlinx.android.synthetic.main.fragment_material.*

/**
 * 素材区
 * */
class MaterialFragment : BaseFragment(), MaterialListContract.View {
    override fun showError(msg: String, errorCode: Int) {
        refreshLayout.setEnableLoadMore(false)
    }

    override fun getMaterialList(bean: List<MaterialList>) {
        refreshLayout.setEnableLoadMore(true)
        mData.clear()
        mData.addAll(bean)
        mAdapter.notifyDataSetChanged()
    }

    override fun freshEmpty() {
        refreshLayout.setEnableLoadMore(false)
    }

    override fun setLoadMore(bean: List<MaterialList>) {
        mData.addAll(bean)
        mAdapter.notifyDataSetChanged()
    }

    override fun setLoadComplete() {
        refreshLayout.finishLoadMoreWithNoMoreData()
    }

    override fun loadMoreError(msg: String, errorCode: Int) {
        showToast(msg)
    }

    override fun showLoading() {

    }

    override fun dismissLoading() {
        refreshLayout.finishRefresh()
        refreshLayout.finishLoadMore()
    }

    private var mId = ""

    companion object {
        fun buildFragment(id: String): MaterialFragment {
            val fragment = MaterialFragment()
            fragment.mId = id
            return fragment
        }
    }

    override fun getLayoutId(): Int = R.layout.fragment_material

    private val presenter by lazy { MaterialListPresenter() }

    private var mData = ArrayList<MaterialList>()

    private val mAdapter by lazy { MaterialAdapter(context, mData) }
    override fun initView() {
        presenter.attachView(this)

        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = mAdapter

    }

    override fun lazyLoad() {
        refreshLayout.setEnableLoadMore(false)
        presenter.requestMaterialList(mId, 1)
    }

    override fun initEvent() {
        /**上拉加载*/
        refreshLayout.setOnLoadMoreListener {
            presenter.requestMaterialList(mId, null)
        }
        /**下拉刷新*/
        refreshLayout.setOnRefreshListener {
            lazyLoad()

        }
        mAdapter.mClickListener = {
            MaterialDetailActivity.actionStart(context, it)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.detachView()
    }
}