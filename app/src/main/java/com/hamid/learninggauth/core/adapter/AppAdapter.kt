package com.hamid.learninggauth.core.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.hamid.learninggauth.R
import com.hamid.learninggauth.core.data.AppData
import kotlinx.android.synthetic.main.list_item.view.*

class AppAdapter() : RecyclerView.Adapter<AppVH>() {

    var listItem = emptyList<AppData>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AppVH {
        return AppVH(
            LayoutInflater.from(parent.context).inflate(
                R.layout.list_item, parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: AppVH, position: Int) {
        val currentItem = listItem[position]
        with(holder.itemView) {
            //  tvNumber_item.text = (holder.adapterPosition+1).toString()
            tv_title_item.text = currentItem.title
            tv_income_item.text = computeIncome(currentItem.total.toInt(), currentItem.cost.toInt())
                .toString()
            tv_date_item.text = convertDate(currentItem.day, currentItem.month, currentItem.year)
            root_item.setOnClickListener {
                findNavController().navigate(R.id.go_update, bundleOf("id" to currentItem.id))
            }
        }
    }

    private fun convertDate(day: Int, month: Int, year: Int): String {

        return "$year/$month/$day"
    }

    override fun getItemCount() = listItem.size

    fun submit(list: List<AppData>) {
        listItem = list
        notifyItemInserted(listItem.size)
    }

    fun computeIncome(total: Int, cost: Int) = total - cost

}

class AppVH(view: View) : RecyclerView.ViewHolder(view)
