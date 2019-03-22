package com.zf.mart.ui.activity

import android.content.Context
import android.content.Intent
import androidx.core.content.ContextCompat
import com.zf.mart.R
import com.zf.mart.base.BaseActivity
import com.zf.mart.utils.StatusBarUtils
import com.zf.mart.view.dialog.CustomAddressDialog
import kotlinx.android.synthetic.main.activity_address_edit.*
import kotlinx.android.synthetic.main.layout_toolbar.*

class AddressEditActivity : BaseActivity() {

    companion object {
        fun actionStart(context: Context?) {
            context?.startActivity(Intent(context, AddressEditActivity::class.java))
        }
    }

    override fun initToolBar() {
        StatusBarUtils.darkMode(this, ContextCompat.getColor(this, R.color.colorSecondText), 0.3f)
        back.setOnClickListener { finish() }
        titleName.text = "编辑收货人"
        rightIcon.setImageResource(R.drawable.ic_delete)
    }

    override fun layoutId(): Int = R.layout.activity_address_edit

    override fun initData() {
    }

    override fun initView() {
    }

    override fun initEvent() {
        //选择地址 修改选中图标在item_area
        addressLayout.setOnClickListener {
            val dialog = CustomAddressDialog(this)
            dialog.setOnAddressSelectedListener { province, city, country, street ->
                val mAddress = if (province?.name == null) "" else province.name +
                        if (city?.name == null) "" else city.name +
                                if (country?.name == null) "" else country.name +
                                        if (street?.name == null) "" else street.name
                address.text = mAddress

                dialog.dismiss()
            }
            dialog.setDialogDismisListener {
                dialog.dismiss()
            }
            dialog.show()

        }
    }

    override fun start() {
    }
}