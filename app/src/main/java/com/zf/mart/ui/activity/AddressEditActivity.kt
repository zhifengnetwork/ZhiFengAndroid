package com.zf.mart.ui.activity

import android.content.Context
import android.content.Intent
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.core.content.ContextCompat
import com.zf.mart.R
import com.zf.mart.base.BaseActivity
import com.zf.mart.showToast
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
        initTag()
    }

    override fun initEvent() {
        //选择地址 修改选中图标在item_area
        addressLayout.setOnClickListener {
            val dialog = CustomAddressDialog(this)
            dialog.setOnAddressSelectedListener { province, city, country, street ->
                val mAddress = "${province?.name ?: ""}${city?.name ?: ""}${country?.name ?: ""}${street?.name ?: ""}"
                address.text = mAddress
                dialog.dismiss()
            }
            dialog.setDialogDismisListener {
                dialog.dismiss()
            }
            dialog.show()
        }

        confirm.setOnClickListener {
            //选中的tag标签
            val chooseTag = when {
                homeRb.isSelected -> homeRb.text.toString()
                companyRb.isSelected -> companyRb.text.toString()
                schoolRb.isSelected -> schoolRb.text.toString()
                inputEditText.isSelected -> inputEditText.text.toString()
                else -> ""
            }
            showToast(chooseTag)
        }

    }

    override fun start() {
    }

    //地址标签
    private fun initTag() {
        homeRb.setOnClickListener {
            companyRb.isSelected = false
            schoolRb.isSelected = false
            customRb.isSelected = false
            inputEditText.isSelected = false
            unSelectedCustom()
            homeRb.isSelected = !homeRb.isSelected
            closeKeyBord(inputEditText, this)
        }
        companyRb.setOnClickListener {
            homeRb.isSelected = false
            schoolRb.isSelected = false
            customRb.isSelected = false
            inputEditText.isSelected = false
            unSelectedCustom()
            companyRb.isSelected = !companyRb.isSelected
            closeKeyBord(inputEditText, this)
        }
        schoolRb.setOnClickListener {
            homeRb.isSelected = false
            companyRb.isSelected = false
            customRb.isSelected = false
            inputEditText.isSelected = false
            unSelectedCustom()
            schoolRb.isSelected = !schoolRb.isSelected
            closeKeyBord(inputEditText, this)
        }
        customRb.setOnClickListener {
            customRb.visibility = View.GONE
            editTagLayout.visibility = View.VISIBLE
            editTextView.isSelected = true
            //获取焦点并且显示键盘
            inputEditText.requestFocus()
            openKeyBord(inputEditText, this)
        }

        inputEditText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                editTextView.isSelected = s.isNotEmpty()
                editTextView.setTextColor(
                    if (s.isEmpty()) ContextCompat.getColor(this@AddressEditActivity, R.color.colorThirdText)
                    else ContextCompat.getColor(this@AddressEditActivity, R.color.colorPrimaryText)
                )
            }
        })

        inputEditText.setOnClickListener {
            if (editTextView.isSelected) {
                inputEditText.isSelected = false
            } else {
                homeRb.isSelected = false
                companyRb.isSelected = false
                customRb.isSelected = false
                schoolRb.isSelected = false
                inputEditText.isSelected = !inputEditText.isSelected
            }
        }

        //编辑、完成
        editTextView.setOnClickListener {
            if (editTextView.isSelected) {
                if (inputEditText.text.isNotEmpty()) {
                    homeRb.isSelected = false
                    companyRb.isSelected = false
                    customRb.isSelected = false
                    schoolRb.isSelected = false
                    editTextView.text = "编辑"
                    inputEditText.isFocusable = false
                    inputEditText.isFocusableInTouchMode = false
                    inputEditText.isSelected = true
                    editTextView.isSelected = !editTextView.isSelected
                    //关闭键盘
                    closeKeyBord(inputEditText, this)
                }
            } else {
                editTextView.text = "完成"
                inputEditText.isFocusable = true
                inputEditText.isFocusableInTouchMode = true
                inputEditText.isSelected = false
                editTextView.isSelected = !editTextView.isSelected
                inputEditText.requestFocus()
                //打开键盘
                openKeyBord(inputEditText, this)
            }
        }
    }

    private fun unSelectedCustom() {
        if (inputEditText.text.isEmpty()) {
            editTagLayout.visibility = View.GONE
            customRb.visibility = View.VISIBLE
        } else {
            inputEditText.isFocusable = false
            inputEditText.isFocusableInTouchMode = false
            editTextView.isSelected = false
            editTextView.text = "编辑"
        }
    }
}