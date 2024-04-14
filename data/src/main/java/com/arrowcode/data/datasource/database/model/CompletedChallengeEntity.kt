package com.arrowcode.data.datasource.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "completed_challenge")
data class CompletedChallengeEntity(
    @PrimaryKey
    val id: String,
    val username: String,
    val name: String,
    val slug: String,
    val completedAt: String,
    val completedLanguages: List<String>,
)
