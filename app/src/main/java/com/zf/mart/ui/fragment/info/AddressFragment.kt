package com.zf.mart.ui.fragment.info

import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.scwang.smartrefresh.layout.util.DensityUtil
import com.zf.mart.R
import com.zf.mart.base.BaseFragment
import com.zf.mart.ui.adapter.AddressAdapter
import com.zf.mart.utils.LogUtils
import com.zf.mart.view.recyclerview.RecyclerViewDivider
import kotlinx.android.synthetic.main.fragment_address.*

/**
 * 地址管理
 */
class AddressFragment : BaseFragment() {

    override fun getLayoutId(): Int = R.layout.fragment_address

    private val adapter by lazy { AddressAdapter(context) }

    override fun initView() {

        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = adapter
        recyclerView.addItemDecoration(
            RecyclerViewDivider(
                context,
                LinearLayoutManager.VERTICAL,
                DensityUtil.dp2px(10f),
                ContextCompat.getColor(context!!, R.color.colorBackground)
            )
        )
    }

    override fun lazyLoad() {
        LogUtils.e(">>>>>>load")
    }


    override fun initEvent() {
        //新建收货人
        newAddress.setOnClickListener {
            findNavController().navigate(R.id.addressEditAction)
        }
    }
}