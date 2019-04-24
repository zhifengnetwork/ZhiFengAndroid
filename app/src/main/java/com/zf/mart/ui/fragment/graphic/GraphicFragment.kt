package com.zf.mart.ui.fragment.graphic

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.scwang.smartrefresh.layout.util.DensityUtil
import com.zf.mart.R
import com.zf.mart.base.BaseFragment
import com.zf.mart.mvp.bean.GoodsAttrBean
import com.zf.mart.mvp.contract.GoodsAttrContract
import com.zf.mart.mvp.presenter.GoodsAttrPresenter
import com.zf.mart.ui.adapter.OrderInfoAdapter
import com.zf.mart.utils.LogUtils
import com.zf.mart.utils.UnicodeUtil
import com.zf.mart.view.recyclerview.RecyclerViewDivider
import com.zzhoujay.richtext.ImageHolder
import com.zzhoujay.richtext.RichText
import kotlinx.android.synthetic.main.fragment_graphic.*

class GraphicFragment : BaseFragment(), GoodsAttrContract.View {
    override fun showError(msg: String, errorCode: Int) {

    }

    override fun getGoodsAttr(bean: GoodsAttrBean) {
        mData = bean
        recyclerView.adapter = adapter
        Log.e("检测","接收到数据"+mData)
    }

    override fun showLoading() {

    }

    override fun dismissLoading() {

    }

