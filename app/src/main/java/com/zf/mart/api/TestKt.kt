package com.zf.mart.api

import com.zf.mart.mvp.bean.DateHeadEntity


fun main(args: Array<String>) {


    val urlTxt = "&lt;p&gt;&lt;img src=&quot;https://mobile.zhifengwangluo.c3w.cc/public/upload/goods/2019/03-16/68eb9fe414499db3a35189ae98b6a28b.png&quot;/&gt;&lt;/p&gt;&lt;p&gt;&lt;img src=&quot;https://mobile.zhifengwangluo.c3w.cc/public/upload/goods/2019/03-16/f3490163655c39ec7de2916aa6a8c528.png&quot;/&gt;&lt;/p&gt;&lt;p&gt;&lt;img src=&quot;https://mobile.zhifengwangluo.c3w.cc/public/upload/goods/2019/03-16/9c532305143b3a9a7d49e4d7265e83be.png&quot;/&gt;&lt;/p&gt;&lt;p&gt;&lt;img src=&quot;https://mobile.zhifengwangluo.c3w.cc/public/upload/goods/2019/03-16/02e782dba0fd4bbdb9b896bcb11c6cf1.png&quot;/&gt;&lt;/p&gt;&lt;p&gt;&lt;img src=&quot;https://mobile.zhifengwangluo.c3w.cc/public/upload/goods/2019/03-16/57171d02d130039140f95b39c4820935.png&quot;/&gt;&lt;/p&gt;&lt;p&gt;&lt;img src=&quot;https://mobile.zhifengwangluo.c3w.cc/public/upload/goods/2019/03-16/ea95c060a48190807adcfd4726c23eb8.png&quot;/&gt;&lt;/p&gt;&lt;p&gt;&lt;img src=&quot;https://mobile.zhifengwangluo.c3w.cc/public/upload/goods/2019/03-16/fbea54101deb9661e70a676701f9471b.png&quot;/&gt;&lt;/p&gt;&lt;p&gt;&lt;img src=&quot;https://mobile.zhifengwangluo.c3w.cc/public/upload/goods/2019/03-16/7074bdefdc72af614830fbf1f18b50e9.png&quot;/&gt;&lt;/p&gt;&lt;p&gt;&lt;img src=&quot;https://mobile.zhifengwangluo.c3w.cc/public/upload/goods/2019/03-16/9a2684ceb3d737d585384a1fefe695d1.png&quot;/&gt;&lt;/p&gt;&lt;p&gt;&lt;img src=&quot;https://mobile.zhifengwangluo.c3w.cc/public/upload/goods/2019/03-16/2af62d4bb4591810c932f8079b9190bd.png&quot;/&gt;&lt;/p&gt;&lt;p&gt;&lt;img src=&quot;https://mobile.zhifengwangluo.c3w.cc/public/upload/goods/2019/03-16/3fae8f4d4a2e7f7de5c0a152e6fdd371.png&quot;/&gt;&lt;/p&gt;&lt;p&gt;&lt;img src=&quot;https://mobile.zhifengwangluo.c3w.cc/public/upload/goods/2019/03-16/06689f3b3b1e8b96718c1edc393b6e11.png&quot;/&gt;&lt;/p&gt;"

//   print( "aaa"+TestJava.htmlReplace(urlTxt))


    val bean = ArrayList<DateHeadEntity>()
    bean.add(DateHeadEntity("小米", "7"))
    bean.add(DateHeadEntity("小米", "8"))
    bean.add(DateHeadEntity("华为", "荣耀1"))
    bean.add(DateHeadEntity("小米", "9"))
    bean.add(DateHeadEntity("华为", "荣耀2"))
    bean.add(DateHeadEntity("华为", "荣耀3"))

    val result = bean.groupBy {
        val key = it.time
        if (key == it.time) it.time else it.time
    }

    print(result)


//    var sum = 0
//    bean.forEach {
//        if (it.price == "1") {
//            sum += 1
//        }
//    }
//
//    print(">>>"+sum)

}
