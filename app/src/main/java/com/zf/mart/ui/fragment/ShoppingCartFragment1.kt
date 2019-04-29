package com.zf.mart.ui.fragment

import android.app.Activity
import android.view.Gravity
import android.view.View
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.scwang.smartrefresh.layout.util.DensityUtil
import com.zf.mart.R
import com.zf.mart.base.BaseFragment
import com.zf.mart.mvp.bean.*
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
import okhttp3.MediaType
import okhttp3.RequestBody


/**
 * 购物车页面
 */
class ShoppingCartFragment1 : BaseFragment(), CartListContract.View, CartOperateContract.View {

    //根据规格获取商品信息
    override fun setSpecInfo(bean: GoodsSpecInfo) {
        cartData[mShopPos].list[mGoodsPos].goods_price = bean.price
        cartData[mShopPos].list[mGoodsPos].goods.original_img = bean.spec_img
        cartAdapter.notifyDataSetChanged()
    }

    /** 获取商品规格 */
    override fun setGoodsSpec(specBean: List<List<SpecBean>>) {
        /**
         * 把规格的对象扔出来
         * 请求网络更新规格
         * 刷新适配器，单品价格，规格，总体价格
         */
        val specList = ArrayList<SpecCorrect>()
        specBean.forEach {
            if (it.isNotEmpty()) {
                specList.add(SpecCorrect(it[0].name, it, ""))
            }
        }
        val popWindow = object : GroupStylePopupWindow(
                activity as Activity,
                R.layout.pop_order_style,
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT,
                cartData[mShopPos].list[mGoodsPos],
                specList
        ) {}
        popWindow.showAtLocation(parentLayout, Gravity.BOTTOM, 0, 0)
        popWindow.onNumberListener = {
            cartData[mShopPos].list[mGoodsPos].goods_num = it
            cartAdapter.notifyDataSetChanged()
            cartOperatePresenter.requestCount(cartData[mShopPos].list[mGoodsPos].id, it)
        }
        popWindow.onSpecListener = { specId ->
            //规格回调
            LogUtils.e(">>>:$specId")
            //回调后 请求网络1更改规格 请求网络2获取这个规格的商品详情 更改实体类，刷新列表
            //要有商家index和商品index
            /** 价格和图片已经在上面的网络回调修改了，剩下总价和规格中文没修改 */
//            cartData[mShopPos].list[mGoodsPos].spec_key_name = it.item ?: ""
//            cartData[mShopPos].list[mGoodsPos].goods_price = it.price ?: "0.00"
//            cartAdapter.notifyDataSetChanged()
            cartOperatePresenter.requestChangeSpec(cartData[mShopPos].list[mGoodsPos].cat_id, specId)
        }
    }

    /** 修改商品规格 */
    override fun setChangeSpec(bean: CartPrice) {
        price.text = "¥${bean.total_fee}"
    }

    /** 删除购物车 */
    override fun setDeleteCart(bean: CartPrice) {
        price.text = "¥${bean.total_fee}"
        //重组数据
        val shopList = ArrayList<CartBean>()
        for (shop in cartData) {
            if (shop.selected == "0") {
                val goodsList = ArrayList<CartGoodsList>()
                for (goods in shop.list) {
                    if (goods.selected == "0") {
                        goodsList.add(goods)
                    }
                }
                shop.list.clear()
                shop.list.addAll(goodsList)
                shopList.add(shop)
            }
        }
        cartData.clear()
        cartData.addAll(shopList)
        cartAdapter.notifyDataSetChanged()
        if (cartData.isEmpty()) {
            mLayoutStatusView?.showEmpty()
            settleLayout.visibility = View.GONE
        }
    }

    /** 全选状态 */
    override fun setCheckAll(bean: CartPrice) {
        price.text = "¥${bean.total_fee}"
    }

    /** 勾选状态 */
    override fun setSelect(bean: CartPrice) {
        price.text = "¥${bean.total_fee}"
    }

    /** 修改商品数量 */
    override fun setCount(bean: CartPrice) {
        price.text = "¥${bean.total_fee}"
    }

    //购物车操作失败
    override fun cartOperateError(msg: String, errorCode: Int) {
        showToast(msg)
    }


    //购物车为空
    override fun setEmpty() {
        settleLayout.visibility = View.GONE
        mLayoutStatusView?.showEmpty()
        refreshLayout.setEnableLoadMore(false)
    }

    //没有更多数据
    override fun setLoadComplete() {
        refreshLayout.setEnableLoadMore(false)
    }