    //接收详情信息
    companion object {
        fun newInstance(data: String?, id: String?): GraphicFragment {
            val fragment = GraphicFragment()
            val bundle = Bundle()
            bundle.putString("mData", data)
            bundle.putString("id", id)
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun getLayoutId(): Int = R.layout.fragment_graphic

    private val adapter by lazy { OrderInfoAdapter(context,mData) }

    private val presenter by lazy { GoodsAttrPresenter() }

    private var mData: GoodsAttrBean? = null
    //接收传递过来的ID
    private var id:String=""

    override fun initView() {
        presenter.attachView(this)

        id = arguments?.getString("id").toString()

        val htmlText = arguments?.getString("mData")

        RichText.initCacheDir(context)

        RichText.fromHtml(UnicodeUtil.translation(htmlText)).into(h5Decode)

        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.addItemDecoration(
            RecyclerViewDivider(
                context,
                LinearLayoutManager.VERTICAL,
                2,
                ContextCompat.getColor(context!!, R.color.colorLine)
            )
        )

    }


    override fun lazyLoad() {
    }

    override fun initEvent() {
        show.setOnClickListener {
            show.isSelected = !show.isSelected
            if (show.isSelected) {
                show.text = "收起"
                h5Decode.visibility = View.VISIBLE
            } else {
                show.text = "展开"
                h5Decode.visibility = View.GONE
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.detachView()
        RichText.clear(this)
    }

    override fun onStart() {
        super.onStart()
        presenter.requestGoodsAttr(id)
    }

    val htmlTxt = "<!DOCTYPE html>\n" +
            "<html>\n" +
            "<head>\n" +
            "<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" />\n" +
            "<title>这里填写标题</title>\n" +
            "<meta name=\"keywords\" content=\"这里填写关键词\" />\n" +
            "<meta name=\"description\" content=\"这里填写说明内容\" />\n" +
            "\n" +
            "<script language=\"JavaScript\" type=\"text/javascript\">\n" +
            "<!--JS代码位置-->\n" +
            "</script>\n" +
            "\n" +
            "<style type=\"text/css\">\n" +
            "<!--CSS样式代码位置-->\n" +
            "</style>\n" +
            "\n" +
            "</head>\n" +
            "\n" +
            "<body>\n" +
            "\n" +
            "这里填写HTML代码\n" +
            "\n" +
            "<p>\n" +
            "这是段落\n" +
            "</p>\n" +
            "\n" +
            "\n" +
            "<p>\n" +
            "这是段落\n" +
            "</p>\n" +
            "\n" +
            "<p>\n" +
            "这是段落\n" +
            "</p>\n" +
            "\n" +
            "<p>\n" +
            "这是段落\n" +
            "</p>\n" +
            "\n" +
            "<p>\n" +
            "这是段落\n" +
            "</p>\n" +
            "\n" +
            "<p>\n" +
            "这是段落\n" +
            "</p>\n" +
            "\n" +
            "<p>\n" +
            "这是段落\n" +
            "</p>\n" +
            "\n" +
            "\n" +
            "<p>\n" +
            "这是段落\n" +
            "</p>\n" +
            "\n" +
            "\n" +
            "<p>\n" +
            "这是段落\n" +
            "</p>\n" +
            "<p>\n" +
            "这是段落\n" +
            "</p>\n" +
            "<p>\n" +
            "这是段落\n" +
            "</p>\n" +
            "<p>\n" +
            "这是段落\n" +
            "</p>\n" +
            "<p>\n" +
            "这是段落\n" +
            "</p>\n" +
            "<p>\n" +
            "这是段落\n" +
            "</p>\n" +
            "\n" +
            "<p>\n" +
            "这是段落\n" +
            "</p>\n" +
            "\n" +
            "<p>\n" +
            "这是段落\n" +
            "</p> \n" +
            "\n" +
            "<p>\n" +
            "\n" +
            "<img src=\"https://mobile.zhifengwangluo.c3w.cc/public/upload/goods/2019/03-16/6a293870038abbc58820f605d20e9e4b.jpg\" width=\"258\" height=\"39\" />\n" +
            "</p>\n" +
            "\n" +
            "<p>\n" +
            "\n" +
            "<img src=\"https://mobile.zhifengwangluo.c3w.cc/public/upload/goods/2019/03-16/6a293870038abbc58820f605d20e9e4b.jpg\" width=\"258\" height=\"39\" />\n" +
            "</p>\n" +
            "\n" +
            "<p>\n" +
            "\n" +
            "<img src=\"https://mobile.zhifengwangluo.c3w.cc/public/upload/goods/2019/03-16/6a293870038abbc58820f605d20e9e4b.jpg\" width=\"258\" height=\"39\" />\n" +
            "</p>\n" +
            "\n" +
            "<p>\n" +
            "\n" +
            "<img src=\"http://pic3.huitu.com/res/20120531/721_20120531125839525174_1.jpg\" width=\"258\" height=\"39\" />\n" +
            "</p>\n" +
            "\n" +
            "<p>\n" +
            "\n" +
            "<img src=\"http://www.pptok.com/wp-content/uploads/2012/08/xunguang-4.jpg\" width=\"258\" height=\"39\" />\n" +
            "</p>\n" +
            "\n" +
            "<p>\n" +
            "\n" +
            "<img src=\"http://www.pptok.com/wp-content/uploads/2012/08/xunguang-4.jpg\" width=\"258\" height=\"39\" />\n" +
            "</p>\n" +
            "<p>\n" +
            "\n" +
            "<img src=\"http://img.zcool.cn/community/01dc60554bf752000001bf72fa836b.jpg@1280w_1l_2o_100sh.png\" width=\"258\" height=\"39\" />\n" +
            "</p>\n" +
            "\n" +
            "<p>\n" +
            "\n" +
            "<img src=\"http://www.pptok.com/wp-content/uploads/2012/08/xunguang-4.jpg\" width=\"258\" height=\"39\" />\n" +
            "</p>\n" +
            "<img src=\"http://www.pptok.com/wp-content/uploads/2012/08/xunguang-4.jpg\" width=\"258\" height=\"39\" />\n" +
            "\n" +
            "<img src=\"http://img2.imgtn.bdimg.com/it/u=3496345838,732839400&fm=26&gp=0.jpg\" width=\"258\" height=\"39\" />\n" +
            "\n" +
            "\n" +
            "<img src=\"http://pic11.nipic.com/20101203/6066308_121059019434_2.jpg\" width=\"258\" height=\"39\" />\n" +
            "\n" +
            "\n" +
            "<img src=\"http://www.pptok.com/wp-content/uploads/2012/08/xunguang-4.jpg\" width=\"258\" height=\"39\" />\n" +
            "\n" +
            "\n" +
            "<img src=\"http://www.pptok.com/wp-content/uploads/2012/08/xunguang-4.jpg\" width=\"258\" height=\"39\" />\n" +
            "\n" +
            "很长\n" +
            "</body>\n" +
            "</html>\n"
}