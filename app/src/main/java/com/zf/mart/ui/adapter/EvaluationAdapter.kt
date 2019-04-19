package com.zf.mart.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.scwang.smartrefresh.layout.util.DensityUtil
import com.zf.mart.R
import com.zf.mart.mvp.bean.GoodEvaList
import com.zf.mart.view.recyclerview.RecyclerViewDivider
import kotlinx.android.synthetic.main.iten_evaluation.view.*

class EvaluationAdapter(val context: Context?, val data: List<GoodEvaList>) : RecyclerView.Adapter<EvaluationAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.iten_evaluation, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.apply {
            starView.setRate(4)

            val adapter = EvaImageAdapter(context)
            val manager = LinearLayoutManager(context)
            manager.orientation = LinearLayoutManager.HORIZONTAL
            recyclerView.layoutManager = manager
            recyclerView.adapter = adapter
            recyclerView.addItemDecoration(
                    RecyclerViewDivider(
                            context,
                            LinearLayout.VERTICAL,
                            DensityUtil.dp2px(1f),
                            ContextCompat.getColor(context, R.color.colorBackground)
                    )
            )

        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

}