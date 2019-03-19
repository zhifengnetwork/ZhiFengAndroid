package com.zf.mart.ui.activity

import android.content.Context
import android.content.Intent
import android.graphics.drawable.Drawable
import android.view.GestureDetector
import android.view.View
import android.widget.Button
import android.widget.GridView
import android.widget.TextView
import com.zf.mart.MyApplication.Companion.context
import com.zf.mart.R
import com.zf.mart.base.BaseActivity
import com.zf.mart.ui.adapter.RegistrationAdapter
import com.zf.mart.utils.LogUtils
import com.zf.mart.view.gridview.SpecialCalendar
import kotlinx.android.synthetic.main.activity_sign_in_gift.*
import kotlinx.android.synthetic.main.activity_sign_in_gift.view.*
import kotlinx.android.synthetic.main.layout_toolbar.*
import java.text.SimpleDateFormat
import java.util.*

class SigninGiftActivity:BaseActivity(){

    private var registration_calendar_gv: GridView? = null
    private var adapter: RegistrationAdapter? = null
    private val sdf = SimpleDateFormat("yyyy-M-d")
    internal var mYear = 0//年
    internal var mMonth = 0//月
    internal var mDay = 0//日
    internal var mDays: Int = 0
    internal var week: Int = 0
    internal var month: Int = 0
    internal var year: Int = 0


    companion object {
        fun actionStart(context: Context?){
            context?.startActivity(Intent(context,SigninGiftActivity::class.java))
        }
    }

    override fun initToolBar() {
        titleName.text="累计积分"
        back.setOnClickListener {

        }
        rightLayout.visibility= View.INVISIBLE
    }

    override fun layoutId(): Int= R.layout.activity_sign_in_gift

    override fun initData() {

    }

    override fun initView() {
        //已签到按钮图片
        val img=resources.getDrawable(R.drawable.rili)
        img.setBounds(0,2,60,60)//第一0是距左边距离，第二0是距上边距离，70分别是长宽
        signtv.setCompoundDrawables(img,null,null,null)

        //签到日历
        var calendar=Calendar.getInstance()
         mYear=calendar.get(Calendar.YEAR)//获取年份
         year=mYear

         mMonth=calendar.get(Calendar.MONTH)//获取当前月份以（0开头

         month=mMonth

         mDay=calendar.get(Calendar.DAY_OF_MONTH)// 获取当前天以（0开头）

        val mCalendar = SpecialCalendar()
        val isLeapYear = mCalendar.isLeapYear(mYear)//看是否为闰年
        mDays = mCalendar.getDaysOfMonth(isLeapYear, mMonth + 1)//得到当月一共几天
        week = mCalendar.getWeekdayOfMonth(mYear, mMonth)//得到当月第一天星期几

        adapter= RegistrationAdapter(this,mDays,week,mDay)
        calendar_gv.adapter=adapter
        date.text=mYear.toString()+"."+(mMonth+1)

        //左右滑动


    }

    override fun initEvent() {
        //点击事件
        //上一个月
        upmonth.setOnClickListener {
            month--
            if(month>0) {
                val mCalendar = SpecialCalendar()
                val isLeapYear = mCalendar.isLeapYear(year)
                mDays = mCalendar.getDaysOfMonth(isLeapYear, month + 1)//得到当月一共几天
                adapter = RegistrationAdapter(this, mDays, week, mDay)
                calendar_gv.adapter = adapter
                date.text=year.toString()+"."+(month+1)
            }else{
                month=11
                year--
                val mCalendar = SpecialCalendar()
                val isLeapYear = mCalendar.isLeapYear(year)
                mDays = mCalendar.getDaysOfMonth(isLeapYear, month + 1)//得到当月一共几天
                adapter = RegistrationAdapter(this, mDays, week, mDay)
                calendar_gv.adapter = adapter
                date.text=year.toString()+"."+(month+1)
            }

        }

        //下一个月
        downmonth.setOnClickListener {
            month++
            if(month<12){
                val mCalendar = SpecialCalendar()
                val isLeapYear = mCalendar.isLeapYear(year)
                mDays = mCalendar.getDaysOfMonth(isLeapYear, month + 1)//得到当月一共几天
                adapter= RegistrationAdapter(this,mDays,week,mDay)
                calendar_gv.adapter=adapter
                date.text=year.toString()+"."+(month+1)
            }else {
                month=0
                year++
                val mCalendar = SpecialCalendar()
                val isLeapYear = mCalendar.isLeapYear(year)
                mDays = mCalendar.getDaysOfMonth(isLeapYear, month + 1)//得到当月一共几天
                adapter= RegistrationAdapter(this,mDays,week,mDay)
                calendar_gv.adapter=adapter
                date.text=year.toString()+"."+(month+1)
            }


        }

    }

    override fun start() {

    }

}