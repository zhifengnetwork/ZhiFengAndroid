package com.zf.mart.ui.fragment.info

import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.PopupWindow
import androidx.navigation.fragment.findNavController
import com.zf.mart.R
import com.zf.mart.base.BaseFragment
import com.zf.mart.showToast
import com.zf.mart.view.dialog.CustomAddressDialog
import kotlinx.android.synthetic.main.fragment_address_edit.*

/**
 * 新建收货人
 */
class AddressEditFragment : BaseFragment() {

    override fun getLayoutId(): Int = R.layout.fragment_address_edit

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.info_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_delete -> {
                showToast("点击了删除按钮")
            }
        }
        return true
    }

    override fun initView() {
        setHasOptionsMenu(true)
    }

    override fun lazyLoad() {
    }

    override fun initEvent() {

        //选择地址 修改选中图标在item_area
        addressLayout.setOnClickListener {
            val dialog = CustomAddressDialog(context)
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

        confirm.setOnClickListener {
            findNavController().navigateUp()
        }

    }
}