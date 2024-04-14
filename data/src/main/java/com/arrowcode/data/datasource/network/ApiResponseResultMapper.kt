package com.arrowcode.data.datasource.network

import com.arrowcode.data.datasource.network.model.ApiException
import retrofit2.Response

interface ApiResponseResultMapper {

    suspend fun <T> getResult(request: suspend () -> Response<T>): Result<T?> {
        return try {
            val response = request()
            if (response.isSuccessful) {
                Result.success(response.body())
            } else {
                Result.failure(ApiException(response.code()))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}