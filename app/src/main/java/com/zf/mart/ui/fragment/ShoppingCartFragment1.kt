package com.zf.mart.ui.fragment

import android.app.Activity
import android.view.Gravity
import android.view.View
import android.widget.LinearLayout
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.scwang.smartrefresh.layout.util.DensityUtil
import com.zf.mart.R
import com.zf.mart.base.BaseFragment
import com.zf.mart.mvp.bean.ShopList
import com.zf.mart.mvp.contract.CartListContract
import com.zf.mart.mvp.contract.CartOperateContract
import com.zf.mart.mvp.presenter.CartListPresenter
import com.zf.mart.mvp.presenter.CartOperatePresenter
import com.zf.mart.net.exception.ErrorStatus
import com.zf.mart.showToast
import com.zf.mart.ui.activity.ConfirmOrderActivity
import com.zf.mart.ui.adapter.CartShopAdapter1
import com.zf.mart.utils.LogUtils
import com.zf.mart.view.dialog.DeleteCartDialog
import com.zf.mart.view.dialog.InputNumDialog
import com.zf.mart.view.popwindow.GroupStylePopupWindow
import com.zf.mart.view.recyclerview.RecyclerViewDivider
import kotlinx.android.synthetic.main.fragment_shoping_cart.*

/**
 * 购物车页面
 */
class ShoppingCartFragment1 : BaseFragment(), CartListContract.View, CartOperateContract.View {

    //加减成功
    override fun setCount() {
        LogUtils.e(">>>>success")
        //刷新列表
        lazyLoad()
    }

    //加减失败
    override fun cartOperateError(msg: String, errorCode: Int) {
        showToast(msg)
    }

    //购物车为空
    override fun setEmpty() {
        mLayoutStatusView?.showEmpty()
        refreshLayout.setEnableLoadMore(false)
    }

    //没有更多数据
    override fun setLoadComplete() {
        refreshLayout.setEnableLoadMore(false)
    }

    //刷新成功
    override fun setRefreshCart(bean: List<ShopList>) {
        mLayoutStatusView?.showContent()
        refreshLayout.setEnableLoadMore(true)
        cartData.clear()
        cartData.addAll(bean)
        cartAdapter.notifyDataSetChanged()
    }

    //加载下一页成功
    override fun setLoadMoreCart(bean: List<ShopList>) {
        cartData.addAll(bean)
        cartAdapter.notifyDataSetChanged()
    }

    //刷新失败
    override fun showError(msg: String, errorCode: Int) {
        if (errorCode == ErrorStatus.NETWORK_ERROR) {
            mLayoutStatusView?.showNoNetwork()
        } else {
            mLayoutStatusView?.showError()
        }
    }

    //加载下一页失败
    override fun loadMoreError(msg: String, errorCode: Int) {
        showToast(msg)
    }

    override fun showLoading() {
    }

    override fun dismissLoading() {
        refreshLayout.finishRefresh()
        refreshLayout.finishLoadMore()
    }

    companion object {
        fun getInstance(): ShoppingCartFragment1 {
            return ShoppingCartFragment1()
        }
    }

    override fun getLayoutId(): Int = R.layout.fragment_shoping_cart

    //购物车适配器
    private var cartData = ArrayList<ShopList>()
    private val cartAdapter by lazy { CartShopAdapter1(context, cartData) }
    private val cartListPresenter by lazy { CartListPresenter() }
    private val cartOperatePresenter by lazy { CartOperatePresenter() }


    private fun initCart() {
        cartRecyclerView.layoutManager = LinearLayoutManager(context)
        cartRecyclerView.adapter = cartAdapter
        cartRecyclerView.addItemDecoration(
            RecyclerViewDivider(
                context,
                LinearLayoutManager.VERTICAL,
                DensityUtil.dp2px(12f),
                ContextCompat.getColor(context!!, R.color.colorBackground)
            )
        )
    }

    override fun lazyLoad() {
        if (cartData.isEmpty()) {
            mLayoutStatusView?.showLoading()
        }
        refreshLayout.setEnableLoadMore(false)
        cartListPresenter.requestCartList(1)
    }

    override fun initView() {
        cartOperatePresenter.attachView(this)
        cartListPresenter.attachView(this)
        mLayoutStatusView = multipleStatusView
        //购物车
        initCart()
    }


    /** 选中的id列表 */
    private var mChooseIdList = ArrayList<String>()

