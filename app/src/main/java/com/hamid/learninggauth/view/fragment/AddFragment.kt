package com.hamid.learninggauth.view.fragment

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.hamid.learninggauth.R
import com.hamid.learninggauth.core.adapter.FieldsAdapter
import com.hamid.learninggauth.core.adapter.FieldsViewHolder
import com.hamid.learninggauth.core.data.Item
import com.hamid.learninggauth.core.data.Field
import com.hamid.learninggauth.viewmodel.AppViewModel
import kotlinx.android.synthetic.main.item_fields.view.*
import kotlinx.android.synthetic.main.fragment_add.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class AddFragment : Fragment(R.layout.fragment_add) {
    private val viewModel: AppViewModel by viewModel()

    private val fieldAdapter = FieldsAdapter()
    private val fieldList = mutableListOf<Field>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupToolbar()


        val actvAdapter = ArrayAdapter(
            requireContext(), android.R.layout.simple_expandable_list_item_1,
            resources.getStringArray(R.array.actv_title_suggestions)
        )

        actv_title_add.setAdapter(actvAdapter)

        // setup recyclerview with an empty field
        recy_add.adapter = fieldAdapter
        addNewField()

    }

    // add new empty field and notify recyclerview
    private fun addNewField() {
        fieldList.add(Field(0, "", "", "", ""))
        fieldAdapter.submit(fieldList)
        recy_add.smoothScrollToPosition(fieldList.size)
    }

    // compute data from inputs and save list to db
    private fun save(fieldAdapter: FieldsAdapter) {
        if (actv_title_add.text.isNotEmpty()) {
            val title = actv_title_add.text.toString()
            var total = 0
            var cost = 0
            var forr = ""
            var comment = ""

            fieldAdapter.listItem.forEachIndexed { index, _ ->

                val vh = recy_add.findViewHolderForLayoutPosition(index) as FieldsViewHolder

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

                    /** get forr text and seprate each of them via (,) **/
                    forr = if (edt_for_add.text?.isNotEmpty() == true) {
                        forr.plus("${edt_for_add.text.toString()},")
                    } else {
                        forr.plus(" , ندارد")
                    }
                }
            }

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

            println(appData)
            viewModel.insert(appData)
            findNavController().popBackStack()
        } else {
            actv_title_add.requestFocus()
            textInputLayout_title.error = "عنوان نمی تواند خالی باشد"
        }
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
