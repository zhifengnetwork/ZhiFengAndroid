package com.zf.mart.ui.activity

import android.content.Context
import android.content.Intent
import com.yanzhenjie.album.Album
import com.zf.mart.R
import com.zf.mart.base.BaseActivity
import com.zf.mart.mvp.contract.UploadHeadContract
import com.zf.mart.mvp.presenter.UploadHeadPresenter
import com.zf.mart.utils.LogUtils
import kotlinx.android.synthetic.main.activity_up_head.*
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File


class TestUpHeadActivity : BaseActivity(), UploadHeadContract.View {

    override fun showLoading() {
    }

    override fun dismissLoading() {
    }

    override fun showError(msg: String, errorCode: Int) {
    }

    companion object {
        fun actionStart(context: Context?) {
            context?.startActivity(Intent(context, TestUpHeadActivity::class.java))
        }
    }

    override fun initToolBar() {
    }

    override fun layoutId(): Int = R.layout.activity_up_head

    override fun initData() {
    }

    override fun initView() {
        presenter.attachView(this)


    }


    override fun initEvent() {

        /** 选择图片获得图片路径 */
        chooseHead.setOnClickListener {
            Album.image(this)
                .multipleChoice()
                .camera(true)
                .columnCount(3)
                .selectCount(1)
                .onResult {
                    LogUtils.e(">>>>>>:" + it[0].path)
                    val file = File(it[0].path)

                    val builder = MultipartBody.Builder().setType(MultipartBody.FORM)

                    val imgBody = RequestBody.create(
                        MediaType.parse("multipart/form-data"),
                        file
                    )

                    builder.addFormDataPart("imgfile", file.name, imgBody)

                    val imageBodyPart = MultipartBody.Part.createFormData(
                        "imgfile" //头部携带name
                        , file.name  //头部携带fileName
                        , imgBody
                    )

                    //上传多张
//                    presenter.upLoadHead(builder.build().parts())

                    presenter.upLoadHead(imageBodyPart)


//                    LogUtils.e(">>>>>>>:" + file)
//                    LogUtils.e(">>>>>>>:" + file.name)
//                    LogUtils.e(">>>>>>>:" + imgBody)
//                    LogUtils.e(">>>>>>>:$imageBodyPart")
//                    LogUtils.e(">>>>>>>:${imageBodyPart.body()}")
//                    LogUtils.e(">>>>>>>:${imageBodyPart.body()}")
//                    LogUtils.e(">>>>>>>:${imageBodyPart.headers()}")

                }
                .start()
        }

//        presenter.upLoadHead()
    }

    private val presenter by lazy { UploadHeadPresenter() }


    override fun onDestroy() {
        presenter.detachView()
        super.onDestroy()
    }

    override fun start() {
    }
}