    private fun initCheckId(list: List<ShopList>) {
        val chooseGoodsListId = ArrayList<String>()
        list.forEach { shop ->
            shop.data.forEach { goodsList ->
                if (goodsList.ifCheck) {
                    chooseGoodsListId.add(goodsList.id)
                }
            }
        }
        mChooseIdList = chooseGoodsListId
    }

    override fun initEvent() {

        refreshLayout.setOnRefreshListener {
            lazyLoad()
        }

        refreshLayout.setOnLoadMoreListener {
            cartListPresenter.requestCartList(null)
        }

        /** 更改商品数量加减*/
        cartAdapter.onGoodsCount = {
            cartOperatePresenter.requestCount(it.cartId, it.sum)
        }


        /** 商品数量*/
        cartAdapter.onShopNumListener = { bean ->
            InputNumDialog.showDialog(childFragmentManager, bean.sum)
                .onNumListener = { num ->
                cartOperatePresenter.requestCount(bean.cartId, num)
            }
        }

        /** 商品规格 */
        cartAdapter.onShopSpecListener = {
            val popWindow = object : GroupStylePopupWindow(
                activity as Activity,
                R.layout.pop_order_style,
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            ) {}
            popWindow.showAtLocation(parentLayout, Gravity.BOTTOM, 0, 0)
        }

        /** 全选反选按钮 */
        allChoose.setOnClickListener {
            //循环赋值
            cartData.forEach { shopList ->
                shopList.ifCheck = allChoose.isChecked
                shopList.data.forEach { goodsList ->
                    goodsList.ifCheck = allChoose.isChecked
                }
            }
            //赋值之后刷新列表 是否全选
            cartAdapter.notifyDataSetChanged()

            /** 选中或者反选需要获取结果 */
            initCheckId(cartData)
        }

        cartAdapter.checkGoodsListener = { shopList ->
            /** 返回来全部的列表，通过遍历判断是否选中 */
            /** 得到选择的id */
            var size = 0
            shopList.forEach { shop ->
                if (shop.ifCheck) size += 1
            }
            allChoose.isChecked = size == cartData.size

            initCheckId(shopList)
        }

        settle.setOnClickListener {
            //获取选中的id
            Toast.makeText(context, "选中id:$mChooseIdList", Toast.LENGTH_LONG).show()
            if (management.isSelected) {
                //删除
                DeleteCartDialog.showDialog(childFragmentManager, 1)
            } else {
                //结算
                ConfirmOrderActivity.actionStart(context)
            }
        }

        //管理
        management.setOnClickListener {
            management.isSelected = !management.isSelected
            if (management.isSelected) {
                settle.text = "删除"
                management.text = "完成"
                price.visibility = View.INVISIBLE
                totalTxt.visibility = View.INVISIBLE
            } else {
                settle.text = "结算"
                management.text = "管理"
                price.visibility = View.VISIBLE
                totalTxt.visibility = View.VISIBLE
            }

        }

    }

    override fun onDestroy() {
        super.onDestroy()
        cartListPresenter.detachView()
        cartOperatePresenter.detachView()
    }


    //支付方式
//    private fun showPayPopWindow() {
//        val window = object : OrderPayPopupWindow(
//            activity as Activity, R.layout.pop_order_pay,
//            LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT
//        ) {}
//
//        window.showAtLocation(parentLayout, Gravity.BOTTOM, 0, 0)
//
//    }

    //新增地址
//    private fun showAddressPopupWindow() {
//        val window = object : AddAddressPopupWindow(
//            activity as Activity, R.layout.pop_push_address,
//            LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT
//        ) {}
//        window.setOnItemClickListener(object : AddAddressPopupWindow.OnItemClickListener {
//            override fun onBack(address: String) {
//                showOrderPopWindow(address)
//            }
//        })
//        window.showAtLocation(parentLayout, Gravity.BOTTOM, 0, 0)
//
//    }

    //提交订单
//    private fun showOrderPopWindow(txt: String) {
//
//        val window = object : ConfirmOrderPopupWindow(
//            activity as Activity, R.layout.pop_push_order,
//            LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, txt
//        ) {}
//        window.setOnItemClickListener(object : ConfirmOrderPopupWindow.OnItemClickListener {
//            override fun showPayPop() {
//                showPayPopWindow()
//            }
//
//            override fun showAddressPop() {
//                showAddressPopupWindow()
//            }
//        })
//        window.showAtLocation(parentLayout, Gravity.BOTTOM, 0, 0)
//
//    }
}