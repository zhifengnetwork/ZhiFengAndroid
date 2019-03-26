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
import kotlinx.android.synthetic.main.classify_right_item.view.*

//class ClassifyRightAdapter(val context: Context?, val mData:List<Int>?) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
//
//    private var type:Int=0
//
//    private val data : List<Int>? = mData
//
//    private val adapter1 = ClassifyRightconterAdapter1(context) //正常分类3网格式
//
//    private val adapter2 = ClassifyRightconterAdapter2(context)//精选专辑
//
//    private val adapter3 = ClassifyRightconterAdapter3(context)//化妆品商品
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
//
//        if (viewType==1){
//            val view = LayoutInflater.from(context).inflate(R.layout.classify_right_item, parent, false)
//            view.itemtitle.text="常用分类"
//            return oneViewHolder(view)
//        }else if(viewType==2){
//            val view = LayoutInflater.from(context).inflate(R.layout.classify_right_item, parent, false)
//            view.itemtitle.text="热门分类"
//            return twoViewHolder(view)
//        }else if(viewType==3){
//            val view = LayoutInflater.from(context).inflate(R.layout.classify_right_item, parent, false)
//            view.itemtitle.text="推荐品牌"
//            return threeViewHolder(view)
//        }else if(viewType==4){
//            val view = LayoutInflater.from(context).inflate(R.layout.classify_right_item, parent, false)
//            view.itemtitle.text="精选专辑"
//
//            return fourViewHolder(view)
//        }
//        val view = LayoutInflater.from(context).inflate(R.layout.classify_right_item, parent, false)
//        return oneViewHolder(view)
//    }
//
//    override fun getItemCount(): Int = data!!.size
//
//    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
//        if(position==3){
//            holder.itemView.apply {
//                classify_left_item_cylv.layoutManager = LinearLayoutManager(context)
//
//                classify_left_item_cylv.adapter=adapter2
//            }
//
//        }else {
//            holder.itemView.apply {
//                classify_left_item_cylv.layoutManager = GridLayoutManager(context, 3)
//                classify_left_item_cylv.adapter = adapter1
//            }
//        }
//
////        holder.itemView.apply {
////            classify_left_item_cylv.layoutManager = GridLayoutManager(context,3)
////            classify_left_item_cylv.adapter=adapter
////        }
//        holder.itemView.setOnClickListener {
//                Toast.makeText(context,"点击了"+position+"--"+data!!.size, Toast.LENGTH_SHORT).show()
//        }
//    }
//
//    override fun getItemViewType(position: Int): Int {
//        when(data!!.get(position)){
//            1 -> type=1
//            2 -> type=2
//            3 -> type=3
//            4 -> type=4
//        }
//        return type
//    }
//
//    class oneViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
//    class twoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
//    class threeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
//    class fourViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
//
//}