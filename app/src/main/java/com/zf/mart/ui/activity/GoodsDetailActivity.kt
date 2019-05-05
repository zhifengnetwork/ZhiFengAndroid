package com.zf.mart.ui.activity

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.util.Log
import android.view.Gravity
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ScrollView
import androidx.core.content.ContextCompat
import androidx.core.widget.NestedScrollView
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager.widget.ViewPager
import com.bumptech.glide.Glide
import com.flyco.tablayout.listener.CustomTabEntity
import com.zf.mart.MyApplication.Companion.context
import com.zf.mart.R
import com.zf.mart.base.BaseActivity
import com.zf.mart.mvp.bean.*
import com.zf.mart.mvp.contract.GoodsDetailContract
import com.zf.mart.mvp.presenter.GoodsDetailPresenter
import com.zf.mart.showToast
import com.zf.mart.ui.adapter.*
import com.zf.mart.ui.fragment.graphic.GraphicFragment
import com.zf.mart.ui.fragment.graphic.OrderAnswerFragment
import com.zf.mart.ui.fragment.same.DetailSameFragment
import com.zf.mart.utils.GlideUtils
import com.zf.mart.utils.LogUtils
import com.zf.mart.utils.StatusBarUtils
import com.zf.mart.view.dialog.ShareSuccessDialog
import com.zf.mart.view.popwindow.RegionPopupWindow
import com.zf.mart.view.popwindow.ServicePopupWindow
import com.zzhoujay.richtext.RichText
import kotlinx.android.synthetic.main.activity_goods_detail2.*
import kotlinx.android.synthetic.main.layout_buy.*
import kotlinx.android.synthetic.main.layout_detail_brand.*
import kotlinx.android.synthetic.main.layout_detail_eva.*
import kotlinx.android.synthetic.main.layout_detail_goods.*
import kotlinx.android.synthetic.main.layout_detail_head.*
import kotlinx.android.synthetic.main.layout_detail_same.*
import kotlinx.android.synthetic.main.pop_detail_share.view.*
import kotlinx.android.synthetic.main.pop_detail_address.view.*
import kotlinx.android.synthetic.main.pop_detail_specs.view.*

/**
 * 商品详情
 */
class GoodsDetailActivity : BaseActivity(), GoodsDetailContract.View {

    override fun showError(msg: String, errorCode: Int) {
        showToast(msg)
    }

    //获得商品详情
    override fun getGoodsDetail(bean: GoodsDetailBean) {
        mData = bean
        loadData()
        //图文详情
        initGraphic()
    }

    //获得商品评论（默认15条 全部 第一页）
    override fun setGoodEva(bean: List<GoodEvaList>) {
        mEva.clear()
        mEva.addAll(bean)
        evaAdapter.notifyDataSetChanged()
    }

    //获得地址列表
    override fun getAddress(bean: List<AddressBean>) {
        mAddress.clear()
        mAddress.addAll(bean)
        popAdapter.notifyDataSetChanged()
    }

    //获得商品运费
    override fun getGoodsFreight(bean: GoodsFreightBean) {
        if (bean.freight == "0.00" || bean.freight == "0") fare.text = "免邮费" else fare.text = bean.freight
    }

    //获得商品规格
    override fun getGoodsSpce(bean: List<List<GoodsSpecBean>>) {
        mSpec.clear()
        mSpec.addAll(bean)
        specsAdapter.notifyDataSetChanged()

    }

    //点击关注商品
    override fun setCollectGoods(msg: String) {
        showToast(msg)
    }

    //点击取消关注商品
    override fun delCollectGoods(msg: String) {
        showToast(msg)
    }

    //加入购物车
    override fun addCartSuccess(msg: String) {
        showToast(msg)
        cart.isChecked = true
    }

    //根据规格key获取图片，库存
    override fun getPricePic(bean: GoodsSpecInfo) {
        mPrice = bean
        specsPopWindow.updata()
    }

    override fun showLoading() {

    }

    override fun dismissLoading() {

    }

    /**要传一个商品ID过来*/
    companion object {

        fun actionStart(context: Context?, goods_id: String) {
            val intent = Intent(context, GoodsDetailActivity::class.java)
            intent.putExtra("id", goods_id)
            context?.startActivity(intent)
        }

    }

