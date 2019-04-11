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
import com.zf.mart.utils.StatusBarUtils
import com.zf.mart.view.popwindow.RegionPopupWindow
import kotlinx.android.synthetic.main.activity_address_edit.*
import kotlinx.android.synthetic.main.layout_toolbar.*
import kotlinx.android.synthetic.main.pop_region.view.*
import android.app.AlertDialog
import android.content.DialogInterface
import android.util.Log
import android.widget.Toast
import com.zf.mart.MyApplication.Companion.context
import com.zf.mart.mvp.bean.AddAddressBean
import com.zf.mart.mvp.bean.RegionBean
import com.zf.mart.mvp.contract.AddressEditContract
import com.zf.mart.mvp.presenter.AddressEditPresenter
import android.text.TextUtils
import com.zf.mart.mvp.bean.EditAddressBean


class AddressEditActivity : BaseActivity(),AddressEditContract.View{
    override fun deitAddress(bean: EditAddressBean) {

    }

    override fun setAddress(bean: AddAddressBean) {

    }

    override fun showError(msg: String, errorCode: Int) {

    }

    override fun delAddressSuccess() {

    }

    override fun getRegion(bean: List<RegionBean>) {
        regionData.clear()
        regionData.addAll(bean)

       //获取省份，通过省份获取城市，获取区域
        if(switch==1) {
            //获取省份
            for (i in regionData.indices) {
                //储存省名字
                provinceData.add(regionData[i].name)
                //储存省ID
                provinceID.add(regionData[i].id)
            }
        }else if (switch==2) {
            cityID.clear()
            cityData.clear()
            //获取 市
            if(regionData.size!=0){
                for (i in regionData.indices) {
                    //储存 市ID
                    cityID.add(regionData[i].id)
                    //储存 市名字
                    cityData.add(regionData[i].name)
                }
            }
            cityAdapter=ArrayWheelAdapter(cityData)

            /** 城市更新后更新区域 */

            if(cityID.size!=0){
                switch = 3
                addressEditPresenter.requestRegion(cityID[0])
            }else{
                areaData.clear()
                areaID.clear()
            }
            regionPopWindow.updata()

        }else if (switch==3){
            areaData.clear()
            areaID.clear()
            //获取 区
            for (i in regionData.indices){
                //储存 市ID
                areaID.add(regionData[i].id)
                //储存 市名字
                areaData.add(regionData[i].name)
            }
            areaAdapter=ArrayWheelAdapter(areaData)
            regionPopWindow.updata()
        }

    }

    override fun showLoading() {

    }

    override fun dismissLoading() {

    }


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
    //接收传递过来的用户地址信息
    private var data:Array<String> = emptyArray()

    //接收三级联动信息
    private val regionData = ArrayList<RegionBean>()



    private val addressEditPresenter by lazy { AddressEditPresenter() }




    override fun layoutId(): Int = R.layout.activity_address_edit

    override fun initData() {
    }
    override fun initView() {

       addressEditPresenter.attachView(this)

        upinfo()

        initTag()

       //添加地址 隐藏删除图标
        if(data[0]==""){
            rightIcon.visibility=View.GONE
        }

    }


    /**省 市 区 列表*/
//    private var provinceData = arrayOf<String>()
//    private var cityData = arrayOf<String>()
//    private var areaData = arrayOf<String>()
//    private var townData = arrayOf<String>()
    private var provinceData = ArrayList<String>()

    private var provinceID = ArrayList<String>()
    private var cityData = ArrayList<String>()
    private var cityID = ArrayList<String>()
    private var areaData = ArrayList<String>()
    private var areaID = ArrayList<String>()

    private lateinit var regionPopWindow:RegionPopupWindow

    private var switch = 1

    private var provincAdapter=ArrayWheelAdapter(provinceData)

    private var cityAdapter=ArrayWheelAdapter(cityData)

    private var areaAdapter=ArrayWheelAdapter(areaData)




