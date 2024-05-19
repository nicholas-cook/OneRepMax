package com.nickcook.onerepmax.core.util

import java.text.SimpleDateFormat
import java.util.Locale
import java.util.TimeZone
import kotlin.math.roundToInt

object Utils {

    fun getLongFromDateString(dateString: String): Long {
        val dateFormat = SimpleDateFormat("MMM dd yyyy", Locale.US).apply {
            timeZone = TimeZone.getTimeZone("UTC")
        }
        return dateFormat.parse(dateString)?.time ?: 0
    }

    fun getDateStringFromLong(dateLong: Long): String {
        val dateFormat = SimpleDateFormat("MMM d ''yy", Locale.US).apply {
            timeZone = TimeZone.getTimeZone("UTC")
        }
        println(dateLong)
        return dateFormat.format(dateLong)
    }

    fun calculateOneRepMax(reps: Int, weight: Int): Int {
        // Brzycki formula
        return (weight * (36.0 / (37.0 - reps))).roundToInt()
    }
}