    override fun initToolBar() {

        StatusBarUtils.darkMode(
            this,
            ContextCompat.getColor(this, R.color.colorSecondText),
            0.3f
        )


        //分享
        shareLayout.setOnClickListener {
            val popUpWindow = object : ServicePopupWindow(
                this, R.layout.pop_detail_share,
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            ) {
                override fun initView() {
                    contentView.apply {

                        weChat.setOnClickListener {
                            onDismiss()
                            ShareSuccessDialog.showDialog(supportFragmentManager)
                        }

                        cancel.setOnClickListener {
                            onDismiss()
                        }
                    }
                }
            }
            popUpWindow.showAtLocation(shareLayout, Gravity.BOTTOM, 0, 0)
        }


        backLayout.setOnClickListener {
            finish()
        }
    }

    override fun layoutId(): Int = R.layout.activity_goods_detail2

    //网络请求
    private val presenter by lazy { GoodsDetailPresenter() }
    //商品详情
    private var mData: GoodsDetailBean? = null
    //商品评论
    private var mEva = ArrayList<GoodEvaList>()
    //商品规格
    private var mSpec = ArrayList<List<GoodsSpecBean>>()
    //详细商品规格信息
    private var mPrice: GoodsSpecInfo? = null
    //地址pop弹窗
    private lateinit var addressPopWindow: RegionPopupWindow
    //商品规格pop弹窗
    private lateinit var specsPopWindow: RegionPopupWindow
    //pop地址适配器
    private val popAdapter by lazy { GoodsDetailAdapter(context, mAddress) }
    //pop商品规格适配器
    private val specsAdapter by lazy { GoodsSpecsAdapter(context, mSpec) }
    //第一次打开pop商品规格默认请求
    private var no_off = true
    //接收地址列表
    private var mAddress = ArrayList<AddressBean>()
    //商品评价adapter
    private val evaAdapter by lazy { DetailEvaAdapter(this, mEva) }
    //Banner轮播图
    private var images = ArrayList<String>()
    //接收传递过来的id
    private var goodsID = ""
    //传递过来的规格ID
    private var item_id = ""

    override fun initData() {
        goodsID = intent.getStringExtra("id")
    }

    override fun initView() {
        presenter.attachView(this)

        floatingButton.hide()

        //全部评价
        allEvaluation.setOnClickListener {
            EvaluationActivity.actionStart(this, mData?.goods?.goods_id)
        }

        //banner
        initBanner()

        //商品评价
        initEvaluation()

        //问大家
        initAsk()

        //商家品牌推荐
        initBrand()

        //相似推荐
        initSame()

        //图文详情
//        initGraphic()

    }


    private fun changeAlpha(color: Int, fraction: Float): Int {
        val red = Color.red(color)
        val green = Color.green(color)
        val blue = Color.blue(color)
        val alpha = (Color.alpha(color) * fraction).toInt()
        return Color.argb(alpha, red, green, blue)
    }

    private fun initGraphic() {

        val titles = arrayOf("图文详情", "答疑")
        val fgms = arrayListOf(
            GraphicFragment.newInstance(mData?.goods_content, mData?.goods?.goods_id) as Fragment,
            OrderAnswerFragment.newInstance() as Fragment)
        segmentTabLayout.setTabData(titles, this, R.id.graphicFragment, fgms)
    }


    private fun initEvaluation() {
        val manager = LinearLayoutManager(this)
        manager.orientation = LinearLayoutManager.HORIZONTAL
        evaRecyclerView.layoutManager = manager
        evaRecyclerView.adapter = evaAdapter
    }

    private val askAdapter by lazy { DetailAskAdapter(this) }

    private fun initAsk() {
        askRecyclerView.layoutManager = LinearLayoutManager(this)
        askRecyclerView.adapter = askAdapter
    }

    private val brandAdapter by lazy { DetailBrandAdapter(this) }

    private fun initBrand() {
        val manager = LinearLayoutManager(this)
        manager.orientation = LinearLayoutManager.HORIZONTAL
        brandRecyclerView.layoutManager = manager
        brandRecyclerView.adapter = brandAdapter
    }

