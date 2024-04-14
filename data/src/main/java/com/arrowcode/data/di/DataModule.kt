package com.arrowcode.data.di

import com.arrowcode.data.ChallengesRepositoryImpl
import com.arrowcode.data.datasource.ApiChallengesDataSource
import com.arrowcode.data.datasource.CacheableChallengesDataSource
import com.arrowcode.data.datasource.database.DatabaseCacheableChallengesDataSourceImpl
import com.arrowcode.data.datasource.network.NetworkChallengesDataSourceImpl
import com.arrowcode.domain.repository.ChallengesRepository
import kotlinx.coroutines.Dispatchers
import org.koin.core.qualifier.named
import org.koin.dsl.module

private const val ioDispatcherNamed = "IODispatcher"

val dataModule = module {

    single(named(ioDispatcherNamed)) {
        Dispatchers.IO
    }

    single<ApiChallengesDataSource> {
        NetworkChallengesDataSourceImpl(get())
    }

    single<CacheableChallengesDataSource> {
        DatabaseCacheableChallengesDataSourceImpl(
            get(),
            get(),
            get(),
        )
    }

    single<ChallengesRepository> {
        ChallengesRepositoryImpl(get(named(ioDispatcherNamed)), get(), get())
    }
}