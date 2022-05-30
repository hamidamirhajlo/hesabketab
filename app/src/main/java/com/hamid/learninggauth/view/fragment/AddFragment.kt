package com.hamid.learninggauth.view.fragment

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.hamid.learninggauth.R
import com.hamid.learninggauth.core.adapter.FieldsAdapter
import com.hamid.learninggauth.core.adapter.FieldsViewHolder
import com.hamid.learninggauth.core.data.Item
import com.hamid.learninggauth.viewmodel.AppViewModel
import kotlinx.android.synthetic.main.fragment_add.*
import kotlinx.android.synthetic.main.item_fields.view.*
import kotlinx.android.synthetic.main.layout_inputs.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.lang.Exception

class AddFragment : Fragment(R.layout.fragment_add) {
    private val viewModel: AppViewModel by viewModel()

    private val fieldAdapter = FieldsAdapter()
    private val itemList = mutableListOf<Item>()

    // set true when edt_forr is not empty
    private var isValidate = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupToolbar()


        val actvAdapter = ArrayAdapter(
            requireContext(), android.R.layout.simple_expandable_list_item_1,
            resources.getStringArray(R.array.actv_title_suggestions)
        )

        actv_title_inputs.setAdapter(actvAdapter)

        // setup recyclerview with an empty field
        recyclerview_inputs.adapter = fieldAdapter
        addNewField()

    }

    // add new empty field and notify recyclerview
    private fun addNewField() {
        itemList.add(Item(0, "", "", "", ""))
        fieldAdapter.submit(itemList)
        recyclerview_inputs.smoothScrollToPosition(itemList.size)
    }

    // compute data from inputs and save list to db
    private fun save(fieldAdapter: FieldsAdapter) {
        if (actv_title_inputs.text.isNotEmpty()) {
            val title = actv_title_inputs.text.toString()
            var total = 0
            var cost = 0
            var forr = ""
            var comment = ""

            try {

            fieldAdapter.listItem.forEachIndexed { index, _ ->

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

            }catch (e:Exception){
                Toast.makeText(requireContext(), "error", Toast.LENGTH_SHORT).show()
            }
            if (isValidate) {
                save(total, cost, title, forr, comment)
            }

        } else {
            actv_title_inputs.requestFocus()
            textInputLayout_title.error = "عنوان نمی تواند خالی باشد"
        }
    }

    private fun save(
        total: Int,
        cost: Int,
        title: String,
        forr: String,
        comment: String
    ) {
        val income: Int = total - cost
        val appData =
            Item(
                0,
                title,
                total.toString(),
                cost.toString(),
                income.toString(),
                forr,
                comment
            )

        viewModel.insert(appData)
        findNavController().popBackStack()
    }

    private fun setupToolbar() {
        toolbar.setupWithNavController(
            findNavController(), AppBarConfiguration(findNavController().graph)
        )

        toolbar.setOnMenuItemClickListener {

            when (it.itemId) {

                R.id.action_save -> save(fieldAdapter)
                R.id.action_add_field -> addNewField()
            }

            return@setOnMenuItemClickListener true
        }
    }
}
