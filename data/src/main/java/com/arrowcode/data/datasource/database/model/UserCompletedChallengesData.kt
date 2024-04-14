package com.arrowcode.data.datasource.database.model

import androidx.room.Embedded
import androidx.room.Relation
import com.arrowcode.domain.model.CompletedChallenge
import com.arrowcode.domain.model.UserCompletedChallenges

data class UserCompletedChallengesData(
    @Embedded val userData: UserDataEntity,
    @Relation(
        parentColumn = "username",
        entityColumn = "username",
    ) val completedChallenges: List<CompletedChallengeEntity>,
)

fun UserCompletedChallengesData.toDomain(): UserCompletedChallenges =
    UserCompletedChallenges(
        pagesAmount = userData.totalPages,
        totalCompletedChallenges = userData.totalItems,
        completedChallenges = completedChallenges.map {
            CompletedChallenge(
                it.id,
                it.name,
                it.slug,
                it.completedAt,
                it.completedLanguages
            )
        },
    )