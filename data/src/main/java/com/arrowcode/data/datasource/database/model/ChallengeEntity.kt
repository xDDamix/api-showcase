package com.arrowcode.data.datasource.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.arrowcode.domain.model.Challenge

@Entity(tableName = "challenge")
data class ChallengeEntity(
    @PrimaryKey
    val id: String,
    val name: String,
    val slug: String,
    val url: String,
    val category: String,
    val description: String,
    val tags: List<String>,
    val languages: List<String>,
    val publishedAt: String,
    val approvedAt: String,
    val totalCompleted: Long,
    val totalAttempts: Long,
    val totalStars: Long,
    val voteScore: Long,
    val contributorsWanted: Boolean,
)

fun ChallengeEntity.toDomain(): Challenge {
    return Challenge(
        name = name,
        slug = slug,
        url = url,
        category = category,
        description = description,
        tags = tags,
        languages = languages,
        totalCompleted = totalCompleted,
        totalStars = totalStars,
        voteScore = voteScore
    )
}