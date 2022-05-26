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
import com.hamid.learninggauth.core.data.AppData
import com.hamid.learninggauth.core.data.FieldsData
import com.hamid.learninggauth.viewmodel.AppViewModel
import kotlinx.android.synthetic.main.fields_add.view.*
import kotlinx.android.synthetic.main.fragment_add_2.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class AddFragment2 : Fragment(R.layout.fragment_add_2) {
    private val viewModel: AppViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupToolbar()
        val fieldAdapter = FieldsAdapter()

        val actvAdapter = ArrayAdapter(
            requireContext(), android.R.layout.simple_expandable_list_item_1,
            resources.getStringArray(R.array.actv_title_suggestions)
        )
        actv_title_add2.setAdapter(actvAdapter)

        recy_add2.apply {
            adapter = fieldAdapter
/*            addItemDecoration(
                MaterialDividerItemDecoration(
                    requireContext(),
                    MaterialDividerItemDecoration.VERTICAL
                )
            )*/

        }
        val emptyFields = FieldsData(0, "", "", "", "")

        val itemList = mutableListOf<FieldsData>().apply {
            add(emptyFields)
        }

        fieldAdapter.submit(itemList)

        btn_add_new_total.setOnClickListener {
            itemList.add(emptyFields)
            fieldAdapter.submit(itemList)
            recy_add2.smoothScrollToPosition(itemList.size)

        }
        /*
        * save list to db
        * go back to main fragment
        * */
        btn_save_add2.setOnClickListener {
            if (actv_title_add2.text.isNotEmpty()) {
                val title = actv_title_add2.text.toString()
                var total = 0
                var cost = 0
                var income = 0
                var forr = ""
                var comment = ""

                fieldAdapter.listItem.forEachIndexed { index, fieldsData ->

                    val vh = recy_add2.findViewHolderForLayoutPosition(index) as FieldsViewHolder

                    with(vh.itemView) {

                        if (edt_total_add2.text?.isNotEmpty() == true) {
                            total += edt_total_add2.text.toString().toInt()
                        }

                        if (edt_cost_add2.text?.isNotEmpty() == true) {
                            cost += edt_cost_add2.text.toString().toInt()
                        }

                        if (edt_for_add2.text?.isNotEmpty() == true) {
                            forr = edt_for_add2.text.toString()
                        }

                        if (edt_comment_add2.text?.isNotEmpty() == true) {
                            comment = edt_comment_add2.text.toString()
                        }
                    }
                }

                income = total - cost

                val appData =
                    AppData(
                        0,
                        title,
                        total.toString(),
                        cost.toString(),
                        income.toString(),
                        forr,
                        comment
                    )

                println(appData)
            } else {
                actv_title_add2.error = "عنوان نمی تواند خالی باشد"
            }


        }

    }

    private fun setupToolbar() {
        toolbar.setupWithNavController(
            findNavController(), AppBarConfiguration(findNavController().graph)
        )
    }
}
