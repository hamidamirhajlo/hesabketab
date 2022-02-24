package com.hamid.learninggauth.view.fragment

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.divider.MaterialDividerItemDecoration
import com.hamid.learninggauth.R
import com.hamid.learninggauth.core.adapter.FieldsAdapter
import com.hamid.learninggauth.core.data.FieldsData
import kotlinx.android.synthetic.main.fragment_add_2.*
import kotlinx.android.synthetic.main.fragment_add_2.toolbar
import kotlinx.android.synthetic.main.fragment_add_item.*

class AddFragment2 : Fragment(R.layout.fragment_add_2) {

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
            addItemDecoration(
                MaterialDividerItemDecoration(
                    requireContext(),
                    MaterialDividerItemDecoration.VERTICAL
                )
            )

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

    }

    private fun setupToolbar() {
        toolbar.setupWithNavController(
            findNavController(), AppBarConfiguration(findNavController().graph)
        )
    }
}