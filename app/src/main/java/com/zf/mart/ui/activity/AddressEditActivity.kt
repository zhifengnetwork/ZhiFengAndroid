package com.zf.mart.ui.activity

import android.content.Context
import android.content.Intent
import android.text.Editable
import android.text.TextWatcher
import android.view.Gravity
import android.view.View
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import com.bigkoo.pickerview.adapter.ArrayWheelAdapter
import com.zf.mart.R
import com.zf.mart.base.BaseActivity
import com.zf.mart.mvp.bean.AddressBean
import com.zf.mart.showToast
import com.zf.mart.utils.LogUtils
import com.zf.mart.utils.StatusBarUtils
import com.zf.mart.view.popwindow.RegionPopupWindow
import kotlinx.android.synthetic.main.activity_address_edit.*
import kotlinx.android.synthetic.main.layout_toolbar.*
import kotlinx.android.synthetic.main.pop_region.view.*


class AddressEditActivity : BaseActivity() {

    companion object {
        fun actionStart(context: Context?,addressData:Array<String>) {
            val intent=Intent(context, AddressEditActivity::class.java)
            intent.putExtra("address",addressData)
            context?.startActivity(intent)
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

        upinfo()

        initTag()
    }


    //省列表
    private var provinceData = arrayOf<String>()
    private var cityData = arrayOf<String>()
    private var areaData = arrayOf<String>()
    private var townData = arrayOf<String>()


    override fun initEvent() {

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

        addressLayout.setOnClickListener {

            val regionPopWindow = object : RegionPopupWindow(
                this, com.zf.mart.R.layout.pop_region,
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            ) {
                override fun initView() {
                    contentView?.apply {
                        //赋值
                        provincePic.setCyclic(false)
                        cityPic.setCyclic(false)
                        areaPic.setCyclic(false)
                        val provinceData = ArrayList<String>()
                        var cityData = ArrayList<String>()
                        var areaData = ArrayList<String>()
                        //获取省份，通过省份获取城市，获取区域
                        provinceData.addAll(arrayOf("广东省", "湖南省", "福建省", "新疆"))
                        cityData.addAll(arrayOf("广州", "东莞", "湛江"))
                        areaData.addAll(arrayOf("白云", "番禺", "车陂", "东圃", "天河"))
                        provincePic.adapter = ArrayWheelAdapter(provinceData)
                        cityPic.adapter = ArrayWheelAdapter(cityData)
                        areaPic.adapter = ArrayWheelAdapter(areaData)

                        provincePic.setOnItemSelectedListener { index ->
                            /** 省份滑动，通过省份更新城市 */
                            cityData = when (index) {
                                0 -> arrayListOf("广州", "东莞", "湛江")
                                1 -> arrayListOf("湖南城市1", "湖南城市2", "湖南城市3")
                                else -> arrayListOf()
                            }
                            cityPic.adapter = ArrayWheelAdapter(cityData)

                            /** 城市更新后更新区域 */
                            areaData = when (cityPic.currentItem) {
                                0 -> arrayListOf("白云", "番禺", "车陂", "东圃", "天河")
                                1 -> arrayListOf("东莞区1", "东莞区2", "东莞区3")
                                2 -> arrayListOf("雷州", "遂溪", "徐闻")
                                else -> arrayListOf()
                            }
                            areaPic.adapter = ArrayWheelAdapter(areaData)
                        }

                        cityPic.setOnItemSelectedListener { index ->
                            areaData = when (index) {
                                0 -> arrayListOf("白云", "番禺", "车陂", "东圃", "天河")
                                1 -> arrayListOf("东莞区1", "东莞区2", "东莞区3")
                                2 -> arrayListOf("雷州", "遂溪", "徐闻")
                                else -> arrayListOf()
                            }
                            areaPic.adapter = ArrayWheelAdapter(areaData)
                        }

                    }
                }
            }
            regionPopWindow.showAtLocation(parentLayout, Gravity.BOTTOM, 0, 0)

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

    /**
     * 点击地址编辑按钮将传递过来的数据赋值再界面上
     * */
    fun upinfo(){
        val data:Array<String> = intent.getStringArrayExtra("address")

        if(data[0]!=""){
            user_id.setText(data[0])
            user_phone.setText(data[1])
            district.text=data[3]+data[4]+data[5]
            address.setText(data[7])
            if (data[9]=="1"){
                is_default.isChecked=true
            }
        }else{
            user_id.setText("")
            user_phone.setText("")
//            district.text=""
            address.setText("")
            is_default.isChecked=false
        }





    }
}


//旧的三级联动
//            val dialog = CustomAddressDialog(this)
//            dialog.setOnAddressSelectedListener { province, city, country, street ->
//                val mAddress = "${province?.name ?: ""}${city?.name ?: ""}${country?.name ?: ""}${street?.name ?: ""}"
//                address.text = mAddress
//                dialog.dismiss()
//            }
//            dialog.setDialogDismisListener {
//                dialog.dismiss()
//            }
//            dialog.show()