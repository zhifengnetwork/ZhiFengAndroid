package com.zf.mart.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import androidx.recyclerview.widget.RecyclerView
import com.zf.mart.R
import com.zf.mart.mvp.bean.GoodsList
import com.zf.mart.utils.LogUtils
import kotlinx.android.synthetic.main.item_foot.view.*

/**
 *  ifAllChoose 是否全部选中 传入布尔值，如果为真则全选中，如果为假则全部取消选中
 *   ifEdit    是否编辑状态   如果为真则显示选择框，如果为假则隐藏选择框
 *
 *   每次点击单个item都需要遍历所有item的选中状态，如果有非选中的，则让FootActivity的全选按钮取消选中，
 *    如果全部选中了，则让全选按钮选中状态
 *
 *
 *    每点击一个item的checkBox,就添加进数组里面，在activity中获取这个数组，是否被全选
 *
 */
class FootAdapter(val context: Context, val data: ArrayList<GoodsList>) :
    RecyclerView.Adapter<FootAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_foot, parent, false)
        return ViewHolder(view)
    }

    val checkList = ArrayList<Int>()

    private var mIfAllChoose = false
    //是否全部选中
    fun setIfAllChoose(ifAllChoose: Boolean) {
        mIfAllChoose = ifAllChoose
        notifyDataSetChanged()
        // 如果取消全选，则清空 checkList
        //可以用tag保存商品的id来保存
//        if (!ifAllChoose) {
//            checkList.clear()
//        }
    }

    private var mIfEdit = false
    //是否显示选择框
    fun setIfCanEdit(ifEdit: Boolean) {
        mIfEdit = ifEdit
        notifyDataSetChanged()
    }

    fun setOnClickListener(listener: OnItemClickListener) {
        mListener = listener
    }

    private var mListener: OnItemClickListener? = null

    interface OnItemClickListener {
        //如果全选中
        fun checkAll()

        //取消全选
        fun unCheckAll()
    }


    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.apply {

            goodsName.text = data[position].name

            //是否显示选择框
            checkBox.visibility = if (mIfEdit) View.VISIBLE else View.GONE

            //是否全部选中
            checkBox.isChecked = mIfAllChoose


            checkBox.setOnCheckedChangeListener { _, isChecked ->
                //遍历所有item的选择框，更改FootActivity的全选按钮的选中状态
                if (isChecked) {
                    checkList.add(position)
                } else {
                    checkList.remove(position)
                }
                if (checkList.size == data.size) {
                    mListener?.checkAll()
                } else {
                    mListener?.unCheckAll()
                }
            }

        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

}