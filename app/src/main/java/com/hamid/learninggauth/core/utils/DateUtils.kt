package com.hamid.learninggauth.core.utils

import java.text.SimpleDateFormat
import java.util.*

@Suppress("RECEIVER_NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
object DateUtils {

    private val grgDateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.US)

    fun persanDateFormat(date: PersianDate) = PersianDateFormat.format(date, "Y/m/d",
    PersianDateFormat.PersianDateNumberCharacter.FARSI)

    // val min = 60 * 1000
    val hour = 3600000
    val dayMillis = 24 * hour

    private val now = Date()
    val nowMillis = now.time

    val week = SimpleDateFormat("W", Locale.US).format(now)
    val day = SimpleDateFormat("d", Locale.US).format(now)
    val month = SimpleDateFormat("M", Locale.US).format(now)
    val year = SimpleDateFormat("y", Locale.US).format(now)

    // compute this week start time from past days of this week mines now millis
    val w = PersianDate().dayOfWeek(now)
    private val weekStartMillis = (dayMillis * w).toLong()
    val weekStartTime = nowMillis - weekStartMillis

    val monthLong = grgDateFormat.parse("$year-$month-01").time

    val yearStartTime = grgDateFormat.parse("$year-01-01").time

    fun getMonthOfDate(date: Long) = SimpleDateFormat("M", Locale.US).format(Date(date))
    fun getDayOfDate(date: Long) = SimpleDateFormat("d", Locale.US).format(Date(date))
    fun getWeekOfDate(date: Long) = SimpleDateFormat("W", Locale.US).format(Date(date))
    fun getYearOfDate(date: Long) = SimpleDateFormat("y", Locale.US).format(Date(date))

}