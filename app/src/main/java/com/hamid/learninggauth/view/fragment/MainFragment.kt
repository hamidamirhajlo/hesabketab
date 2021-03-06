package com.hamid.learninggauth.view.fragment

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_NO
import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_YES
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.chip.Chip
import com.hamid.learninggauth.R
import com.hamid.learninggauth.core.adapter.ItemAdapter
import com.hamid.learninggauth.core.adapter.MyLinearLayoutManager
import com.hamid.learninggauth.core.data.Item
import com.hamid.learninggauth.core.utils.AppPreferences
import com.hamid.learninggauth.core.utils.DateUtils
import com.hamid.learninggauth.core.utils.DateUtils.monthLong
import com.hamid.learninggauth.core.utils.DateUtils.weekStartTime
import com.hamid.learninggauth.core.utils.DateUtils.yearStartTime
import com.hamid.learninggauth.core.utils.MyTextUtils
import com.hamid.learninggauth.core.utils.MyTextUtils.setFarsi
import com.hamid.learninggauth.core.utils.PersianDate
import com.hamid.learninggauth.viewmodel.AppViewModel
import ir.hamsaa.persiandatepicker.PersianDatePickerDialog
import ir.hamsaa.persiandatepicker.api.PersianPickerDate
import ir.hamsaa.persiandatepicker.api.PersianPickerListener
import kotlinx.android.synthetic.main.fragment_main.*
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

const val TAG = "MainFragment"

class MainFragment : Fragment(R.layout.fragment_main) {

    private var from: Long = 0
    private var until: Long = 0
    private var isInSelectFromDateDialog: Boolean = true
    private var picker: PersianDatePickerDialog? = null
    private var selectedChipText: CharSequence? = ""

    private val viewModel: AppViewModel by viewModel()
    private lateinit var adapter: ItemAdapter
    private val preferences: AppPreferences by inject()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupToolbar()
        setupRecyclerViewItems()
        initChips()
    }

    private fun setupRecyclerViewItems() {
        adapter = ItemAdapter()

        val myLinearLayoutManager = MyLinearLayoutManager(requireContext()).apply {
            reverseLayout = true
            stackFromEnd = true
        }
        recy_items.layoutManager = myLinearLayoutManager
        recy_items.adapter = adapter
    }


    private fun initChips() {

        setDefaultChipItem(chip_this_month)

        chip_group.setOnCheckedChangeListener { group, checkedId ->

            selectedChipText = group.findViewById<Chip>(checkedId).text

            when (checkedId) {
                R.id.chip_this_day -> filterListBy(PersianDate.today().time)
                R.id.chip_this_week -> filterListBy(weekStartTime)
                R.id.chip_this_month -> filterListBy(monthLong)
                R.id.chip_this_year -> filterListBy(yearStartTime)
                R.id.chip_all -> readAll()
                R.id.chip_select_date -> showSelectedDateDialog("?????????? ?? ??????????")
            }

        }
    }

    private fun showSelectedDateDialog(positiveText: String) {
        picker = PersianDatePickerDialog(requireContext())
            .setPositiveButtonString(positiveText)
            .setNegativeButton("????????????")
            .setTodayButton("?????????? ??????????")
            .setTodayButtonVisible(true)
            .setMinYear(1300)
            .setMaxYear(PersianDatePickerDialog.THIS_YEAR)
            .setMaxMonth(PersianDatePickerDialog.THIS_MONTH)
            .setMaxDay(PersianDatePickerDialog.THIS_DAY)
            .setInitDate(1400, 3, 13)
            .setActionTextColor(Color.BLACK)
            .setTypeFace(ResourcesCompat.getFont(requireContext(), R.font.irsns_m))
            .setTitleType(PersianDatePickerDialog.WEEKDAY_DAY_MONTH_YEAR)
            .setShowInBottomSheet(true)
            .setListener(object : PersianPickerListener {
                override fun onDateSelected(persianPickerDate: PersianPickerDate) {
                    Log.d(TAG, "onDateSelected: " + persianPickerDate.timestamp) //675930448000

                    if (isInSelectFromDateDialog) {
                        from = persianPickerDate.timestamp
                        selectedChipText = DateUtils.persanDateFormat(PersianDate(from))
                        showSelectedDateDialog("??????????")
                    } else {
                        // show select until date dialog
                        until = persianPickerDate.timestamp
                        selectedChipText = if (from == until) {
                            selectedChipText.toString().plus(" ???? ?????????? ??????")
                        } else {
                            selectedChipText.toString()
                                .plus(" ???? ${DateUtils.persanDateFormat(PersianDate(until))}")
                        }

                        filterListBy(from, until + DateUtils.dayMillis)
                    }
                    isInSelectFromDateDialog = !isInSelectFromDateDialog
                }

                override fun onDismissed() {}
            })

        picker?.show()
    }

    private fun setDefaultChipItem(chip: Chip) {
        chip.isChecked = true
        selectedChipText = chip.text
        filterListBy(monthLong)
    }

    private fun readAll() {
        viewModel.readAll().observe(viewLifecycleOwner) { all ->
            adapter.submit(all)
            computeTotalIncome(all)
        }
    }

    private fun filterListBy(from: Long, until: Long = System.currentTimeMillis()) {
        viewModel.filterByDate(from, until)
            .observe(viewLifecycleOwner) { listItemOfDay ->
                adapter.submit(listItemOfDay)
                computeTotalIncome(listItemOfDay)
            }
    }

    private fun computeTotalIncome(listItemOfMonth: List<Item>) {
        var t = 0
        listItemOfMonth.forEach {
            t += it.income.toInt()
        }

        if (t == 0) {
            tv_totalIncome_main.text = "???????? ?????? ?????????? ???????????? ?????? ???????? ??????."
        } else {

            //totalIncome = t
            val formattedT = MyTextUtils.formatNumber(t)
            tv_totalIncome_main.text = " ?????????? ?????????? ${selectedChipText}: $formattedT ?????????? "
            tv_totalIncome_main.setFarsi(true)
        }

    }

    private fun setupToolbar() {
        toolbar.setupWithNavController(
            findNavController(), AppBarConfiguration(
                findNavController().graph
            )
        )

        checkNightState()

        switch_night.setOnCheckedChangeListener { switch, b ->
            if (b) {
                AppCompatDelegate.setDefaultNightMode(MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(MODE_NIGHT_NO)
            }
            preferences.setNight(!preferences.isNight())

        }

        toolbar.setOnMenuItemClickListener {
            if (it.itemId == R.id.contact_menu){
                findNavController().navigate(R.id.action_mainFragment_to_contactFragment)
            }
            return@setOnMenuItemClickListener true
        }

    }

    @SuppressLint("UseCompat ForDrawables")
    private fun checkNightState() {
        if (!preferences.isNight()) {
            AppCompatDelegate.setDefaultNightMode(MODE_NIGHT_NO)
            switch_night.isChecked = false

        } else {
            switch_night.isChecked = true
            AppCompatDelegate.setDefaultNightMode(MODE_NIGHT_YES)

        }
    }


}