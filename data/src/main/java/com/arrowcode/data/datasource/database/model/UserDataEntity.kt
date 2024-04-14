package com.arrowcode.data.datasource.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_data")
data class UserDataEntity(
    @PrimaryKey val username: String,
    val totalPages: Int,
    val totalItems: Int,
)