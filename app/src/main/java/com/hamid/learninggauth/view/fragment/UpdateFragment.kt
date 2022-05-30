package com.hamid.learninggauth.view.fragment

import android.content.DialogInterface
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.hamid.learninggauth.R
import com.hamid.learninggauth.core.adapter.FieldsAdapter
import com.hamid.learninggauth.core.adapter.FieldsViewHolder
import com.hamid.learninggauth.core.data.Item
import com.hamid.learninggauth.viewmodel.AppViewModel
import kotlinx.android.synthetic.main.fragment_update.*
import kotlinx.android.synthetic.main.item_fields.view.*
import kotlinx.android.synthetic.main.layout_inputs.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class UpdateFragment : Fragment(R.layout.fragment_update) {

    private lateinit var adapter: FieldsAdapter
    private var isValidate: Boolean = false
    private val viewModel: AppViewModel by viewModel()
    private var id: Int? = null

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
        adapter = FieldsAdapter()
        recyclerview_inputs.adapter = adapter

        if (id != null) {
            viewModel.getItemById(id!!).observe(viewLifecycleOwner) { item ->
                actv_title_inputs.setText(item.title)

                adapter.submit(mutableListOf(item))

            }
        } else {
            Toast.makeText(requireContext(), "خطا", Toast.LENGTH_SHORT).show()
        }

    }

    private fun setupToolbar() {
        toolbar.setupWithNavController(
            findNavController(), AppBarConfiguration(findNavController().graph)
        )

        toolbar.setOnMenuItemClickListener { item ->

            when (item.itemId) {
                R.id.action_delete_update -> showDeleteDialog()
                R.id.action_details_update -> showUpdateDialog()
            }

            return@setOnMenuItemClickListener true
        }
    }

    private fun showUpdateDialog() {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle("ذخیره")
            .setMessage("تغییرات ذخیره شود؟")
            .setNegativeButton("نه") { di: DialogInterface, _: Int ->
                di.dismiss()
            }
            .setPositiveButton("بله") { _: DialogInterface, _: Int ->
                updateItem()
            }
            .show()

    }

    private fun updateItem() {

        save()?.let { it1 -> viewModel.update(it1) }

        Toast.makeText(requireContext(), "تغییرات ذخیره شد", Toast.LENGTH_SHORT).show()
        findNavController().popBackStack()

    }

    // save inputs
    private fun save(): Item? {
        val item: Item? = null

        var total = 0
        var cost = 0
        var forr = ""
        var comment = ""

        // check title
        if (actv_title_inputs.text.isNotEmpty()) {

           item?.title = actv_title_inputs.text.toString()

        } else {
            actv_title_inputs.requestFocus()
            textInputLayout_title.error = "عنوان نمی تواند خالی باشد"
            return null
        }

        adapter.listItem.forEachIndexed { index, _ ->

            val vh =
                recyclerview_inputs.findViewHolderForLayoutPosition(index) as FieldsViewHolder

            with(vh.itemView) {

                if (edt_total_add.text?.isNotEmpty() == true) {
                    total += edt_total_add.text.toString().toInt()
                }

                if (edt_cost_add.text?.isNotEmpty() == true) {
                    cost += edt_cost_add.text.toString().toInt()
                }

                /** get comment text and seprate each of them via (,) **/
                comment = if (edt_comment_add.text?.isNotEmpty() == true) {
                    comment.plus("${edt_comment_add.text.toString()} , ")
                } else {
                    comment.plus(" , ندارد")
                }
                /** (required) get forr text and seprate each of them via (,) **/
                if (edt_for_add.text?.isNotEmpty() == true) {
                    forr = forr.plus("${edt_for_add.text.toString()},")
                    isValidate = true
                } else {
                    til_for_add.error = "الزامی"
                    actv_title_inputs.clearFocus()
                    textInputLayout_title.isErrorEnabled = false
                }
            }
        }

        if (isValidate) {
            return item!!
        }



        return null
    }

    private fun showDeleteDialog() {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle("حذف")
            .setMessage("این آیتم برای همیشه حذف شود؟")
            .setNegativeButton("نه") { di: DialogInterface, _: Int ->
                di.dismiss()
            }
            .setPositiveButton("بله") { _: DialogInterface, _: Int ->
                deleteItem()
            }
            .show()

    }

    private fun deleteItem() {
        id?.let {
            viewModel.delete(it)
            Toast.makeText(requireContext(), "با موفقیت حذف شد.", Toast.LENGTH_SHORT).show()
            findNavController().popBackStack()
        }
    }

}