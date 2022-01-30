package com.hamid.learninggauth.view.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.view.View.*
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_NO
import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_YES
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.hamid.learninggauth.R
import com.hamid.learninggauth.core.adapter.AppAdapter
import com.hamid.learninggauth.core.data.AppData
import com.hamid.learninggauth.core.utils.AppPreferences
import com.hamid.learninggauth.core.utils.PersianDate
import com.hamid.learninggauth.core.utils.PersianDateFormat
import com.hamid.learninggauth.viewmodel.AppViewModel
import kotlinx.android.synthetic.main.fragment_main.*
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainFragment : Fragment(R.layout.fragment_main) {

    private var selectedYear: Int=0
    private var selectedMonth: Int=0
    private var selectedDay: Int=0
    private val viewModel: AppViewModel by viewModel()
    private lateinit var adapter: AppAdapter
    private val preferences: AppPreferences by inject()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupToolbar()
        val pDate = PersianDate()

        val pDateFormat = PersianDateFormat.format(
            pDate,
            "Y/m/d",
            PersianDateFormat.PersianDateNumberCharacter.FARSI
        )

        tv_calender_main.text = pDateFormat.toString()

        adapter = AppAdapter()

        recy_items.adapter = adapter


        viewModel.readAll().observe(viewLifecycleOwner) {

            adapter.submit(it)
            recy_items.smoothScrollToPosition(it.size)

        }

        fab_add_item.setOnClickListener {
            findNavController().navigate(R.id.go_add)
        }

        initChips()
    }

    private fun initChips() {
        chip_this_month.isChecked = true
        chip_group.setOnCheckedChangeListener { group, checkedId ->
            Toast.makeText(requireContext(), checkedId.toString(), Toast.LENGTH_SHORT).show()
            when (checkedId) {
                R.id.chip_this_month -> {
                    root_select_date.visibility = GONE
                    adapter.listItem.forEach {
                        val persianDateFormat = PersianDateFormat(
                            "m",
                            PersianDateFormat.PersianDateNumberCharacter.FARSI
                        )
                    }

                }

                R.id.chip_this_year -> {
                    root_select_date.visibility = GONE
                    viewModel.readThisYear()
                }

                R.id.chip_all -> {
                    root_select_date.visibility = GONE
                    viewModel.readThisMonth()
                }

                R.id.chip_select_date -> {
                    initACTVs()
                    root_select_date.visibility = VISIBLE
                }
            }
        }
    }

    private fun initACTVs() {
        val days = (1..31).toList()
        val dayAdapter = ArrayAdapter(
            requireContext(), android.R.layout.simple_expandable_list_item_1,
            days
        )
        actv_day.setAdapter(dayAdapter)

        actv_day.setOnItemClickListener { adapterView, view, i, l ->
            selectedDay = i+1
        }

        val month = PersianDate.monthNames
        val monthAdapter = ArrayAdapter(
            requireContext(), android.R.layout.simple_expandable_list_item_1,
            month
        )

        actv_month.setAdapter(monthAdapter)
//        actv_month.listSelection = month[PersianDate().shMonth]
        actv_month.setOnItemClickListener { adapterView, view, i, l ->
            selectedMonth = i+1
        }

        val years = (PersianDate().shYear - 5..PersianDate().shYear).toList()

        val yearAdapter = ArrayAdapter(
            requireContext(), android.R.layout.simple_expandable_list_item_1,
            years
        )

        actv_year.setAdapter(yearAdapter)
        actv_year.setOnItemClickListener { adapterView, view, i, l ->
            selectedYear = years[i]
        }
    }

    private fun setupToolbar() {
        toolbar.setupWithNavController(
            findNavController(), AppBarConfiguration(
                findNavController().graph
            )
        )

        checkNightState()

        toolbar.setOnMenuItemClickListener { menuItem ->

            if (menuItem.itemId == R.id.action_main_night) {

                if (!preferences.isNight()) {
                    AppCompatDelegate.setDefaultNightMode(MODE_NIGHT_YES)
                } else {
                    AppCompatDelegate.setDefaultNightMode(MODE_NIGHT_NO)
                }

                preferences.setNight(!preferences.isNight())
            }
            return@setOnMenuItemClickListener false
        }

    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private fun checkNightState() {
        if (!preferences.isNight()) {
            toolbar.menu.findItem(R.id.action_main_night).icon =
                resources.getDrawable(R.drawable.ic_night_yes)
            AppCompatDelegate.setDefaultNightMode(MODE_NIGHT_NO)

        } else {
            toolbar.menu.findItem(R.id.action_main_night).icon =
                resources.getDrawable(R.drawable.ic_night_no)
            AppCompatDelegate.setDefaultNightMode(MODE_NIGHT_YES)

        }
    }

}