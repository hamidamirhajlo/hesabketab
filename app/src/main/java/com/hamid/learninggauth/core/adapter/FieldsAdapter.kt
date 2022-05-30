package com.hamid.learninggauth.core.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.hamid.learninggauth.R
import com.hamid.learninggauth.core.data.Item
import kotlinx.android.synthetic.main.item_fields.view.*

class FieldsAdapter : RecyclerView.Adapter<FieldsViewHolder>() {

    var listItem = mutableListOf<Item>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FieldsViewHolder {
        return FieldsViewHolder.by(parent)
    }

    override fun onBindViewHolder(holder: FieldsViewHolder, position: Int) {
        holder.bind(listItem[position])
    }

    override fun getItemCount() = listItem.size

    @SuppressLint("NotifyDataSetChanged")
    fun submit(newList: MutableList<Item>) {
        listItem = newList
        notifyItemInserted(listItem.size - 1)
    }


}

class FieldsViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    @SuppressLint("SetTextI18n")
    fun bind(item: Item) {
        with(itemView) {
            try {

                tv_num_add.text = "${layoutPosition + 1}"

                edt_for_add.setText(item.forr)
                edt_total_add.setText(item.total)
                edt_cost_add.setText(item.cost)
                edt_comment_add.setText(item.comment)

            } catch (e: Exception) {
                Toast.makeText(this.context, "لطفا ورودی ها را کنترل کنید.", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }

    companion object {
        fun by(parent: ViewGroup): FieldsViewHolder {
            return FieldsViewHolder(
                LayoutInflater.from(parent.context).inflate(
                    R.layout.item_fields, parent, false
                )
            )
        }
    }
}
