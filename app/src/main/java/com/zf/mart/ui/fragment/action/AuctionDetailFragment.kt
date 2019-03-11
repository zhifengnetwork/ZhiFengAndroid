package com.zf.mart.ui.fragment.action

import android.view.Gravity
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import com.zf.mart.R
import com.zf.mart.base.BaseFragment
import com.zf.mart.ui.adapter.AuctionPeopleAdapter
import com.zf.mart.view.dialog.AuctionSuccessDialog
import com.zf.mart.view.popwindow.ServicePopupWindow
import kotlinx.android.synthetic.main.fragment_auction_detail.*
import kotlinx.android.synthetic.main.pop_push_order.view.*

class AuctionDetailFragment : BaseFragment() {

//    companion object {
//        fun newInstance(): AuctionDetailFragment {
//            return AuctionDetailFragment()
//        }
//    }

    private val adapter by lazy { AuctionPeopleAdapter(context) }

    override fun getLayoutId(): Int = R.layout.fragment_auction_detail

    override fun initView() {
        val manager = LinearLayoutManager(context)
        manager.orientation = LinearLayoutManager.HORIZONTAL
        recyclerView.layoutManager = manager
        recyclerView.adapter = adapter
    }

    override fun lazyLoad() {
    }

    override fun initEvent() {
        confirm.setOnClickListener {
            AuctionSuccessDialog.showDialog(childFragmentManager)
                .setOnItemClickListener(object : AuctionSuccessDialog.OnItemClickListener {
                    override fun onItemClick() {

                        val window = object : ServicePopupWindow(
                            activity, R.layout.pop_push_order,
                            LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT
                        ) {
                            override fun initView() {
                                contentView.apply {
                                    submit.setOnClickListener {
                                        onDismiss()
                                    }
                                }
                            }
                        }
                        window.showAtLocation(parentLayout, Gravity.BOTTOM, 0, 0)
                    }
                })
        }
    }

}