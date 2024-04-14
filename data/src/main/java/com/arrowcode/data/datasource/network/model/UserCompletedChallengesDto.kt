package com.arrowcode.data.datasource.network.model

import com.arrowcode.data.datasource.database.model.CompletedChallengeEntity
import com.arrowcode.data.datasource.database.model.UserDataEntity
import com.arrowcode.domain.model.UserCompletedChallenges
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserCompletedChallengesDto(
    val totalPages: Int,
    val totalItems: Int,
    @SerialName("data") val completedChallenges: Array<CompletedChallengeDto>,
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as UserCompletedChallengesDto

        if (totalPages != other.totalPages) return false
        if (totalItems != other.totalItems) return false
        return completedChallenges.contentEquals(other.completedChallenges)
    }

    override fun hashCode(): Int {
        var result = totalPages
        result = 31 * result + totalItems
        result = 31 * result + completedChallenges.contentHashCode()
        return result
    }
}

fun UserCompletedChallengesDto.toDomain() = UserCompletedChallenges(
    pagesAmount = totalPages,
    totalCompletedChallenges = totalItems,
    completedChallenges = completedChallenges.map { it.toDomain() },
)

fun UserCompletedChallengesDto.toEntity(username: String) = UserDataEntity(
    username = username,
    totalPages = totalPages,
    totalItems = totalItems
)

fun UserCompletedChallengesDto.toCompletedChallengesList(username: String) =
    completedChallenges.map {
        CompletedChallengeEntity(
            id = it.id,
            username = username,
            name = it.name,
            slug = it.slug,
            completedAt = it.completedAt,
            completedLanguages = it.completedLanguages.toList(),
        )
    }