    /**
     * 相似推荐
     * pageRecyclerView
     */
    private fun initSame() {

        val fgms = arrayListOf(
            DetailSameFragment.newInstance() as Fragment
            , DetailSameFragment.newInstance() as Fragment
        )
        val entitys = ArrayList<CustomTabEntity>()
        entitys.add(TabEntity("相似推荐", 0, 0))
        entitys.add(TabEntity("同类热销排行", 0, 0))
        sameTabLayout.setTabData(entitys, this, R.id.frameLayout, fgms)

    }

    private fun initBanner() {

        val imageViews = ArrayList<ImageView>()
        repeat(images.size) { pos ->
            val img = ImageView(this)

            Glide.with(this).load(images[pos]).into(img)

            img.scaleType = ImageView.ScaleType.CENTER_CROP
            imageViews.add(img)
            img.setOnClickListener {
                LogUtils.e(">>>>>>点击了第：$pos")
            }
        }
        bannerViewPager.adapter = GuideAdapter(imageViews)
        bannerNum.text = "1/${images.size}"

        /** 滑动改变指示器 */
        bannerViewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {
            }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
            }

            override fun onPageSelected(position: Int) {
                bannerNum.text = "${position + 1}/${images.size}"
            }
        })
    }

    override fun initEvent() {

        scrollView.setOnScrollChangeListener { v: NestedScrollView?, scrollX: Int, scrollY: Int, oldScrollX: Int, oldScrollY: Int ->
            //标题栏渐变
            var alpha = scrollY / 100 * 0.7f
            if (alpha >= 1.0) {
                alpha = 1.0f
            }
            orderDetailHead.setBackgroundColor(
                changeAlpha(
                    ContextCompat.getColor(this, R.color.whit)
                    , alpha
                )
            )
            //回到顶部按钮
            if (scrollY - oldScrollY > 0) {
                floatingButton.hide()
            } else {
                floatingButton.show()
            }

        }

        floatingButton.setOnClickListener {
            scrollView.fullScroll(ScrollView.SCROLL_INDICATOR_TOP)
        }

        /**选择配送地址*/
        goods_address.setOnClickListener {
            //请求地址列表
            presenter.requestAddress()

            addressPopWindow = object : RegionPopupWindow(
                this, R.layout.pop_detail_address,
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            ) {
                override fun initView() {

                    contentView?.apply {
                        pop_recyclerView.layoutManager = LinearLayoutManager(context)
                        pop_recyclerView.adapter = popAdapter
                        //将所选的的地址信息传递过来/计算出邮费
                        popAdapter.mClickListener = {
                            goods_address.text = it.province_name + it.city_name + it.district_name
                            //邮费请求
                            presenter.requestGoodsFreight(goodsID, it.city, "1")
                            addressPopWindow.onDismiss()
                        }
                    }
                }
            }
            //更新视图
//            addressPopWindow.updata()
            addressPopWindow.showAtLocation(parentLayout, Gravity.BOTTOM, 0, 0)
        }

        //收藏按钮
        collect.setOnClickListener {
            if (collect.isChecked) {
                presenter.requestCollectGoods(mData?.goods?.goods_id.toString())

            } else {
                presenter.requestDelCollectGoods(mData?.goods?.goods_id.toString())
            }
        }

        //加入购物车
        shop_cat.setOnClickListener {
            specsPopWindow = object : RegionPopupWindow(
                this, R.layout.pop_detail_specs,
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            ) {
                override fun initView() {

                    var sum = contentView?.card_number?.text.toString().toInt()
                    //第一次打开默认请求第一个
                    if (no_off) {
                        //重组ID
                        for (i in 0 until mSpec.size) {
                            item_id = if (i == 0) {
                                mSpec[i][0].id
                            } else {
                                item_id + "_" + mSpec[i][0].id
                            }
                        }
                        no_off = if (item_id != "") {
                            presenter.requestPricePic(item_id, mData?.goods?.goods_id.toString())
                            false
                        } else {
                            true
                        }
                    }

                    contentView?.apply {
                        specs_rl.layoutManager = LinearLayoutManager(context)
                        specs_rl.adapter = specsAdapter
                        //没有规格的商品 根据no_off判断
                        if (no_off) {
                            //图片
                            GlideUtils.loadUrlImage(
                                context,
                                "https://mobile.zhifengwangluo.c3w.cc" + mData?.goods?.original_img,
                                goods_img
                            )
                            //名称
                            goodsName.text = mData?.goods?.goods_name
                            //价格
                            goods_price.text = mData?.goods?.shop_price
                            //库存
                            goods_stock.text = "剩余库存:" + mData?.goods?.store_count
                        } else {
                            //图片
                            GlideUtils.loadUrlImage(context, mPrice?.spec_img, goods_img)
                            //名称
                            goodsName.text = mData?.goods?.goods_name
                            //价格
                            goods_price.text = mPrice?.price
                            //库存
                            goods_stock.text = "剩余库存:" + mPrice?.store_count
                        }
                        //输入文本框
                        card_number.addTextChangedListener {
                            val text = it.toString()
                            val len = it.toString().length
                            if (len > 1 && text.startsWith("0")) {
                                it?.replace(0, 1, "")
                            }
                            sum = if (text == "") {
                                card_number.setText("1")
                                1
                            } else {
                                text.toInt()
                            }


                        }
                        //点击减少数量
                        del_btn.setOnClickListener {

                            sum -= 1
                            if (sum <= 0) {
                                sum = 1
                            }
                            card_number.setText(sum.toString())

                        }
                        //点击增加数量
                        add_btn.setOnClickListener {
                            sum += 1
                            card_number.setText(sum.toString())

                        }
                        //点击加入购物车
                        specs_btn.setOnClickListener {
                            //商品ID 数量（默认1） 规格ID
                            presenter.requestAddCart(mData?.goods?.goods_id.toString(), sum.toString(), item_id)
                        }

                    }
                    //适配器监听回调
                    specsAdapter.mClickListener = {
                        item_id = it
                        presenter.requestPricePic(item_id, mData?.goods?.goods_id.toString())
                    }
//                    specsAdapter.setOnCheckedChangeListener(object : GoodsSpecsAdapter.OnCheckedChangeListener {
//                        override fun onItemClick(itemId: String) {
//                            item_id = itemId
//                            presenter.requestPricePic(item_id, mData?.goods?.goods_id.toString())
//                        }
//
//
//                    })

                }

            }
            specsPopWindow.updata()
            specsPopWindow.showAtLocation(parentLayout, Gravity.BOTTOM, 0, 0)

        }
        //购物车复选框
        cart.setOnCheckedChangeListener { _, isChecked ->
            //选中亮图标 未选中在购物车删除该商品
            if (isChecked) {

            }
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.detachView()
    }

    override fun start() {
        //请求商品详情
        presenter.requestGoodsDetail(goodsID)
        //请求评论
        presenter.requestGoodEva(goodsID, 1, 1, 6)
        //请求规格
        presenter.requestGoodsSpec(goodsID)
    }

    /**将网络请求到的数据赋值到页面上*/
    private fun loadData() {
        //商品名字
        goods_name.text = mData?.goods?.goods_name
        //商品价格
        shop_price.text = mData?.goods?.shop_price
        //市场价格
        market_price.text = "￥" + mData?.goods?.market_price
        //销量
        virtual_sales_sum.text = mData?.goods?.virtual_collect_sum
        //库存
        store_count.text = mData?.goods?.store_count + "件"
        //运费
        if (mData?.goods?.is_free_shipping == "1") fare.text = "免运费" else fare.text = "按实际地区收费"
        //说明
        goods_remark.text = mData?.goods?.goods_remark
        //商品评论数
        comments.text = "(${mData?.goods?.comment_count})"
        //好评率
        high_rate.text = mData?.goods?.comment_fr?.high_rate
        //问大家数

        //店铺名字
        shopName.text = mData?.goods?.seller_info?.store_name
        //店铺在售件数
        inSellNum.text = mData?.goods?.seller_info?.num
        //店铺图片avatar
//         GlideUtils.loadUrlImage(this,mData?.goods,shopIcon)
        //是否已收藏
        collect.isChecked = mData?.goods?.is_collect != "0"
        //是否在购物车
        cart.isChecked = mData?.goods?.is_cart != "0"
        //轮播图获取
        if (mData?.goods?.goods_images != null && mData != null) {
            for (i in 0 until mData?.goods?.goods_images?.size!!) {
                images.add("https://mobile.zhifengwangluo.c3w.cc" + mData?.goods?.goods_images!![i])
            }
        }
        //banner
        initBanner()


    }
}