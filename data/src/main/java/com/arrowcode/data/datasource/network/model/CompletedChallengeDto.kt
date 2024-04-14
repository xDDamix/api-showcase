package com.arrowcode.data.datasource.network.model

import com.arrowcode.domain.model.CompletedChallenge
import kotlinx.serialization.Serializable

@Serializable
data class CompletedChallengeDto(
    val id: String,
    val name: String,
    val slug: String,
    val completedAt: String,
    val completedLanguages: Array<String>,
) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as CompletedChallengeDto

        if (id != other.id) return false
        if (name != other.name) return false
        if (slug != other.slug) return false
        if (completedAt != other.completedAt) return false
        return completedLanguages.contentEquals(other.completedLanguages)
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + name.hashCode()
        result = 31 * result + slug.hashCode()
        result = 31 * result + completedAt.hashCode()
        result = 31 * result + completedLanguages.contentHashCode()
        return result
    }
}

fun CompletedChallengeDto.toDomain() =
    CompletedChallenge(
        id = id,
        name = name,
        slug = slug,
        completedAt = completedAt,
        completedLanguages = completedLanguages.toList()
    )