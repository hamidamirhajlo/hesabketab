package com.hamid.learninggauth.view.fragment

import android.content.DialogInterface
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.hamid.learninggauth.R
import com.hamid.learninggauth.core.data.Item
import com.hamid.learninggauth.core.utils.MyTextUtils.convertToEnglish
import com.hamid.learninggauth.core.utils.MyTextUtils.setFarsi
import com.hamid.learninggauth.viewmodel.AppViewModel
import kotlinx.android.synthetic.main.fragment_update.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class UpdateFragment : Fragment(R.layout.fragment_update) {

    private val viewModel: AppViewModel by viewModel()
    private var id: Int? = null
    private var currentItem: Item? = null

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
            deleteItem()
        }

    }

    private fun updateItem() {
        edt_total_details.setFarsi()
        edt_cost_details.setFarsi()
        val title: String
        var total = "0"
        var cost = "0"

        if (edt_title_details.text?.isNotEmpty() == true) {

            title = edt_title_details.text.toString()

            if (edt_total_details.text?.isNotEmpty() == true) {
                total =  convertToEnglish(edt_total_details.text.toString())
            }

            if (edt_cost_details.text?.isNotEmpty() == true) {
                cost = convertToEnglish(edt_cost_details.text.toString())
            }

            val income = (total.toLong() - cost.toLong()).toString()

            val appData = Item(id!!, title, total, cost, income, "","",currentItem?.date)
            viewModel.update(appData)
            findNavController().popBackStack()

        } else {
            edt_title_details.error = "لطفا یک عنوان وارد کنید"
        }
    }

    private fun extractItemToViews(item: Item) {
        edt_total_details.setFarsi()
        edt_cost_details.setFarsi()

        edt_title_details.setText(item.title)
        edt_total_details.setText(item.total)
        edt_cost_details.setText(item.cost)

    }

    private fun setupToolbar() {
        toolbar.setupWithNavController(
            findNavController(), AppBarConfiguration(findNavController().graph)
        )

        toolbar.setOnMenuItemClickListener { item ->
            if (item.itemId == R.id.action_details_update) {
                if (currentItem != null) {
                    updateItem()
                }

            }

            return@setOnMenuItemClickListener true
        }
    }

    private fun deleteItem() {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle("حذف")
            .setMessage("این آیتم برای همیشه حذف شود؟")
            .setNegativeButton("نه") { di: DialogInterface, i: Int ->
                di.dismiss()
            }
            .setPositiveButton("بله") { di: DialogInterface, i: Int ->
                viewModel.delete(currentItem!!)
                findNavController().popBackStack()
            }
            .show()

    }

}