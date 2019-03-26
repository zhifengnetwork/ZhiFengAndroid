package com.zf.mart.ui.fragment.info

import android.app.DatePickerDialog
import android.widget.DatePicker
import androidx.navigation.fragment.findNavController
import com.zf.mart.R
import com.zf.mart.base.BaseFragment
import com.zf.mart.ui.activity.TestUpHeadActivity
import com.zf.mart.utils.LogUtils
import kotlinx.android.synthetic.main.fragment_user.*
import java.util.*

class UserFragment : BaseFragment() {

    override fun getLayoutId(): Int = R.layout.fragment_user

    override fun initView() {
    }

    override fun lazyLoad() {
    }

    override fun initEvent() {

        birthLayout.setOnClickListener {

            val di = DatePickerDialog(
                context,
                DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
                    LogUtils.e(">>>>:$year $month $dayOfMonth")
                },
                2019, 2, 2
            )
            di.datePicker.maxDate = Date().time
            di.show()
        }

        headLayout.setOnClickListener {
            TestUpHeadActivity.actionStart(context)
        }

        nameLayout.setOnClickListener {
            findNavController().navigate(R.id.changeNameAction)
        }
    }
}