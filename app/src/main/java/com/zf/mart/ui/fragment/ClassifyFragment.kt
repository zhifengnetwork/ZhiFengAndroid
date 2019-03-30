package com.zf.mart.ui.fragment


import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.provider.Settings
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.PagerAdapter
import com.uuzuche.lib_zxing.activity.CaptureActivity
import com.uuzuche.lib_zxing.activity.CodeUtils
import com.uuzuche.lib_zxing.activity.ZXingLibrary
import com.zf.mart.R
import com.zf.mart.base.BaseFragment
import com.zf.mart.ui.activity.SearchActivity
import com.zf.mart.ui.adapter.ClassifyPagerAdapter
import com.zf.mart.ui.adapter.ClassifyTitleAdapter
import com.zf.mart.view.verticalViewPager.DefaultTransformer
import kotlinx.android.synthetic.main.fragment_classify.*
import kotlinx.android.synthetic.main.layout_classify_title.*
import com.zf.mart.ui.activity.MainActivity
import com.zf.mart.ui.activity.ScanActivity
import com.zf.mart.utils.ImageUtil
import pub.devrel.easypermissions.AfterPermissionGranted
import pub.devrel.easypermissions.AppSettingsDialog
import pub.devrel.easypermissions.EasyPermissions


class ClassifyFragment : BaseFragment(),EasyPermissions.PermissionCallbacks {


    companion object {
        fun getInstance(): ClassifyFragment {
            return ClassifyFragment()
        }
    }

    override fun getLayoutId(): Int = com.zf.mart.R.layout.fragment_classify

    private val data = ArrayList<String>()

    private val adapter by lazy { ClassifyTitleAdapter(context, data) }

    private var mPagerAdapter: PagerAdapter? = null
   //扫描跳转Activity RequestCode
    private val REQUEST_CODE=111
    //选择系统图片Request Code
    private val REQUEST_IMAGE = 112
    //需要申请的限权
    private val permissions:Array<String> = arrayOf(
        Manifest.permission.WRITE_EXTERNAL_STORAGE,
        Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.CAMERA)
    //限权请求码
    private val REQUEST_CAMERA_PERM = 101
    //控制限权请求初始化
    private var isopen:Boolean=true
    private val lefttitle: Array<String> = arrayOf(
        "为您推荐",
        "品牌墙",
        "美容彩妆",
        "奶粉纸尿裤",
        "外部专区",
        "轻奢",
        "个人护理",
        "手表配饰",
        "数码家电",
        "家居生活",
        "环球美食",
        "服饰靴鞋",
        "家用家电",
        "医药保健",
        "汽车生活"
    )

    override fun initView() {

        /** 左边分类 */
        for (i in lefttitle.iterator()) {

            data.add(i)

        }


        leftRecyclerView.layoutManager = LinearLayoutManager(context)
        leftRecyclerView.adapter = adapter

        //给右边的viewpager设置adapter

        mPagerAdapter=ClassifyPagerAdapter(childFragmentManager,lefttitle)
        rightViewPager.setPageTransformer(true, DefaultTransformer())
        rightViewPager.setNoScroll(true)
        rightViewPager.adapter=mPagerAdapter

        //扫描二维码初始化
        ZXingLibrary.initDisplayOpinion(context)

    }

    override fun lazyLoad() {
    }

    override fun initEvent() {

        //搜索框
        searchLayout.setOnClickListener {
            SearchActivity.actionStart(context, "")
        }

        //左边适配器点击事件
        adapter.setOnItemClickListener(object : ClassifyTitleAdapter.OnItemClickListener {
            override fun onItemClick(position: Int) {
                //分类右边页面跳转
                rightViewPager.currentItem = position
                //分类左边选中item字体变化
                adapter.setThisPosition(position)
                //更新适配器
                adapter.notifyDataSetChanged()
            }
        })

        //扫描二维码(默认界面)
        scan.setOnClickListener {
            //Android 6.0以上动态申请限权
            if(isopen){
                initPermission()//初始化限权
                isopen=false
            }
            if(EasyPermissions.hasPermissions(context, Manifest.permission.CAMERA)){
                //动态权限申请成功转跳页面
                val intent = Intent(context, ScanActivity::class.java)
                startActivityForResult(intent, REQUEST_CODE)
            }else{
                EasyPermissions.requestPermissions(this,
                    "需要请求camera权限",
                    REQUEST_CAMERA_PERM, Manifest.permission.CAMERA
                )
            }

        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        //判断扫描解析结果
         if(requestCode==REQUEST_CODE){
             if(null!=data){
                  val bundle=data.extras
                 if(bundle==null){
                      return
                 }
                 if(bundle.getInt(CodeUtils.RESULT_TYPE)==CodeUtils.RESULT_SUCCESS){
                      val result=bundle.getString(CodeUtils.RESULT_STRING)
                    Toast.makeText(context,"解析结果"+result,Toast.LENGTH_LONG).show()
                 }else if(bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_FAILED){
                     Toast.makeText(context,"解析失败",Toast.LENGTH_LONG).show()
                 }
             }
         } /**
          * 选择系统图片并解析
          */
//          else if(requestCode == REQUEST_IMAGE){
//             if(data!=null){
//                val uri=data.data
//                try{
//                    CodeUtils.analyzeBitmap(
//                        ImageUtil.getImageAbsolutePath(context, uri),
//                        object : CodeUtils.AnalyzeCallback {
//                            override fun onAnalyzeSuccess(mBitmap: Bitmap, result: String) {
//                                Toast.makeText(context, "解析结果:$result", Toast.LENGTH_LONG).show()
//                            }
//
//                            override fun onAnalyzeFailed() {
//                                Toast.makeText(context, "解析二维码失败", Toast.LENGTH_LONG).show()
//                            }
//                        })
//                }catch (e:Exception){
//                     e.printStackTrace()
//                }
//             }
//         }
          else if(requestCode == REQUEST_CAMERA_PERM){
             Toast.makeText(context, "从设置页面返回...", Toast.LENGTH_SHORT).show()
         }


    }

//初始化限权事件
    fun initPermission() {
        //检测权限
        val data = ArrayList<String>()//存储未申请的权限

        for (permission in permissions.iterator()){
            val checkSelfPermission = ContextCompat.checkSelfPermission(context!!, permission)
            if (checkSelfPermission == PackageManager.PERMISSION_DENIED) {//未申请
                data.add(permission)
            }
        }
       val permissions:Array<String> = data.toTypedArray()
       if(permissions.size == 0){
                //权限都申请了
       }else{
           //申请权限
           ActivityCompat.requestPermissions(context as Activity,permissions,100)
       }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        EasyPermissions.onRequestPermissionsResult(requestCode,permissions,grantResults,this)
    }


    @AfterPermissionGranted(101)
    override fun onPermissionsDenied(requestCode: Int, perms: MutableList<String>?) {

        Toast.makeText(context, "执行onPermissionsDenied()...", Toast.LENGTH_SHORT).show()
        if(EasyPermissions.somePermissionPermanentlyDenied(this, perms)){
            AppSettingsDialog.Builder(this,"此功能需要相机限权")
                .setTitle("限权申请")
                .setPositiveButton("确认")
                .setNegativeButton("取消",null)
                .setRequestCode(REQUEST_CAMERA_PERM)
                .build()
                .show()
        }
    }

    override fun onPermissionsGranted(requestCode: Int, perms: MutableList<String>?) {
        Toast.makeText(context, "执行onPermissionsGranted()...", Toast.LENGTH_SHORT).show()
    }


}