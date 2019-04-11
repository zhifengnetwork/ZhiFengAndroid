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
import kotlinx.android.synthetic.main.pop_region.*


class AddressEditActivity : BaseActivity(),AddressEditContract.View{
    override fun showError(msg: String, errorCode: Int) {

    }

    override fun delAddressSuccess() {
        Log.e("检测","删除操作返回数据成功")
    }

    override fun setAddress(bean: List<AddAddressBean>) {
        Log.e("检测","添加地址返回数据操作成功")
    }

    override fun getRegion(bean: List<RegionBean>) {
        regionData.clear()
        regionData.addAll(bean)
        Log.e("检测","三级列表数据获取成功"+regionData)

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
            switch = 3
            addressEditPresenter.requestRegion(cityID[0])

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
        Log.e("检测","View执行了"+regionData)
       addressEditPresenter.attachView(this)

        upinfo()

        initTag()

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

            Log.e("检测","点击了按钮")

            /**添加地址*/
            if (data[0]==""){
                //选中的tag标签
                val chooseTag = when {
                    homeRb.isSelected -> homeRb.text.toString()
                    companyRb.isSelected -> companyRb.text.toString()
                    schoolRb.isSelected -> schoolRb.text.toString()
                    inputEditText.isSelected -> inputEditText.text.toString()
                    else -> ""
                }
//            showToast(chooseTag)
                    val consignee= user_id.text.toString()
                    val mobile=user_phone.text.toString()
                    val province=district.text.toString()
                    val city=district.text.toString()
                    val district=district.text.toString()
                    val address=address.text.toString()
                    var mis_default="0"

//                    if (is_default.isChecked){
//                        mis_default="1"
//                    }
                 //网络请求
                addressEditPresenter.requestAddressEdit("测试添加","12345678","132","456","111","桃园西街","0")

                Toast.makeText(context,"添加成功",Toast.LENGTH_SHORT).show()
                finish()
            }
                 /** 修改地址*/
            else{
                Toast.makeText(context,"修改地址",Toast.LENGTH_SHORT).show()
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

                    contentView?.apply {
                        //赋值
                        provincePic.setCyclic(false)
                        cityPic.setCyclic(false)
                        areaPic.setCyclic(false)
//                        var provinceData = ArrayList<String>()
//                        var cityData = ArrayList<String>()
//                        var areaData = ArrayList<String>()

//                       if(regionData.size!=0){
//                           provinceData.addAll(arrayOf(regionData[0].name, regionData[1].name, regionData[2].name, regionData[3].name))
//                       }else{
//                           provinceData.addAll(arrayOf("没有更新数据1", "没有更新数据2", "没有更新数据3", "没有更新数据4"))
//                       }


//                        cityData.addAll(arrayOf("广州", "东莞", "湛江"))

//                        areaData.addAll(arrayOf("白云", "番禺", "车陂", "东圃", "天河"))
                        provincePic.adapter = provincAdapter
                        cityPic.adapter = cityAdapter
                        areaPic.adapter = areaAdapter

                        provincePic.setOnItemSelectedListener { index ->
                            /** 省份滑动，通过省份更新城市 */

                            switch = 2
                            addressEditPresenter.requestRegion(provinceID[index])

//                            cityData = when (index) {
//                                0 -> arrayListOf("广州", "东莞", "湛江"),
//                                1 -> arrayListOf("湖南城市1", "湖南城市2", "湖南城市3")
//                                else -> arrayListOf()
//                            }
//                            cityPic.adapter = ArrayWheelAdapter(cityData)



//                            areaData = when (cityPic.currentItem) {
//                                0 -> arrayListOf("白云", "番禺", "车陂", "东圃", "天河")
//                                1 -> arrayListOf("东莞区1", "东莞区2", "东莞区3")
//                                2 -> arrayListOf("雷州", "遂溪", "徐闻")
//                                else -> arrayListOf()
//                            }
//                            areaPic.adapter = ArrayWheelAdapter(areaData)
                        }

                        cityPic.setOnItemSelectedListener { index ->

                            switch = 3
                            addressEditPresenter.requestRegion(cityID[index])
//                            switch = 3
//                            regionData.clear()
//                            areaData.clear()
//                            areaID.clear()
//                            addressEditPresenter.requestRegion(cityID[index])
//                            areaData = when (index) {
//                                0 -> arrayListOf("白云", "番禺", "车陂", "东圃", "天河")
//                                1 -> arrayListOf("东莞区1", "东莞区2", "东莞区3")
//                                2 -> arrayListOf("雷州", "遂溪", "徐闻")
//                                else -> arrayListOf()
//                            }
//                            areaPic.adapter = ArrayWheelAdapter(areaData)
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
            district.text=data[4]+data[5]+data[6]
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