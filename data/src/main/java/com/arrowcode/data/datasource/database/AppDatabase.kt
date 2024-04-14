package com.arrowcode.data.datasource.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.arrowcode.data.datasource.database.dao.ChallengeDao
import com.arrowcode.data.datasource.database.dao.CompletedChallengeDao
import com.arrowcode.data.datasource.database.dao.UserCompletedChallengesDao
import com.arrowcode.data.datasource.database.model.ChallengeEntity
import com.arrowcode.data.datasource.database.model.CompletedChallengeEntity
import com.arrowcode.data.datasource.database.converter.ListStringConverter
import com.arrowcode.data.datasource.database.model.UserDataEntity

@Database(entities = [ChallengeEntity::class, CompletedChallengeEntity::class, UserDataEntity::class], version = 1, exportSchema = false)
@TypeConverters(ListStringConverter::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun challengeDao(): ChallengeDao
    abstract fun completedChallengeDao(): CompletedChallengeDao
    abstract fun userCompletedChallengesDao(): UserCompletedChallengesDao

}