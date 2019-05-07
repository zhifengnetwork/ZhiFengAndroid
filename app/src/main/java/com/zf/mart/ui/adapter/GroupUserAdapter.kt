package com.zf.mart.ui.adapter

import android.content.Context
import android.os.CountDownTimer
import android.util.SparseArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.util.forEach
import androidx.recyclerview.widget.RecyclerView
import com.zf.mart.R
import com.zf.mart.mvp.bean.GroupMemberList
import com.zf.mart.utils.GlideUtils
import com.zf.mart.utils.LogUtils
import com.zf.mart.utils.TimeUtils
import kotlinx.android.synthetic.main.item_group_user.view.*

/**
 * 拼团 用户列表
 */
class GroupUserAdapter(val context: Context, val data: List<GroupMemberList>) : RecyclerView.Adapter<GroupUserAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_group_user, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = data.size

    private val countDownMap = SparseArray<CountDownTimer>()

    fun finishCountDown() {
        LogUtils.e(">>>>>size1:" + countDownMap.size())
        countDownMap.forEach { _, value ->
            value.cancel()
        }
    }

    var onItemClickListener: ((String) -> Unit)? = null

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.apply {
            userName.text = data[position].nickname
            GlideUtils.loadUrlImage(context, data[position].head_pic, userIcon)
            discount.text = "还差${data[position].need - data[position].join}人拼成"

            LogUtils.e(">>>>>size2:" + countDownMap.size())

            addGroup.setOnClickListener {
                onItemClickListener?.invoke(data[position].found_id)
            }


            //倒计时
            if ((data[position].found_time * 1000 > System.currentTimeMillis())) {
                downTime.text = "${TimeUtils.auctionTime(data[position].found_time)}准时开始"
            } else if ((data[position].found_time * 1000 < System.currentTimeMillis())
                    && (data[position].found_end_time * 1000 > System.currentTimeMillis())
            ) {
                holder.countDownTimer?.cancel()
                val time: Long = (data[position].found_end_time * 1000) - System.currentTimeMillis()
                holder.countDownTimer = object : CountDownTimer((time), 1000) {
                    override fun onFinish() {
                    }

                    override fun onTick(millisUntilFinished: Long) {
                        downTime.text = "剩余${TimeUtils.getCountTime2(millisUntilFinished)}"
                        LogUtils.e(">>>>>tick:" + TimeUtils.getCountTime2(millisUntilFinished))
                    }
                }.start()
                countDownMap.put(position, holder.countDownTimer)
                LogUtils.e(">>>>>size3:" + countDownMap.size())
            } else {
                downTime.text = "活动已结束"
            }
        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var countDownTimer: CountDownTimer? = null
    }

}