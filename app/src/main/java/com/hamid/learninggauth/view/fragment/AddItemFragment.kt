package com.hamid.learninggauth.view.fragment

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.hamid.learninggauth.R
import com.hamid.learninggauth.core.data.AppData
import com.hamid.learninggauth.core.utils.PersianDate
import com.hamid.learninggauth.viewmodel.AppViewModel
import kotlinx.android.synthetic.main.fragment_add_item.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class AddItemFragment : Fragment(R.layout.fragment_add_item) {
    private var selectedYear: Int = -1
    private var selectedMonth: Int = -1
    private var selectedDay: Int = -1
    private val viewModel: AppViewModel by viewModel()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        setupViews()

    }

    private fun setupViews() {
        setupToolbar()

        btn_save_add.setOnClickListener {
            val title: String
            var total = "0"
            var cost = "0"
            if (selectedDay == -1) {
                selectedDay = PersianDate().shDay
            }
            if (selectedMonth == -1) {
                selectedMonth = PersianDate().shMonth
            }
            if (selectedYear == -1) {
                selectedYear = PersianDate().shYear
            }

            if (edt_title_add.text?.isNotEmpty() == true) {
                title = edt_title_add.text.toString()
                if (edt_total_add.text?.isNotEmpty() == true) {
                    total = edt_total_add.text.toString()
                }

                if (edt_cost_add.text?.isNotEmpty() == true) {
                    cost = edt_cost_add.text.toString()
                }

                val appData =
                    AppData(0, title, total, cost, selectedDay, selectedMonth, selectedYear)
                viewModel.insert(appData)
                findNavController().popBackStack()

            } else {
                edt_title_add.error = "لطفا یک عنوان وارد کنید"
            }

            edt_title_add.setText("")
            edt_total_add.setText("")
            edt_cost_add.setText("")


        }
    }

    private fun setupToolbar() {
        toolbar.setupWithNavController(
            findNavController(), AppBarConfiguration(findNavController().graph)
        )
    }

}