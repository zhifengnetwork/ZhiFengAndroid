package com.zf.mart.ui.activity

import android.content.Context
import android.content.Intent
import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.zf.mart.R
import com.zf.mart.base.BaseActivity
import com.zf.mart.ui.adapter.TeamAdapter
import com.zf.mart.utils.StatusBarUtilNotUse
import kotlinx.android.synthetic.main.activity_team.*
import kotlinx.android.synthetic.main.layout_toolbar.*

/**
 * 我的团队
 */
class TeamActivity : BaseActivity() {

    companion object {
        fun actionStart(context: Context?) {
            context?.startActivity(Intent(context, TeamActivity::class.java))
        }
    }

    override fun initToolBar() {

        StatusBarUtilNotUse.darkMode(this, ContextCompat.getColor(this, R.color.colorSecondText), 0.3f)

        back.setOnClickListener {
            finish()
        }
        titleName.text = "我的团队业绩"
        rightLayout.visibility = View.INVISIBLE

    }

    override fun layoutId(): Int = R.layout.activity_team

    override fun initData() {
    }

    private val adapter by lazy { TeamAdapter(this) }

    override fun initView() {

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

    }

    override fun initEvent() {
    }

    override fun start() {
    }

}