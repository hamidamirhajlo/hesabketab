package com.hamid.learninggauth.view.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.hamid.learninggauth.R
import com.hamid.learninggauth.core.data.AppData
import com.hamid.learninggauth.viewmodel.AppViewModel
import kotlinx.android.synthetic.main.fragment_update.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class UpdateFragment : Fragment(R.layout.fragment_update) {


    private val viewModel: AppViewModel by viewModel()
    private var id: Int? = null
    private var currentItem: AppData? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            if (it.containsKey("id")) {
                id = it.getInt("id")
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupToolbar()
        id?.let {

            viewModel.getItemById(id!!).observe(viewLifecycleOwner) { item ->
                currentItem = item
                extractItemToViews(currentItem!!)
            }

        }

        btn_save_details.setOnClickListener {
            updateItem()
        }


    }

    private fun updateItem() {
        val title: String
        var total = "0"
        var cost = "0"

        if (edt_title_details.text?.isNotEmpty() == true) {

            title = edt_title_details.text.toString()

            if (edt_total_details.text?.isNotEmpty() == true) {
                total = edt_total_details.text.toString()
            }

            if (edt_cost_details.text?.isNotEmpty() == true) {
                cost = edt_cost_details.text.toString()
            }

            val income = (total.toLong() - cost.toLong()).toString()

            val appData = AppData(id!!, title, total, cost,income,currentItem?.date)
            viewModel.update(appData)
            findNavController().popBackStack()

        } else {
            edt_title_details.error = "لطفا یک عنوان وارد کنید"
        }
    }

    private fun extractItemToViews(item: AppData) {

        edt_title_details.setText(item.title)
        edt_cost_details.setText(item.cost)
        edt_total_details.setText(item.total)

    }

    private fun setupToolbar() {
        toolbarDetails.setupWithNavController(
            findNavController(), AppBarConfiguration(findNavController().graph)
        )

        toolbarDetails.setOnMenuItemClickListener { item ->
            if (item.itemId == R.id.action_details_delete) {
                if (currentItem != null) {
                    viewModel.delete(currentItem!!)
                    findNavController().popBackStack()
                }

            }

            return@setOnMenuItemClickListener true
        }
    }

}