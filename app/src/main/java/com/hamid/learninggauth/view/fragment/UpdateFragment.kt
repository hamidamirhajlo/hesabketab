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


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupToolbar()

    }

    private fun setupToolbar() {
        toolbar.setupWithNavController(
            findNavController(), AppBarConfiguration(findNavController().graph)
        )

        toolbar.setOnMenuItemClickListener { item ->


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