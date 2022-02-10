package com.hamid.learninggauth.core.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.hamid.learninggauth.R
import com.hamid.learninggauth.core.data.AppData
import com.hamid.learninggauth.core.utils.MyTextUtils.formatNumber
import com.hamid.learninggauth.core.utils.MyTextUtils.setFarsi
import com.hamid.learninggauth.core.utils.PersianDate
import kotlinx.android.synthetic.main.list_item.view.*
const val GOL_PRICE = 500000
class AppAdapter() : RecyclerView.Adapter<AppVH>() {

    var listItem = emptyList<AppData>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AppVH {
        return AppVH.by(parent)
    }

    override fun onBindViewHolder(holder: AppVH, position: Int) {
        val currentItem = listItem[position]
        with(holder.itemView) {

            tv_title_item.text = currentItem.title
            tv_income_item.text = formatNumber(currentItem.income.toInt()).plus(" تومان ")
            //gol(add a flower for items that income >= GOL_PRICE)
            if (currentItem.income.toInt() >= GOL_PRICE){
                tv_income_item.text = tv_income_item.text.toString().plus("\uD83C\uDF39")
            }

            // red color for bad works
//            if (currentItem.income.toInt() <= 0){
//                tv_income_item.setTextColor(resources.getColor(R.color.error))
//            }

            tv_income_item.setFarsi(true)
            tv_date_item.text = convertDate(currentItem.date)
            tv_date_item.setFarsi(true)

            root_item.setOnClickListener {
                findNavController().navigate(R.id.go_update, bundleOf("id" to currentItem.id))
            }
        }
    }

    private fun convertDate(date: Long?): String {

        return PersianDate(date).toString()
    }

    override fun getItemCount() = listItem.size

    fun submit(list: List<AppData>) {
        listItem = list
        println("AppAdapter.submit size:${list.size}")
        notifyDataSetChanged()
    }

}

class AppVH(view: View) : RecyclerView.ViewHolder(view) {

    companion object {
        fun by(parent: ViewGroup): AppVH {
            return AppVH(
                LayoutInflater.from(parent.context).inflate(
                    R.layout.list_item, parent, false
                )
            )
        }
    }
}

