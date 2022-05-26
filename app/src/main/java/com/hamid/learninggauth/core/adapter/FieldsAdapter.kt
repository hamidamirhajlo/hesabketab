package com.hamid.learninggauth.core.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.RecyclerView
import com.hamid.learninggauth.R
import com.hamid.learninggauth.core.data.FieldsData
import kotlinx.android.synthetic.main.fields_add.view.*

class FieldsAdapter : RecyclerView.Adapter<FieldsViewHolder>() {

    private var cost: Int = 0
    private var total: Int = 0
    private var income: Int = 0
    var listItem = mutableListOf<FieldsData>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FieldsViewHolder {
        return FieldsViewHolder.by(parent)
    }

    override fun onBindViewHolder(holder: FieldsViewHolder, position: Int) {

        holder.bind(listItem[position])

        with(holder.itemView) {

            btn_remove_new_total.setOnClickListener {
                removeItem(position)
            }

            edt_total_add2.addTextChangedListener {

                cost
            }
        }
    }

    override fun getItemCount() = listItem.size

    @SuppressLint("NotifyDataSetChanged")
    fun submit(newList: MutableList<FieldsData>) {
        listItem = newList
        notifyItemInserted(listItem.size - 1)
    }

    private fun removeItem(index: Int) {
        listItem.removeAt(index)
        notifyItemRemoved(listItem.size)
    }

}

class FieldsViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    @SuppressLint("SetTextI18n")
    fun bind(item: FieldsData) {
        with(itemView) {
            try {
                if (layoutPosition == 0) {
                    btn_remove_new_total.visibility = GONE
                }

                tv_num_add2.text = "${layoutPosition + 1}"
                item.id = layoutPosition
                item.forr = edt_for_add2.text.toString()
                item.total = edt_total_add2.text.toString()
                item.cost = edt_cost_add2.text.toString()
                item.comment = edt_comment_add2.text.toString()

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
                    R.layout.fields_add, parent, false
                )
            )
        }
    }
}
