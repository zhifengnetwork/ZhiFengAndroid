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
import com.zf.mart.mvp.bean.CartGoodsList
import com.zf.mart.mvp.bean.ShopList
import com.zf.mart.ui.activity.ConfirmOrderActivity
import com.zf.mart.ui.adapter.CartShopAdapter1
import com.zf.mart.utils.LogUtils
import com.zf.mart.view.dialog.InputNumDialog
import com.zf.mart.view.popwindow.GroupStylePopupWindow
import com.zf.mart.view.recyclerview.RecyclerViewDivider
import kotlinx.android.synthetic.main.fragment_shoping_cart.*

/**
 * 购物车页面
 */
class ShoppingCartFragment1 : BaseFragment() {

    companion object {
        fun getInstance(): ShoppingCartFragment1 {
            return ShoppingCartFragment1()
        }
    }

    override fun getLayoutId(): Int = R.layout.fragment_shoping_cart

    //购物车适配器
    private var cartData = ArrayList<ShopList>()
    private val cartAdapter by lazy { CartShopAdapter1(context, cartData) }

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


    override fun initView() {

        //购物车
        initCart()

        initRefresh()
    }

    private fun initRefresh() {

        /** 购物车有商品 */
        cartData.clear()
        cartData.addAll(getCartData())
        cartAdapter.notifyDataSetChanged()
        refreshLayout.finishRefresh()
    }

    /** 选中的id列表 */
    private var mChooseIdList = ArrayList<Int>()

    private fun initCheckId(list: List<ShopList>) {
        val chooseGoodsListId = ArrayList<Int>()
        list.forEach { shop ->
            shop.goodsList.forEach { goodsList ->
                if (goodsList.ifCheck) {
                    chooseGoodsListId.add(goodsList.id)
                }
            }
        }
        mChooseIdList = chooseGoodsListId
    }

    override fun initEvent() {

        cartAdapter.onShopNumListener = {
            InputNumDialog.showDialog(childFragmentManager, it)
                .onNumListener = { num ->
                LogUtils.e(">>>>>:$num")
            }
        }

        cartAdapter.onShopSpecListener = {
            val popWindow = object : GroupStylePopupWindow(
                activity as Activity,
                R.layout.pop_order_style,
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            ) {}
            popWindow.showAtLocation(parentLayout, Gravity.BOTTOM, 0, 0)
        }


        allChoose.setOnClickListener {
            //循环赋值
            cartData.forEach { shopList ->
                shopList.ifCheck = allChoose.isChecked
                shopList.goodsList.forEach { goodsList ->
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
            } else {
                //结算
                ConfirmOrderActivity.actionStart(context)
            }
        }

        refreshLayout.setOnRefreshListener {
            initRefresh()
        }

        refreshLayout.setOnLoadMoreListener {
            refreshLayout.finishLoadMore()
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

    private fun getCartData(): ArrayList<ShopList> {
        val list = ArrayList<ShopList>()
        list.addAll(
            arrayListOf(
                ShopList(
                    "小米旗舰店", arrayListOf(
                        CartGoodsList(1, "小米Note"),
                        CartGoodsList(2, "小米5"),
                        CartGoodsList(3, "小米8"),
                        CartGoodsList(4, "小米8se")
                    )
                ),
                ShopList(
                    "华为旗舰店", arrayListOf(
                        CartGoodsList(5, "华为mate"),
                        CartGoodsList(6, "华为荣耀")
                    )
                ),
                ShopList(
                    "格力空调", arrayListOf(
                        CartGoodsList(7, "1匹"),
                        CartGoodsList(8, "2匹"),
                        CartGoodsList(9, "3匹")
                    )
                ),
                ShopList(
                    "oppo手机", arrayListOf(
                        CartGoodsList(10, "oppo r1"),
                        CartGoodsList(11, "oppo r2"),
                        CartGoodsList(12, "oppo r3")
                    )
                ),
                ShopList(
                    "戴尔", arrayListOf(
                        CartGoodsList(13, "笔记本"),
                        CartGoodsList(14, "台式机"),
                        CartGoodsList(15, "屏幕")
                    )
                ),
                ShopList(
                    "索尼", arrayListOf(
                        CartGoodsList(16, "索尼手机"),
                        CartGoodsList(17, "索尼相机"),
                        CartGoodsList(18, "索尼其他东西")
                    )
                )


            )
        )
        return list
    }

    override fun lazyLoad() {
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