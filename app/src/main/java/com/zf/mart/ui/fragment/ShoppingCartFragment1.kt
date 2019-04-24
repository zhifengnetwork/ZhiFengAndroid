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
import com.zf.mart.mvp.bean.CartBean
import com.zf.mart.mvp.bean.CartCheckBean
import com.zf.mart.mvp.bean.CartGoodsList
import com.zf.mart.mvp.bean.CartPrice
import com.zf.mart.mvp.contract.CartListContract
import com.zf.mart.mvp.contract.CartOperateContract
import com.zf.mart.mvp.presenter.CartListPresenter
import com.zf.mart.mvp.presenter.CartOperatePresenter
import com.zf.mart.net.exception.ErrorStatus
import com.zf.mart.showToast
import com.zf.mart.ui.activity.ConfirmOrderActivity
import com.zf.mart.ui.adapter.CartShopAdapter1
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
        mLayoutStatusView?.showEmpty()
        refreshLayout.setEnableLoadMore(false)
    }

    //没有更多数据
    override fun setLoadComplete() {
        refreshLayout.setEnableLoadMore(false)
    }

    private var cartData = ArrayList<CartBean>()

    //刷新成功
    override fun setRefreshCart(bean: CartBean) {
        mLayoutStatusView?.showContent()
        refreshLayout.setEnableLoadMore(true)
        /** 重组数据 */
        val result = bean.list.groupBy {
            val key = it.seller_name
            if (key == it.seller_name) it.seller_name else it.seller_name
        }
        val shopList = ArrayList<CartBean>()
        result.forEach {
            shopList.add(CartBean(it.value as ArrayList<CartGoodsList>, it.key))
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
            shopList.add(CartBean(it.value as ArrayList<CartGoodsList>, it.key))
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

    override fun getLayoutId(): Int = com.zf.mart.R.layout.fragment_shoping_cart

    //购物车适配器

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
            cartOperatePresenter.requestCount(it.cartId, it.sum)
        }

        /** 商品数量*/
        cartAdapter.onShopNumListener = { bean ->
            InputNumDialog.showDialog(childFragmentManager, bean.sum)
                .onNumListener = { num ->
                cartOperatePresenter.requestCount(bean.cartId, num)
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
        cartAdapter.onShopSpecListener = {
            val popWindow = object : GroupStylePopupWindow(
                activity as Activity,
                R.layout.pop_order_style,
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            ) {}
            popWindow.showAtLocation(parentLayout, Gravity.BOTTOM, 0, 0)
        }

        settle.setOnClickListener { _ ->
            //获取选中的id
            if (management.isSelected) {
                DeleteCartDialog.showDialog(childFragmentManager, 1)
                    .onConfirmListener = {
                    /**
                     *  删除
                     */
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
                 */
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


    /** 手动添加多条数据 */
    //        cartData.addAll(
//
//            arrayListOf(
//                ShopList(
//                    "小米",
//                    arrayListOf(
//                        CartGoodsList(
//                            "1", "12", "5",
//                            Goods("mi2", "1", "34", "12"),
//                            "231", "0"
//                        )
//                    ),
//                    ""
//                ),
//                ShopList(
//                    "hua wei",
//                    arrayListOf(
//                        CartGoodsList(
//                            "1", "12", "5",
//                            Goods("hw 3", "1", "34", "12"),
//                            "232", "1"
//                        ),
//                        CartGoodsList(
//                            "1", "12", "5",
//                            Goods("hw4", "1", "34", "12"),
//                            "232", "1"
//                        )
//                    ),
//                    ""
//                ),
//                ShopList(
//                    "vi vo",
//                    arrayListOf(
//                        CartGoodsList(
//                            "1", "12", "5",
//                            Goods("vivo 19", "1", "34", "12"),
//                            "232", "0"
//                        ),
//                        CartGoodsList(
//                            "1", "12", "5",
//                            Goods("vivo 20", "1", "34", "12"),
//                            "232", "1"
//                        )
//                    ),
//                    ""
//                )
//            )
//        )

}