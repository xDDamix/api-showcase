package com.arrowcode.data_test.datasource.network

import com.arrowcode.data.datasource.database.model.ChallengeEntity
import com.arrowcode.data.datasource.database.model.CompletedChallengeEntity
import com.arrowcode.data.datasource.network.model.ChallengeDto
import com.arrowcode.data.datasource.network.model.CompletedChallengeDto
import com.arrowcode.domain.model.CompletedChallenge

class TestHelper {

    companion object {
        fun createCompletedChallengesList(vararg completedChallenges: CompletedChallengeDto): List<CompletedChallengeDto> =
            listOf(*completedChallenges)

        fun createCompletedChallengeDto(
            id: String = "id",
            name: String = "name",
            slug: String = "slug",
            completedAt: String = "completedAt",
            completedLanguages: Array<String> = arrayOf("a", "b")
        ): CompletedChallengeDto = CompletedChallengeDto(
            id = id,
            name = name,
            slug = slug,
            completedAt = completedAt,
            completedLanguages = completedLanguages
        )

        fun createCompletedChallengeEntity(
            id: String = "0",
            username: String = "asd",
            name: String = "aaa",
            slug: String = "bbb",
            completedAt: String = "",
            completedLanguages: List<String> = listOf("a", "b"),
        ): CompletedChallengeEntity = CompletedChallengeEntity(
            id,
            username,
            name,
            slug,
            completedAt,
            completedLanguages,
        )

        fun createCompletedChallenge(
            id: String = "id",
            name: String = "name",
            slug: String = "slug",
            completedAt: String = "completedAt",
            completedLanguages: List<String> = listOf("a", "b")
        ): CompletedChallenge =
            CompletedChallenge(
                id = id,
                name = name,
                slug = slug,
                completedAt = completedAt,
                completedLanguages = completedLanguages
            )

        fun createChallengeDto(
            id: String = "id",
            name: String = "name",
            slug: String = "slug",
            url: String = "url",
            category: String = "category",
            description: String = "description",
            tags: Array<String> = arrayOf("a", "b"),
            languages: Array<String> = arrayOf("a", "b"),
            publishedAt: String = "publishedAt",
            approvedAt: String = "approvedAt",
            totalCompleted: Long = 1,
            totalAttempts: Long = 1,
            totalStars: Long = 1,
            voteScore: Long = 1,
            contributorsWanted: Boolean = false,
        ): ChallengeDto = ChallengeDto(
            id = id,
            name = name,
            slug = slug,
            url = url,
            category = category,
            description = description,
            tags = tags,
            languages = languages,
            publishedAt = publishedAt,
            approvedAt = approvedAt,
            totalCompleted = totalCompleted,
            totalAttempts = totalAttempts,
            totalStars = totalStars,
            voteScore = voteScore,
            contributorsWanted = contributorsWanted
        )

        fun createChallengeEntity(
            id: String = "id",
            name: String = "name",
            slug: String = "slug",
            url: String = "url",
            category: String = "category",
            description: String = "description",
            tags: List<String> = listOf("a", "b"),
            languages: List<String> = listOf("a", "b"),
            publishedAt: String = "publishedAt",
            approvedAt: String = "approvedAt",
            totalCompleted: Long = 1,
            totalAttempts: Long = 1,
            totalStars: Long = 1,
            voteScore: Long = 1,
            contributorsWanted: Boolean = false,
        ): ChallengeEntity = ChallengeEntity(
            id = id,
            name = name,
            slug = slug,
            url = url,
            category = category,
            description = description,
            tags = tags,
            languages = languages,
            publishedAt = publishedAt,
            approvedAt = approvedAt,
            totalCompleted = totalCompleted,
            totalAttempts = totalAttempts,
            totalStars = totalStars,
            voteScore = voteScore,
            contributorsWanted = contributorsWanted
        )
    }
}