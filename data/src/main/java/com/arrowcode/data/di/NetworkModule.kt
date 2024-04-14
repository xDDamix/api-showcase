package com.arrowcode.data.di

import com.arrowcode.data.datasource.network.RetrofitCodeWarsApi
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit

val networkModule = module {

    single {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        OkHttpClient.Builder().addInterceptor(loggingInterceptor).build()
    }

    single {
        val contentType = "application/json".toMediaType()
        val jsonConfiguration = Json { ignoreUnknownKeys = true }
        Retrofit.Builder()
            .baseUrl("https://www.codewars.com/")
            .client(get())
            .addConverterFactory(jsonConfiguration.asConverterFactory(contentType))
            .build()
    }

    single {
        get<Retrofit>().create(RetrofitCodeWarsApi::class.java)
    }
}