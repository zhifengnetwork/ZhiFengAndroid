package com.zf.mart.ui.activity

import android.app.Activity
import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.Gravity
import android.view.View
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import com.yanzhenjie.durban.Controller
import com.yanzhenjie.durban.Durban
import com.zf.mart.R
import com.zf.mart.api.UriConstant
import com.zf.mart.base.BaseActivity
import com.zf.mart.base.BaseBean
import com.zf.mart.livedata.UserInfoLiveData
import com.zf.mart.mvp.bean.ChangeUserBean
import com.zf.mart.mvp.bean.UserInfoBean
import com.zf.mart.mvp.contract.UpdateUserInfoContract
import com.zf.mart.mvp.contract.UserInfoContract
import com.zf.mart.mvp.presenter.UpdateUserInfoPresenter
import com.zf.mart.mvp.presenter.UserInfoPresenter
import com.zf.mart.showToast
import com.zf.mart.utils.DurbanUtils
import com.zf.mart.utils.GlideUtils
import com.zf.mart.utils.LogUtils
import com.zf.mart.utils.StatusBarUtils
import com.zf.mart.view.popwindow.AvatarPopupWindow
import com.zf.mart.view.popwindow.SexChangeWindow
import kotlinx.android.synthetic.main.activity_user.*
import kotlinx.android.synthetic.main.layout_toolbar.*
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File
import java.util.*

class UserActivity : BaseActivity(), UpdateUserInfoContract.View, UserInfoContract.View {

    //获取用户资料
    override fun setUserInfo(bean: UserInfoBean) {
        UserInfoLiveData.value = bean
    }

    //修改用户信息成功
    //通知首页更新用户信息
    override fun setChangeUserInfo(bean: BaseBean<ChangeUserBean>) {
        showToast(bean.msg)
        userInfoPresenter.requestUserInfo()
    }

    //头像上传成功
    override fun setHead(bean: BaseBean<String>) {
        showToast(resources.getString(R.string.img_success))
        GlideUtils.loadUrlImage(this, bean.data, avatar)
    }

    override fun showError(msg: String, errorCode: Int) {
        showToast(msg)
    }

    override fun showLoading() {
        showLoadingDialog()
    }

    override fun dismissLoading() {
        dismissLoadingDialog()
    }


    companion object {
        const val REQUEST_CODE = 11
        const val CHANGE_NAME_CODE = 13
        fun actionStart(context: Context?) {
            context?.startActivity(Intent(context, UserActivity::class.java))
        }
    }

    override fun initToolBar() {
        StatusBarUtils.darkMode(this, ContextCompat.getColor(this, R.color.colorSecondText), 0.3f)
        back.setOnClickListener { finish() }
        titleName.text = "个人资料"
        rightLayout.visibility = View.INVISIBLE
    }

    override fun layoutId(): Int = R.layout.activity_user

    override fun initData() {
    }

    private val updateUserInfoPresenter by lazy { UpdateUserInfoPresenter() }
    private val userInfoPresenter by lazy { UserInfoPresenter() }

    override fun initView() {
        updateUserInfoPresenter.attachView(this)
        userInfoPresenter.attachView(this)
    }

    override fun onDestroy() {
        updateUserInfoPresenter.detachView()
        userInfoPresenter.detachView()
        super.onDestroy()
    }

    /**
     *    头像裁剪回调
     *
     *    修改名称回调
     */
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        //修改名称回调
        if (requestCode == CHANGE_NAME_CODE) {
            val newName: String? = data?.getStringExtra("newName")
            updateUserInfoPresenter.changeUserInfo(
                newName ?: "", "", "", "",
                "", ""
            )
        }

        if (resultCode == Activity.RESULT_OK) {
            //裁剪头像回调
            if (requestCode == REQUEST_CODE) {
                super.onActivityResult(requestCode, resultCode, data)
                data?.let {
                    val uri: Uri = Uri.parse(Durban.parseResult(it)[0])
                    val file = File(uri.path)
                    val builder = MultipartBody.Builder().setType(MultipartBody.FORM)
                    val imgBody = RequestBody.create(
                        MediaType.parse("multipart/form-data"),
                        file
                    )
                    builder.addFormDataPart("image_file", file.name, imgBody)
                    val imageBodyPart = MultipartBody.Part.createFormData(
                        "image" //约定key
                        , System.currentTimeMillis().toString() + ".png" //后台接收的文件名
                        , imgBody
                    )
                    updateUserInfoPresenter.upLoadHead(imageBodyPart)
                }
            }

        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    private fun initCrop(it: String) {
        val cropDirectory = DurbanUtils.getAppRootPath(this).absolutePath
        Durban.with(this)
            .inputImagePaths(it)
            .outputDirectory(cropDirectory)
            .maxWidthHeight(400, 400)
            .aspectRatio(1f, 1f)
            .compressFormat(Durban.COMPRESS_PNG)
            .compressQuality(70)
            .gesture(Durban.GESTURE_ALL)
            .controller(
                Controller.newBuilder()
                    .enable(false)
                    .build()
            )
            .requestCode(REQUEST_CODE)
            .start()
    }

    override fun initEvent() {

        //修改性别
        sex.setOnClickListener { _ ->
            val popWindow = object : SexChangeWindow(
                this,
                R.layout.pop_sex_change,
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            ) {}
            popWindow.showAtLocation(parentLayout, Gravity.BOTTOM, 0, 0)

            popWindow.onMan = {
                updateUserInfoPresenter.changeUserInfo(
                    "", "", "1", "", "", ""
                )
            }

            popWindow.onWomen = {
                updateUserInfoPresenter.changeUserInfo("", "", "2", "", "", "")
            }
        }

        headLayout.setOnClickListener { _ ->
            val popWindow = object : AvatarPopupWindow(
                this,
                R.layout.pop_avatar,
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            ) {}
            popWindow.showAtLocation(parentLayout, Gravity.BOTTOM, 0, 0)

            popWindow.onCamera = { it ->
                initCrop(it)
            }

            popWindow.onPhoto = {
                initCrop(it)
            }

        }


        birthLayout.setOnClickListener {
            val di = DatePickerDialog(
                this,
                DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
                    LogUtils.e(">>>>:$year $month $dayOfMonth")
                    updateUserInfoPresenter.changeUserInfo("", "", "", "year", "month", "dayOfMonth")
                },
                2019, 2, 2
            )
            di.datePicker.maxDate = Date().time
            di.show()
        }

        nickNameLayout.setOnClickListener {

            val intent = Intent(this, ChangeNameActivity::class.java)
            intent.putExtra("nickName", nickName.text.toString())
            startActivityForResult(intent, CHANGE_NAME_CODE)

        }
    }

    override fun start() {

        userInfoPresenter.requestUserInfo()

        UserInfoLiveData.observe(this, androidx.lifecycle.Observer { userInfo ->
            userInfo?.apply {
                GlideUtils.loadUrlImage(this@UserActivity, UriConstant.BASE_URL + head_pic, avatar)
                userName.text = realname
                nickName.text = nickname
            }
        })
    }
}