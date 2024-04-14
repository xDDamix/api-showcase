package com.arrowcode.data.di

import androidx.room.Room
import com.arrowcode.data.datasource.database.AppDatabase
import com.arrowcode.data.datasource.database.dao.ChallengeDao
import com.arrowcode.data.datasource.database.dao.CompletedChallengeDao
import com.arrowcode.data.datasource.database.dao.UserCompletedChallengesDao
import org.koin.dsl.module

val databaseModule = module {

    single<AppDatabase> {
        Room.databaseBuilder(get(), AppDatabase::class.java, "app-database")
            .fallbackToDestructiveMigration()
            .build()
    }

    single<ChallengeDao> { get<AppDatabase>().challengeDao() }
    single<CompletedChallengeDao> { get<AppDatabase>().completedChallengeDao() }
    single<UserCompletedChallengesDao> { get<AppDatabase>().userCompletedChallengesDao() }
}