    //刷新失败
    override fun showError(msg: String, errorCode: Int) {
        settleLayout.visibility = View.GONE
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

    //刷新成功
    override fun setRefreshCart(bean: CartBean) {
        settleLayout.visibility = View.VISIBLE
        mLayoutStatusView?.showContent()
        refreshLayout.setEnableLoadMore(true)
        /** 重组数据 */
        val result = bean.list.groupBy {
            val key = it.seller_name
            if (key == it.seller_name) it.seller_name else it.seller_name
        }
        val shopList = ArrayList<CartBean>()

        result.forEach {
            shopList.add(CartBean(it.value as ArrayList<CartGoodsList>, it.key ?: ""))
        }

        /**
         * 对商家选中状态进行赋值
         */
        shopList.forEach { shop ->
            var size = 0
            shop.list.forEach { goods ->
                if (goods.selected == "1") size += 1
            }
            shop.selected = if (size == shop.list.size) "1" else "0"
        }
        cartData.clear()
        cartData.addAll(shopList)
        cartAdapter.notifyDataSetChanged()

        price.text = "¥${bean.cart_price_info?.total_fee}"
        allChoose.isChecked = 1 == bean.selected_flag?.all_flag
    }

    //加载下一页成功
    override fun setLoadMoreCart(bean: CartBean) {
        /** 重组数据 */
        val result = bean.list.groupBy {
            val key = it.seller_name
            if (key == it.seller_name) it.seller_name else it.seller_name
        }
        val shopList = ArrayList<CartBean>()
        result.forEach {
            shopList.add(CartBean(it.value as ArrayList<CartGoodsList>, it.key ?: ""))
        }

        /**
         * 对商家选中状态进行赋值
         */
        shopList.forEach { shop ->
            var size = 0
            shop.list.forEach { goods ->
                if (goods.selected == "1") size += 1
            }
            shop.selected = if (size == shop.list.size) "1" else "0"
        }

        cartData.addAll(shopList)
        cartAdapter.notifyDataSetChanged()

        price.text = "¥${bean.cart_price_info?.total_fee}"
        allChoose.isChecked = 1 == bean.selected_flag?.all_flag
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


    private var mShopPos = 0
    private var mGoodsPos = 0

    //购物车适配器
    private var cartData = ArrayList<CartBean>()

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

    override fun initEvent() {

        /** 全选反选按钮 */
        allChoose.setOnClickListener {
            cartData.forEach { shopList ->
                shopList.selected = if (allChoose.isChecked) "1" else "0"
                shopList.list.forEach { goodsList ->
                    goodsList.selected = if (allChoose.isChecked) "1" else "0"
                }
            }
            cartAdapter.notifyDataSetChanged()
            cartOperatePresenter.requestCheckAll(if (allChoose.isChecked) 1 else 2)
        }

        /** 选中商品回调 */
        cartAdapter.onGoodsCheckListener = {
            val json = ArrayList<CartCheckBean>()
            var sum = 0
            cartData.forEach { shop ->
                shop.list.forEach {
                    val cartBean = CartCheckBean()
                    cartBean.id = it.id
                    cartBean.selected = it.selected
                    json.add(cartBean)
                }
                if (shop.selected == "1") sum += 1
            }
            allChoose.isChecked = sum == cartData.size
            val body =
                    RequestBody.create(okhttp3.MediaType.parse("application/json;charset=UTF-8"), Gson().toJson(json))
            cartOperatePresenter.requestSelect(body)
        }

        refreshLayout.setOnRefreshListener {
            lazyLoad()
        }

        refreshLayout.setOnLoadMoreListener {
            cartListPresenter.requestCartList(null)
        }

        /** 更改商品数量加减*/
        cartAdapter.onGoodsCount = {
            cartOperatePresenter.requestCount(it.id, it.sum)
        }

        /** 商品数量*/
        cartAdapter.onShopNumListener = { bean ->
            InputNumDialog.showDialog(childFragmentManager, bean.sum)
                    .onNumListener = { num ->
                cartOperatePresenter.requestCount(bean.id, num)
                bean.shopPosition?.let { shopPos ->
                    bean.goodsPosition?.let { goodsPos ->
                        cartData[shopPos].list.let {
                            it[goodsPos].goods_num = num
                        }

                    }
                }
                cartAdapter.notifyDataSetChanged()
            }
        }

        /** 商品规格 */
        cartAdapter.onShopSpecListener = { shopPos, goodsPos ->
            mShopPos = shopPos
            mGoodsPos = goodsPos
            cartOperatePresenter.requestGoodsSpec(cartData[shopPos].list[goodsPos].goods.goods_id)
        }

        settle.setOnClickListener { _ ->
            //获取选中的id
            if (management.isSelected) {
                /**
                 *  删除
                 *  如果未勾选到商品，不能结算
                 */
                var sum = 0
                cartData.forEach {
                    if (it.selected == "1") sum += 1
                }
                if (sum < 1) {
                    showToast("请先选择商品")
                    return@setOnClickListener
                }
                DeleteCartDialog.showDialog(childFragmentManager, 1)
                        .onConfirmListener = {
                    val deleteList = ArrayList<HashMap<String, String>>()
                    cartData.forEach { shop ->
                        shop.list.forEach { goods ->
                            if (goods.selected == "1") {
                                val map = HashMap<String, String>()
                                map["id"] = goods.id
                                deleteList.add(map)
                            }
                        }
                    }
                    val body =
                            RequestBody.create(MediaType.parse("application/json;charset=UTF-8"), Gson().toJson(deleteList))
                    cartOperatePresenter.requestDeleteCart(body)
                }

            } else {
                /**
                 * 结算
                 * 如果未勾选到商品，不能结算
                 */
                var sum = 0
                cartData.forEach {
                    if (it.selected == "1") sum += 1
                }
                if (sum > 0) {
                    ConfirmOrderActivity.actionStart(context)
                } else {
                    showToast("请先选择商品")
                }
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