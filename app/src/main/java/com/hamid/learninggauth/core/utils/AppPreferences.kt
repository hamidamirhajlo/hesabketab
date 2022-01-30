package com.hamid.learninggauth.core.utils

import android.content.Context
import android.widget.Toast
import com.hamid.learninggauth.Application

class AppPreferences(private val application: Application) {
    private val preferences = application.getSharedPreferences("app_pref", Context.MODE_PRIVATE)

    fun setNight(b: Boolean) {
        preferences.edit().putBoolean("night", b).apply()
        //isNight = preferences.getBoolean("night", false)
    }

    fun isNight() = preferences.getBoolean("night", false)

}