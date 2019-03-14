package com.zf.mart.ui.fragment.info

import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.navigation.fragment.findNavController
import com.zf.mart.R
import com.zf.mart.base.BaseFragment
import com.zf.mart.showToast
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
        confirm.setOnClickListener {
            findNavController().navigateUp()
        }

    }
}