    override fun initEvent() {
       /**点击删除地址*/
        rightLayout.setOnClickListener {

            val builder = AlertDialog.Builder(this)
            builder.setMessage("确定要删除该地址吗?")
            builder.setPositiveButton("确定",DialogInterface.OnClickListener { dialog, which ->
                //网络请求
                addressEditPresenter.requestDelAddress(data[0])
                finish()
            })
            builder.setNegativeButton("取消",null)
            builder.show()

        }

        /**点击确认 添加地址或修改地址*/
        confirm.setOnClickListener {

            /**添加地址*/
            if (data[0]==""){
                //选中的tag标签
//                val chooseTag = when {
//                    homeRb.isSelected -> homeRb.text.toString()
//                    companyRb.isSelected -> companyRb.text.toString()
//                    schoolRb.isSelected -> schoolRb.text.toString()
//                    inputEditText.isSelected -> inputEditText.text.toString()
//                    else -> ""
//                }
//            showToast(chooseTag)
                    val mConsignee =user_id.text.toString()
                    val mMobile=user_phone.text.toString()
                    var mProvince=""
                    var mCity=""
                    var mDistrict=""
                    val mAddress=address.text.toString()
                    var mIs_default="0"
                    if (is_default.isChecked){
                        mIs_default="1"
                    }else{
                        mIs_default="0"
                    }

                /**判断 省 市 区 的ID*/
                for(i in provinceData.indices){
                     if(province.text==provinceData[i]){
                         mProvince=provinceID[i]
                     }
                }
                for(i in cityData.indices){
                    if(city.text==cityData[i]){
                        mCity=cityID[i]
                    }
                }
                for(i in areaData.indices){
                    if(area.text==areaData[i]){
                        mDistrict=areaID[i]
                    }
                }
               /**判断用户输入信息是否规范*/
                if (mConsignee.length<2){
                        Toast.makeText(context,"请输入姓名2-25个字符",Toast.LENGTH_SHORT).show()
                }else if (!isMobileNO(mMobile)){
                    Toast.makeText(context,"请正确输入11位手机号码",Toast.LENGTH_SHORT).show()
                }else if(province.text.isEmpty()){
                    Toast.makeText(context,"请选择地区",Toast.LENGTH_SHORT).show()
                }else if (mAddress.length<5){
                    Toast.makeText(context,"请输入详细地址5-120个字符",Toast.LENGTH_SHORT).show()
                }
                else{
                    //网络请求
                    addressEditPresenter.requestAddressEdit(mConsignee,mMobile,mProvince,mCity,mDistrict,mAddress,mIs_default)

                    Toast.makeText(context,"添加成功",Toast.LENGTH_SHORT).show()
                    finish()
                }

            }
                 /** 修改地址*/
            else{

                //获得界面信息
                //选中的tag标签
                val chooseTag = when {
                    homeRb.isSelected -> homeRb.text.toString()
                    companyRb.isSelected -> companyRb.text.toString()
                    schoolRb.isSelected -> schoolRb.text.toString()
                    inputEditText.isSelected -> inputEditText.text.toString()
                    else -> ""
                }
//            showToast(chooseTag)

                val mConsignee =user_id.text.toString()
                val mMobile=user_phone.text.toString()
                var mProvince=data[4]
                var mCity=data[5]
                var mDistrict=data[6]
                val mAddress=address.text.toString()
                var mIs_default=""
                if (is_default.isChecked){
                    mIs_default="1"
                }else{
                    mIs_default="0"
                }
                addressEditPresenter.requestDeitAddress(data[0],mConsignee,mMobile,mProvince,mCity,mDistrict,mAddress,chooseTag,mIs_default)
                Toast.makeText(context,"修改成功",Toast.LENGTH_SHORT).show()
                finish()
            }

        }

        addressLayout.setOnClickListener {

            //初始页面数据 保证打开页面 三级联动有数据显示
            switch = 2
            addressEditPresenter.requestRegion(provinceID[0])


              regionPopWindow = object : RegionPopupWindow(
                this, R.layout.pop_region,
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            ) {
                override fun initView() {
                    provincAdapter.getItem(2)
                    contentView?.apply {
                        //赋值
                        provincePic.setCyclic(false)
                        cityPic.setCyclic(false)
                        areaPic.setCyclic(false)
                        provincePic.adapter = provincAdapter
                        cityPic.adapter = cityAdapter
                        areaPic.adapter = areaAdapter
                        provincePic.setOnItemSelectedListener { index ->
                            /** 省份滑动，通过省份更新城市 */

                            switch = 2
                            addressEditPresenter.requestRegion(provinceID[index])
                            province.text=provinceData[index]


                        }

                        cityPic.setOnItemSelectedListener { index ->
                           /**市滑动 更新区**/

                            if(cityID.size!=0){
                                switch = 3
                                addressEditPresenter.requestRegion(cityID[index])
                                city.text=cityData[index]
                            }else{
                                city.text=""
                                area.text=""
                            }


                        }
                        areaPic.setOnItemSelectedListener { index ->
                            /**区滑动**/
                            if(areaData.size!=0){
                                area.text=areaData[index]
                            }

                        }
                    }
                }

            }
            regionPopWindow.updata()
            regionPopWindow.showAtLocation(parentLayout, Gravity.BOTTOM, 0, 0)

        }


    }

    override fun onDestroy() {
        super.onDestroy()
       addressEditPresenter.detachView()


    }
    override fun start() {

        /**三级联动的请求*/
        addressEditPresenter.requestRegion("")

    }

    //判断手机号码格式是否正确
    fun isMobileNO(mobiles:String): Boolean {
        val telRegex = "[1][34578]\\d{9}"//"[1]"代表第1位为数字1，"[358]"代表第二位可以为3、5、8中的一个，"\\d{9}"代表后面是可以是0～9的数字，有9位。
        if(TextUtils.isEmpty(mobiles)){
            return false
        }
        else return mobiles.matches(telRegex.toRegex())
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
         data = intent.getStringArrayExtra("address")

        if(data[0]!=""){
            user_id.setText(data[1])
            user_phone.setText(data[2])
            province.text=data[4]+data[5]+data[6]
            address.setText(data[8])
            if (data[10]=="1"){
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