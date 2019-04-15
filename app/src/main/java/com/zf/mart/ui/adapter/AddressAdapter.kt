package com.zf.mart.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.zf.mart.R
import com.zf.mart.mvp.bean.AddressBean
import com.zf.mart.ui.activity.AddressEditActivity
import kotlinx.android.synthetic.main.item_address.view.*

class AddressAdapter(val context: Context?, val addressData: List<AddressBean>) :
    RecyclerView.Adapter<AddressAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_address, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = addressData.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.itemView.apply {

            phoneNum.text = addressData[position].mobile

            userName.text = addressData[position].consignee

            province.text = addressData[position].province_name +
                    addressData[position].city_name +
                    addressData[position].district_name +
                    addressData[position].address


        }

        holder.itemView.edit_btn.setOnClickListener {

            //将地址信息传递过去
            AddressEditActivity.actionStart(context,addressData[position])


        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

}