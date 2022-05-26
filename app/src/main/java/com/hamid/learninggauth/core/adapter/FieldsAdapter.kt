package com.hamid.learninggauth.core.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.hamid.learninggauth.R
import com.hamid.learninggauth.core.data.Field
import kotlinx.android.synthetic.main.item_fields.view.*

class FieldsAdapter : RecyclerView.Adapter<FieldsViewHolder>() {

    var listItem = mutableListOf<Field>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FieldsViewHolder {
        return FieldsViewHolder.by(parent)
    }

    override fun onBindViewHolder(holder: FieldsViewHolder, position: Int) {

        holder.bind(listItem[position])

        with(holder.itemView) {

            btn_remove_new_total.setOnClickListener {
                removeItem(position)
            }

        }
    }

    override fun getItemCount() = listItem.size

    @SuppressLint("NotifyDataSetChanged")
    fun submit(newList: MutableList<Field>) {
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
    fun bind(item: Field) {
        with(itemView) {
            try {
                if (layoutPosition == 0) {
                    btn_remove_new_total.visibility = GONE
                }

                tv_num_add.text = "${layoutPosition + 1}"
                item.id = layoutPosition
                item.forr = edt_for_add.text.toString()
                item.total = edt_total_add.text.toString()
                item.cost = edt_cost_add.text.toString()
                item.comment = edt_comment_add.text.toString()

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
