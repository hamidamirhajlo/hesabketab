package com.hamid.learninggauth.view.fragment

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.hamid.learninggauth.R
import kotlinx.android.synthetic.main.fragment_nerkh.*

class NerkhFragment() : Fragment(R.layout.fragment_nerkh) {


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val brands = listOf("آیفون ", "اندروید")

        spinner_brand_nerkh.adapter =
            ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, brands)

        spinner_brand_nerkh.setSelection(0)

        val models = listOf("تا1 میلیون", "تا 2 میلیون")

        spinner_price_nerkh.adapter =
            ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, models)

        spinner_price_nerkh.setSelection(0)


    }
}