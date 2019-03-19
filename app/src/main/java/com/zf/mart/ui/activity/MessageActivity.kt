package com.zf.mart.ui.activity

import android.content.Context
import android.content.Intent
import android.view.View
import androidx.fragment.app.FragmentTransaction
import com.flyco.tablayout.listener.CustomTabEntity
import com.flyco.tablayout.listener.OnTabSelectListener
import com.zf.mart.R
import com.zf.mart.base.BaseActivity
import com.zf.mart.mvp.bean.TabEntity
import com.zf.mart.ui.fragment.MessageFragment
import kotlinx.android.synthetic.main.activity_message.*
import kotlinx.android.synthetic.main.layout_toolbar.*

class MessageActivity:BaseActivity(){
    private var mMessageFragment: MessageFragment? = null
    private val mTabEntities = ArrayList<CustomTabEntity>()
    private val mTitles = arrayOf("消息", "公告")
    private val mIconUnSelectIds = intArrayOf(
        R.drawable.ic_sy,
        R.drawable.ic_fl

    )

    private val mIconSelectIds = intArrayOf(
        R.drawable.ic_sy_se,
        R.drawable.ic_fl_se

    )
    companion object {
        fun actionStart(context: Context?){
            context?.startActivity(Intent(context,MessageActivity::class.java))
        }
    }
    override fun initToolBar() {
        titleName.text="站内信息"
        back.setOnClickListener {

        }
        rightLayout.visibility= View.INVISIBLE
    }

    override fun layoutId(): Int = R.layout.activity_message

    override fun initData() {

    }

    override fun initView() {

    }

    override fun initEvent() {

    }

    override fun start() {

    }




    }
