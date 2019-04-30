package com.zf.mart.ui.fragment.focus

import android.app.Activity
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import android.widget.RadioButton
import android.widget.Space
import androidx.core.content.ContextCompat
import androidx.core.view.marginRight
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.scwang.smartrefresh.layout.util.DensityUtil
import com.zf.mart.R
import com.zf.mart.base.BaseFragment
import com.zf.mart.mvp.bean.MyFollowBean
import com.zf.mart.mvp.bean.MyFollowList
import com.zf.mart.mvp.contract.MyFollowContract
import com.zf.mart.mvp.presenter.MyFollowPresenter
import com.zf.mart.net.exception.ErrorStatus
import com.zf.mart.showToast
import com.zf.mart.ui.adapter.FocusGoodsAdapter
import com.zf.mart.ui.adapter.FocusLoveGoodsAdapter
import com.zf.mart.utils.LogUtils
import com.zf.mart.view.LayoutGravity
import com.zf.mart.view.RecDecoration
import com.zf.mart.view.popwindow.FocusClassifyPopupWindow
import com.zf.mart.view.recyclerview.RecyclerViewDivider
import com.zf.mart.view.recyclerview.SwipeItemLayout
import kotlinx.android.synthetic.main.fragment_focus_goods.*

class FocusGoodsFragment : BaseFragment(), MyFollowContract.View {


    override fun showError(msg: String, errorCode: Int) {
        refreshLayout.setEnableLoadMore(false)
        showToast(msg)
    }

    //商品关注列表(第一页)
    override fun getMyFollowSuccess(bean: MyFollowBean) {
        mData.clear()
        mData.addAll(bean.list)
        goods_sum.text = bean.count
        goodsAdapter.notifyDataSetChanged()
        love_goods_ly.visibility = View.GONE
    }

    //获得之后页数据
    override fun setLoadMore(bean: List<MyFollowList>) {
        mData.addAll(bean)
        goodsAdapter.notifyDataSetChanged()
    }

    //当第一页数据为空时
    override fun freshEmpty() {
//        mLayoutStatusView?.showEmpty()
//        refreshLayout.setEnableLoadMore(false)
        goods_sum.text = "0"
        mData.clear()
        goodsAdapter.notifyDataSetChanged()
    }

    //实际获得数据小于一页最大数据时 加载完成
    override fun setLoadComplete() {
        refreshLayout.finishLoadMoreWithNoMoreData()
        //显示推荐商品布局
        love_goods_ly.visibility = View.VISIBLE
    }

    //下拉加载错误
    override fun loadMoreError(msg: String, errorCode: Int) {
        showToast(msg)
    }

    override fun setBuyError(msg: String) {

    }

    //删除关注商品
    override fun delCollectGoods(msg: String) {
        showToast(msg)
        refreshLayout.setNoMoreData(false)
        lazyLoad()
        love_goods_ly.visibility = View.GONE

    }

    override fun showLoading() {

    }

    override fun dismissLoading() {
        refreshLayout.finishRefresh()
        refreshLayout.finishLoadMore()
    }

    companion object {
        fun newInstance(): FocusGoodsFragment {
            return FocusGoodsFragment()
        }
    }

    override fun getLayoutId(): Int = R.layout.fragment_focus_goods

    //已关注的商品列表
    private val goodsAdapter by lazy { FocusGoodsAdapter(context, mData) }

    //猜你喜欢的商品列表
    private val loveAdapter by lazy { FocusLoveGoodsAdapter(context) }

    private val presenter by lazy { MyFollowPresenter() }
    //接收网络数据值
    private var mData = ArrayList<MyFollowList>()

    override fun initView() {
        presenter.attachView(this)
        //已关注商品的列表
        goodsRecyclerView.layoutManager = LinearLayoutManager(context)
        goodsRecyclerView.addOnItemTouchListener(SwipeItemLayout.OnSwipeItemTouchListener(context))
        goodsRecyclerView.adapter = goodsAdapter


        //猜你喜欢的商品
        val manager = GridLayoutManager(context, 2)
        loveGoodsRecyclerView.layoutManager = manager
        loveGoodsRecyclerView.adapter = loveAdapter
        loveGoodsRecyclerView.addItemDecoration(RecDecoration(DensityUtil.dp2px(12f)))


        /** 动态添加筛选按钮 */
        repeat(8) {
            val radioButton = layoutInflater.inflate(R.layout.radiobutton_tag, null) as RadioButton
            radioButton.text = "有券$it"
            radioButton.tag = it
            radioGroup.addView(radioButton)
            val spaceView = Space(context)
            val lp = LinearLayout.LayoutParams(DensityUtil.dp2px(18f), 1)
            spaceView.layoutParams = lp
            radioGroup.addView(spaceView)
        }


    }

    override fun lazyLoad() {
//        refreshLayout.setEnableLoadMore(false)
        presenter.requestMyFollow(1, 6)
    }

    override fun initEvent() {
        //分类
        classify.setOnClickListener {
            val popWindow = object : FocusClassifyPopupWindow(
                activity as Activity, R.layout.pop_focus_classify,
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            ) {}
            popWindow.showBashOfAnchor(classifyLayout, LayoutGravity(LayoutGravity.ALIGN_RIGHT), 0, 0)

        }
        /** 筛选按钮点击事件 */
        radioGroup.setOnCheckedChangeListener { group, checkedId ->
            val radioBtn = group.findViewById<RadioButton>(checkedId)
            LogUtils.e("tag:" + radioBtn.tag)
        }
        //删除商品点击事件
        goodsAdapter.mClickListener = {
            presenter.requestDelCollectGoods(it)
        }
        /**上拉加载*/
        refreshLayout.setOnLoadMoreListener {

            presenter.requestMyFollow(null, 6)
        }
        /**下拉刷新*/
        refreshLayout.setOnRefreshListener {
            lazyLoad()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.detachView()
    }
}