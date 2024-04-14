package com.arrowcode.data.datasource.network.model

import com.arrowcode.data.datasource.database.model.ChallengeEntity
import com.arrowcode.domain.model.Challenge
import kotlinx.serialization.Serializable

@Serializable
data class ChallengeDto(
    val id: String,
    val name: String,
    val slug: String,
    val url: String,
    val category: String,
    val description: String,
    val tags: Array<String>,
    val languages: Array<String>,
    val publishedAt: String,
    val approvedAt: String,
    val totalCompleted: Long,
    val totalAttempts: Long,
    val totalStars: Long,
    val voteScore: Long,
    val contributorsWanted: Boolean,
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ChallengeDto

        if (id != other.id) return false
        if (name != other.name) return false
        if (slug != other.slug) return false
        if (url != other.url) return false
        if (category != other.category) return false
        if (description != other.description) return false
        if (!tags.contentEquals(other.tags)) return false
        if (!languages.contentEquals(other.languages)) return false
        if (publishedAt != other.publishedAt) return false
        if (approvedAt != other.approvedAt) return false
        if (totalCompleted != other.totalCompleted) return false
        if (totalAttempts != other.totalAttempts) return false
        if (totalStars != other.totalStars) return false
        if (voteScore != other.voteScore) return false
        return contributorsWanted == other.contributorsWanted
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + name.hashCode()
        result = 31 * result + slug.hashCode()
        result = 31 * result + url.hashCode()
        result = 31 * result + category.hashCode()
        result = 31 * result + description.hashCode()
        result = 31 * result + tags.contentHashCode()
        result = 31 * result + languages.contentHashCode()
        result = 31 * result + publishedAt.hashCode()
        result = 31 * result + approvedAt.hashCode()
        result = 31 * result + totalCompleted.hashCode()
        result = 31 * result + totalAttempts.hashCode()
        result = 31 * result + totalStars.hashCode()
        result = 31 * result + voteScore.hashCode()
        result = 31 * result + contributorsWanted.hashCode()
        return result
    }
}

fun ChallengeDto.toDomain() = Challenge(
    name = name,
    slug = slug,
    url = url,
    category = category,
    description = description,
    tags = tags.toList(),
    languages = languages.toList(),
    totalCompleted = totalCompleted,
    totalStars = totalStars,
    voteScore = voteScore
)

fun ChallengeDto.toEntity() = ChallengeEntity(
    id = id,
    name = name,
    slug = slug,
    url = url,
    category = category,
    description = description,
    tags = tags.toList(),
    languages = languages.toList(),
    publishedAt = publishedAt,
    approvedAt = approvedAt,
    totalCompleted = totalCompleted,
    totalAttempts = totalAttempts,
    totalStars = totalStars,
    voteScore = voteScore,
    contributorsWanted = contributorsWanted
)
