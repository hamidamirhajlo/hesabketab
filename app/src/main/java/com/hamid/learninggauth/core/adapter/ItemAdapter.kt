package com.hamid.learninggauth.core.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.hamid.learninggauth.R
import com.hamid.learninggauth.core.data.Item
import com.hamid.learninggauth.core.utils.MyTextUtils.formatNumber
import com.hamid.learninggauth.core.utils.MyTextUtils.setFarsi
import com.hamid.learninggauth.core.utils.PersianDate
import kotlinx.android.synthetic.main.list_item.view.*
const val GOL_PRICE = 500000
class ItemAdapter : RecyclerView.Adapter<ItemVH>() {

    var listItem = emptyList<Item>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemVH {
        return ItemVH.by(parent)
    }

    override fun onBindViewHolder(holder: ItemVH, position: Int) {
        val currentItem = listItem[position]
        with(holder.itemView) {

            tv_title_item.text = currentItem.title
            tv_income_item.text = formatNumber(currentItem.income.toInt()).plus(" تومان ")
            //gol(add a flower for items that income >= GOL_PRICE)
            if (currentItem.income.toInt() >= GOL_PRICE){
                tv_income_item.text = tv_income_item.text.toString().plus("\uD83C\uDF39")
            }

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

    fun submit(list: List<Item>) {
        listItem = list
        notifyDataSetChanged()
    }

}

class ItemVH(view: View) : RecyclerView.ViewHolder(view) {

    companion object {
        fun by(parent: ViewGroup): ItemVH {
            return ItemVH(
                LayoutInflater.from(parent.context).inflate(
                    R.layout.list_item, parent, false
                )
            )
        }
    }
}

