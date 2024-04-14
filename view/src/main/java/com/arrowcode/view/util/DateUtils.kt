package com.arrowcode.view.util

import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter

object DateUtils {

    private val dateInputFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSX")
    private val dateOutputFormat = DateTimeFormatter.ofPattern("d MMMM yyyy")

    fun formatDate(inputDate: String): String? {
        return try {
            val instant = Instant.from(dateInputFormat.parse(inputDate))

            dateOutputFormat.format(instant.atZone(ZoneId.systemDefault()))
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}