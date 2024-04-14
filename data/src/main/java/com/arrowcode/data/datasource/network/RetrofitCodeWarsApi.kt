package com.arrowcode.data.datasource.network

import com.arrowcode.data.datasource.network.model.ChallengeDto
import com.arrowcode.data.datasource.network.model.UserCompletedChallengesDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RetrofitCodeWarsApi {

    @GET("api/v1/users/{user}/code-challenges/completed")
    suspend fun getCompletedChallenges(
        @Path("user") user: String,
        @Query("page") page: Int = 0
    ): Response<UserCompletedChallengesDto>

    @GET("api/v1/code-challenges/{challenge}")
    suspend fun getChallenge(
        @Path("challenge") challenge: String
    ): Response<ChallengeDto>
}