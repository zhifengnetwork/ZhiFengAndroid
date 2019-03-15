package com.zf.mart.view.calendar

import android.content.Context
import android.graphics.Canvas
import com.haibin.calendarview.Calendar
import com.haibin.calendarview.MonthView
import com.zf.mart.MyApplication.Companion.context

class MeiZuMonthView: MonthView(context) {
    override fun onDrawText(
        canvas: Canvas?,
        calendar: Calendar?,
        x: Int,
        y: Int,
        hasScheme: Boolean,
        isSelected: Boolean
    ) {

    }

    override fun onDrawSelected(canvas: Canvas?, calendar: Calendar?, x: Int, y: Int, hasScheme: Boolean): Boolean {
        return true
        //这里绘制选中的日子样式，看需求需不需要继续调用onDrawSchemeeturn true
    }
    /**
     * 绘制标记的事件日子
     *
     * @param canvas   canvas
     * @param calendar 日历calendar
     * @param x        日历Card x起点坐标
     * @param y        日历Card y起点坐标
     */
    override fun onDrawScheme(canvas: Canvas?, calendar: Calendar?, x: Int, y: Int) {
//这里绘制标记的日期样式，想怎么操作就怎么操作
    }

}