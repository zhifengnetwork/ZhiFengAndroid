package com.zf.mart.ui.activity

import android.content.Context
import android.content.Intent
import android.util.TypedValue
import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.zf.mart.R
import com.zf.mart.base.BaseActivity
import com.zf.mart.mvp.bean.DateHeadEntity
import com.zf.mart.mvp.bean.GoodsList
import com.zf.mart.mvp.bean.MonthList
import com.zf.mart.showToast
import com.zf.mart.ui.adapter.FootAdapter
import com.zf.mart.utils.LogUtils
import com.zf.mart.utils.StatusBarUtils
import com.zf.mart.view.recyclerview.FloatingItemDecoration
import kotlinx.android.synthetic.main.activity_foot.*
import kotlinx.android.synthetic.main.layout_toolbar.*

/**
 * 我的足迹
 */
class FootActivity : BaseActivity() {

    companion object {
        fun actionStart(context: Context?) {
            context?.startActivity(Intent(context, FootActivity::class.java))
        }
    }

    override fun initToolBar() {
        StatusBarUtils.darkMode(
            this,
            ContextCompat.getColor(this, R.color.colorSecondText),
            0.3f
        )

        rightLayout.visibility = View.GONE
        editOperation.visibility = View.VISIBLE
        titleName.text = "浏览记录"
        back.setOnClickListener {
            finish()
        }

        /** 点击编辑按钮 */
        editOperation.setOnClickListener {
            if (!editOperation.isSelected) {
                //编辑状态
                editOperation.isSelected = true
                operationLayout.visibility = View.VISIBLE
                editOperation.text = "完成"
                adapter.setIfCanEdit(true)
            } else {
                //不可编辑状态
                editOperation.isSelected = false
                operationLayout.visibility = View.GONE
                editOperation.text = "编辑"
                adapter.setIfAllChoose(false)
                adapter.setIfCanEdit(false)
                allChoose.isChecked = false
            }
        }

        /** 是否全部选中 */
        allChoose.setOnClickListener {
            adapter.setIfAllChoose(allChoose.isChecked)
        }

        //删除按钮
        delete.setOnClickListener {
            //可以传实体类，存放id和position
            /** 重新请求数据渲染，如果直接移除item需要注意头布局 */
            repeat(adapter.checkList.size) {
                //注意！！！不是removeAt(it)
                days.removeAt(adapter.checkList[it])
            }
            adapter.notifyDataSetChanged()
            showToast("删除：" + adapter.checkList)
        }

    }

    override fun initEvent() {
        adapter.setOnClickListener(object : FootAdapter.OnItemClickListener {
            override fun unCheckAll() {
                allChoose.isChecked = false
            }

            override fun checkAll() {
                allChoose.isChecked = true
            }
        })
    }

    override fun layoutId(): Int = R.layout.activity_foot

    private val adapter by lazy { FootAdapter(this, days) }
    private val decoration by lazy {
        FloatingItemDecoration(
            this, ContextCompat.getColor(this, R.color.colorBackground),
            2f, 12f
        )
    }

    private val keys = HashMap<Int, DateHeadEntity>()
    private var size = 0
    private var long = 0
    private val days = ArrayList<GoodsList>()
    private val monthData = ArrayList<MonthList>()

    override fun initView() {

        recyclerView.addItemDecoration(decoration)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        //刷新初始化假数据
        long = 0
        size = 0
        monthData.clear()
        days.clear()
        keys.clear()
        monthData.addAll(getData())

        size = monthData.size
        for (i in 0 until monthData.size) {
            keys[long] = DateHeadEntity(monthData[i].month, "")
            monthData[i].goodsList.let {
                days.addAll(it)
                long += it.size
            }
        }
        decoration.setKeys(keys)

        decoration.setmTitleHeight(
            TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                50f,
                resources.displayMetrics
            ).toInt()
        )
        adapter.notifyDataSetChanged()
    }

    private fun getData(): ArrayList<MonthList> {
        val list = ArrayList<MonthList>()
        list.add(MonthList("2018/02", arrayListOf(GoodsList("长袖", "99"))))
        list.add(
            MonthList(
                "2018/03", arrayListOf(
                    GoodsList("衬衫", "299"),
                    GoodsList("鞋子", "159")
                )
            )
        )
        return list
    }

    override fun initData() {

    }

    override fun start() {
    }
}