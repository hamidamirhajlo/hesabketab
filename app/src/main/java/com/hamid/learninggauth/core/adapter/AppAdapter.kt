package com.hamid.learninggauth.core.adapter

import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textfield.TextInputEditText
import com.hamid.learninggauth.R
import com.hamid.learninggauth.core.adapter.MyMethods.setFarsi
import com.hamid.learninggauth.core.data.AppData
import com.hamid.learninggauth.core.utils.PersianDate
import kotlinx.android.synthetic.main.list_item.view.*
import java.math.BigDecimal
import java.text.DecimalFormat

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

            tv_title_item.text = currentItem.title
            tv_income_item.text = currentItem.income.plus(" تومان ")
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
        notifyItemInserted(listItem.size)
    }

}

class AppVH(view: View) : RecyclerView.ViewHolder(view)

object MyMethods {

    fun convert(faNumbers: String): String {
        var faNumbers = faNumbers
        val mChars = arrayOf(
            arrayOf("0", "۰"),
            arrayOf("1", "۱"),
            arrayOf("2", "۲"),
            arrayOf("3", "۳"),
            arrayOf("4", "۴"),
            arrayOf("5", "۵"),
            arrayOf("6", "۶"),
            arrayOf("7", "۷"),
            arrayOf("8", "۸"),
            arrayOf("9", "۹")
        )
        for (num in mChars) {
            faNumbers = faNumbers.replace(num[0], num[1])
        }
        return faNumbers
    }

    fun TextView.setFarsi(b: Boolean) {

        val s = this.text.toString()

       // val formattedNumber = formatNumber(number)

        if (b) {
            this.text = convert(s)
        }
    }

    fun formatNumber(number:Int):String{

        return DecimalFormat("#,###").format(number)
    }

    fun TextInputEditText.setFarsi() {

        this.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(text: Editable?) {
                this@setFarsi.removeTextChangedListener(this)
                //processes

                println(text.toString())
                val textInput = this@setFarsi.text.toString()
                if (!TextUtils.isEmpty(textInput)){
                    val textWithOutComma = textInput.replace(",","")
                    val englishNumber = BigDecimal(textWithOutComma).toString()
                    val number = englishNumber.toInt()
                    val formattedNumber = formatNumber(number)
                    this@setFarsi.setText(formattedNumber)
                    this@setFarsi.setSelection(formattedNumber.length)
                }

                this@setFarsi.addTextChangedListener(this)
            }
        })

    }


}