package com.zf.mart.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.zf.mart.R
import com.zf.mart.mvp.bean.ClassifyBean
import com.zf.mart.mvp.bean.ClassifyProductBean
import kotlinx.android.synthetic.main.classify_right_item.view.*

class ClassifyRightAdapter(val context: Context?, val mData:ArrayList<ClassifyProductBean>,val name:String) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var type:Int=0

    private val data : ArrayList<ClassifyProductBean> = mData

    private val adapter1 = ClassifyRightconterAdapter1(context,data) //正常分类3网格式

    private val adapter2 = ClassifyRightconterAdapter2(context)//精选专辑

    private val adapter3 = ClassifyRightconterAdapter3(context)//化妆品商品

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        val view = LayoutInflater.from(context).inflate(R.layout.classify_right_item, parent, false)
//        if(id=="为您推荐"){
//            when(viewType){
//                1 -> view.itemtitle.text="常用分类"
//                2 -> view.itemtitle.text="热门分类"
//                3 -> view.itemtitle.text="推荐品牌"
//                4 -> view.itemtitle.text="精选专辑"
//            }
//        }else if(id=="美容彩妆"){
//            when(viewType){
//                1 -> view.itemtitle.text="面部护理"
//                2 -> view.itemtitle.text="洗发护理"
//
//            }
//        }

        return ViewHolder(view)
    }

    override fun getItemCount(): Int = 1

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

//        if(id=="为您推荐") {
//            if (position == 3) {
//                holder.itemView.apply {
//                    classify_left_item_cylv.layoutManager = LinearLayoutManager(context)
//
//                    classify_left_item_cylv.adapter = adapter2
//                }
//
//            } else {
//                holder.itemView.apply {
//                    classify_left_item_cylv.layoutManager = GridLayoutManager(context, 3)
//                    classify_left_item_cylv.adapter = adapter1
//                }
//            }
//        }else if(id=="美容彩妆"){
//            holder.itemView.apply {
//                classify_left_item_cylv.layoutManager = LinearLayoutManager(context)
//                classify_left_item_cylv.adapter = adapter3
//            }
//        }

        holder.itemView.apply {
            itemtitle.text=name
            classify_left_item_cylv.layoutManager = GridLayoutManager(context, 3)
            adapter1.notifyDataSetChanged()
            classify_left_item_cylv.adapter = adapter1
        }
        holder.itemView.setOnClickListener {
                Toast.makeText(context,"点击了"+position+"--"+data.size, Toast.LENGTH_SHORT).show()
        }
    }
//
//    override fun getItemViewType(position: Int): Int {
//          type=data!!.get(position)
////        when(data!!.get(position)){
////            1 -> type=1
////            2 -> type=2
////            3 -> type=3
////            4 -> type=4
////        }
//        return type
//    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

}