package com.hamid.learninggauth.view.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.hamid.learninggauth.R
import com.hamid.learninggauth.core.data.AppData
import com.hamid.learninggauth.core.utils.MyTextUtils.convertToEnglish
import com.hamid.learninggauth.core.utils.MyTextUtils.setFarsi
import com.hamid.learninggauth.viewmodel.AppViewModel
import kotlinx.android.synthetic.main.fragment_add_item.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class AddItemFragment : Fragment(R.layout.fragment_add_item) {
    private val viewModel: AppViewModel by viewModel()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViews()

    }

    private fun setupViews() {
        setupToolbar()
        edt_total_add.setFarsi()
        edt_cost_add.setFarsi()
        btn_save_add.setOnClickListener {
            val title: String
            var total = "0"
            var cost = "0"

            if (edt_title_add.text?.isNotEmpty() == true) {
                title = edt_title_add.text.toString()
                if (edt_total_add.text?.isNotEmpty() == true) {

                    total = convertToEnglish(edt_total_add.text.toString())
                }

                if (edt_cost_add.text?.isNotEmpty() == true) {

                    cost = convertToEnglish(edt_cost_add.text.toString())
                }
                val income = (total.toLong() - cost.toLong()).toString()
                val appData = AppData(0, title, total, cost, income)
                viewModel.insert(appData)
                findNavController().popBackStack()

            } else {
                til_title_add.error = "لطفا یک عنوان وارد کنید"
            }

        }
    }

    private fun setupToolbar() {
        toolbar.setupWithNavController(
            findNavController(), AppBarConfiguration(findNavController().graph)
        )
    }

}