package com.hamid.learninggauth.core.utils

import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.widget.TextView
import com.google.android.material.textfield.TextInputEditText
import java.math.BigDecimal
import java.text.DecimalFormat

/**
 * @author hamidamirhajlo
 * @since 2/2/2022
 *
 **/
object MyTextUtils {

    private val mChars = arrayOf(
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

    fun convertToFarsi(input: String): String {
        var faNumbers = input

        for (num in mChars) {
            faNumbers = faNumbers.replace(num[0], num[1])
        }
        return faNumbers
    }

    fun convertToEnglish(input: String): String {
        var enNumbers = input

        for (num in mChars) {
            enNumbers = enNumbers.replace(num[1], num[0])
        }
        return enNumbers.replace(",", "")
    }

    fun TextView.setFarsi(b: Boolean) {

        val s = this.text.toString()

        // val formattedNumber = formatNumber(number)

        if (b) {
            this.text = convertToFarsi(s)
        }
    }


    /**
     * Separate 3 numbers by comma
     * @sample (1000000) -> ("1,000,000")
     * @param number decimal number
     * @return string
     */
    fun formatNumber(number: Int): String {

        return DecimalFormat("#,###").format(number)
    }

    fun TextInputEditText.setFarsi() {

        this.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(text: Editable?) {

                this@setFarsi.removeTextChangedListener(this)
                val textInput = this@setFarsi.text.toString()
                if (!TextUtils.isEmpty(textInput)) {
                    val textWithOutComma = textInput.replace(",".toRegex(), "")
                    val englishNumber = BigDecimal(textWithOutComma).toString()
                    val number = englishNumber.toInt()
                    val formattedNumber = formatNumber(number)
                    this@setFarsi.setText(convertToFarsi(formattedNumber))
                    this@setFarsi.setSelection(formattedNumber.length)
                }

                this@setFarsi.addTextChangedListener(this)
            }
        })

    }

}