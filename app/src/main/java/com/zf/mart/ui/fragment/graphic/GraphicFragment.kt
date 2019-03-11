package com.zf.mart.ui.fragment.graphic

import com.zf.mart.R
import com.zf.mart.base.BaseFragment
import com.zf.mart.utils.LogUtils
import com.zzhoujay.richtext.RichText
import kotlinx.android.synthetic.main.fragment_graphic.*

class GraphicFragment : BaseFragment() {

    companion object {
        fun newInstance(): GraphicFragment {
            return GraphicFragment()
        }
    }

    override fun getLayoutId(): Int = R.layout.fragment_graphic


    override fun initView() {

        RichText.initCacheDir(context)
        RichText.fromHtml(htmlTxt).into(textView)


    }


    override fun lazyLoad() {
    }

    override fun initEvent() {
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
            "<img src=\"http://www.pptok.com/wp-content/uploads/2012/08/xunguang-4.jpg\" width=\"258\" height=\"39\" />\n" +
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