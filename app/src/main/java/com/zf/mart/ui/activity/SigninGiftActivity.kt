package com.zf.mart.ui.activity

import android.content.Context
import android.content.Intent
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import android.widget.Toast
import com.zf.mart.MyApplication.Companion.context
import com.zf.mart.R
import com.zf.mart.base.BaseActivity
import com.zf.mart.ui.adapter.RegistrationAdapter
import com.zf.mart.view.gridview.SpecialCalendar
import kotlinx.android.synthetic.main.activity_sign_in_gift.*
import kotlinx.android.synthetic.main.layout_toolbar.*
import java.text.SimpleDateFormat
import java.util.*

class SigninGiftActivity:BaseActivity() {
    private var mDetector: GestureDetector?=null
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

        adapter= RegistrationAdapter(this,mDays,week,mDay,year,month)
        calendar_gv.adapter=adapter
        date.text=mYear.toString()+"."+(mMonth+1)

        //左右滑动
        mDetector = GestureDetector(this, MyGesture())
        calendar_gv.setLongClickable(true)



    }

    override fun initEvent() {
        //点击事件
        //上一个月
        upmonth.setOnClickListener {

            upmonth()
        }

        //下一个月
        downmonth.setOnClickListener {

            downmoth()
        }

        //左右滑动
        calendar_gv.setOnTouchListener(View.OnTouchListener { view, motionEvent ->
            mDetector!!.onTouchEvent(
                motionEvent
            )
        })

    }

    override fun start() {

    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        if(mDetector != null){
            return mDetector!!.onTouchEvent(event)
        }
        return super.onTouchEvent(event)
    }

    //下一个月方法
    fun downmoth(){
        month++
        if(month<12){
            val mCalendar = SpecialCalendar()
            val isLeapYear = mCalendar.isLeapYear(year)
            mDays = mCalendar.getDaysOfMonth(isLeapYear, month + 1)//得到当月一共几天
            week = mCalendar.getWeekdayOfMonth(year, month)//得到当月第一天星期几
            adapter= RegistrationAdapter(this,mDays,week,mDay,year,month)
            calendar_gv.adapter=adapter
            date.text=year.toString()+"."+(month+1)
        }else {
            month=0
            year++
            val mCalendar = SpecialCalendar()
            val isLeapYear = mCalendar.isLeapYear(year)
            mDays = mCalendar.getDaysOfMonth(isLeapYear, month + 1)//得到当月一共几天
            week = mCalendar.getWeekdayOfMonth(year, month)//得到当月第一天星期几
            adapter= RegistrationAdapter(this,mDays,week,mDay,year,month)
            calendar_gv.adapter=adapter
            date.text=year.toString()+"."+(month+1)
        }

    }
    //上一个月实现方法
    fun upmonth(){

        month--
        if(month>0) {
            val mCalendar = SpecialCalendar()
            val isLeapYear = mCalendar.isLeapYear(year)
            mDays = mCalendar.getDaysOfMonth(isLeapYear, month + 1)//得到当月一共几天
            week = mCalendar.getWeekdayOfMonth(year, month)//得到当月第一天星期几
            adapter = RegistrationAdapter(this, mDays, week, mDay,year,month)
            calendar_gv.adapter = adapter
            date.text=year.toString()+"."+(month+1)
        }else{
            month=11
            year--
            val mCalendar = SpecialCalendar()
            val isLeapYear = mCalendar.isLeapYear(year)
            mDays = mCalendar.getDaysOfMonth(isLeapYear, month + 1)//得到当月一共几天
            week = mCalendar.getWeekdayOfMonth(year, month)//得到当月第一天星期几
            adapter = RegistrationAdapter(this, mDays, week, mDay,year,month)
            calendar_gv.adapter = adapter
            date.text=year.toString()+"."+(month+1)
        }

    }

    inner class MyGesture : GestureDetector.OnGestureListener {

        override fun onDown(motionEvent: MotionEvent): Boolean {
            return true
        }

        override fun onShowPress(motionEvent: MotionEvent) {

        }

        override fun onSingleTapUp(motionEvent: MotionEvent): Boolean {
            return false
        }

        override fun onScroll(motionEvent: MotionEvent, motionEvent1: MotionEvent, v: Float, v1: Float): Boolean {
            return false
        }

        override fun onLongPress(motionEvent: MotionEvent) {

        }

        override fun onFling(e1: MotionEvent, e2: MotionEvent, velocityX: Float, velocityY: Float): Boolean {
            if (e1.x - e2.x > 80 && Math.abs(velocityX) > 200) {
                downmoth()

                return true
            } else if (e2.x - e1.x > 80 && Math.abs(velocityX) > 200) {
                upmonth()

                return true
            }
            return false
        }
    }
}