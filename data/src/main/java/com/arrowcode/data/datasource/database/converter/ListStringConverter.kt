package com.arrowcode.data.datasource.database.converter

import androidx.room.TypeConverter
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class ListStringConverter {
    @TypeConverter
    fun encode(list: List<String>): String = Json.encodeToString(list)

    @TypeConverter
    fun decode(list: String) = Json.decodeFromString<List<String>>(list)
}