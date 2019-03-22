package com.zf.mart.ui.fragment

import android.app.Activity
import android.view.Gravity
import android.view.View
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.scwang.smartrefresh.layout.util.DensityUtil
import com.zf.mart.R
import com.zf.mart.base.BaseFragment
import com.zf.mart.mvp.bean.CartGoodsList
import com.zf.mart.mvp.bean.ShopList
import com.zf.mart.showToast
import com.zf.mart.ui.activity.ConfirmOrderActivity
import com.zf.mart.ui.adapter.CartShopAdapter
import com.zf.mart.view.dialog.InputNumDialog
import com.zf.mart.view.popwindow.GroupStylePopupWindow
import com.zf.mart.view.recyclerview.RecyclerViewDivider
import kotlinx.android.synthetic.main.fragment_shoping_cart.*

/**
 * 购物车页面
 */
class ShoppingCartFragment : BaseFragment() {


    companion object {
        fun getInstance(): ShoppingCartFragment {
            return ShoppingCartFragment()
        }
    }

    override fun getLayoutId(): Int = R.layout.fragment_shoping_cart

    //购物车适配器
    private var cartData = ArrayList<ShopList>()
    private val cartAdapter by lazy { CartShopAdapter(context, cartData) }

    private fun initCart() {

        cartRecyclerView.layoutManager = LinearLayoutManager(context)
        cartRecyclerView.adapter = cartAdapter
        cartRecyclerView.addItemDecoration(
            RecyclerViewDivider(
                context,
                LinearLayoutManager.VERTICAL,
                DensityUtil.dp2px(10f),
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

    override fun lazyLoad() {
    }



    override fun initEvent() {

        refreshLayout.setOnRefreshListener {
            initRefresh()
        }

//        refreshLayout.setOnLoadMoreListener {
//            cartData.addAll(getCartData())
//            cartAdapter.notifyDataSetChanged()
//            refreshLayout.finishLoadMore()
//
//        }

        cartAdapter.apply {

            //数量
            onShopNumListener = {
                InputNumDialog.showDialog(childFragmentManager, it)
                    .setOnItemClickListener(object : InputNumDialog.OnItemClickListener {
                        override fun onNumConfirm(num: Int) {
                            //修改商品数量
                        }
                    })
            }

            //规格参数
            onShopSpecListener = {
                val popWindow = object : GroupStylePopupWindow(
                    activity as Activity,
                    R.layout.pop_order_style,
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                ) {}
                popWindow.showAtLocation(parentLayout, Gravity.BOTTOM, 0, 0)
            }
        }

        //全选
        allChoose.setOnCheckedChangeListener { _, isChecked ->
            //是否全选
            cartAdapter.ifCheckAll(isChecked)

        }

        //结算
        settle.setOnClickListener {

            if (management.isSelected) {
                //删除
            } else {
                //结算
//                showOrderPopWindow("")
                ConfirmOrderActivity.actionStart(context)
                showToast("选中的id:" + filter())
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

    private fun getCartData(): ArrayList<ShopList> {
        val list = ArrayList<ShopList>()
        list.addAll(
            arrayListOf(
                ShopList(
                    "小米旗舰店", arrayListOf(
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

    //去重
    private fun filter(): ArrayList<Int> {
        val newList = ArrayList<Int>()
        val it = cartAdapter.mCheckList.iterator()
        while (it.hasNext()) {
            val obj = it.next()
            if (!newList.contains(obj)) {
                newList.add(obj)
            }
        }
        return newList
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