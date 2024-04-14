package com.arrowcode.data.datasource.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.arrowcode.data.datasource.database.model.UserCompletedChallengesData
import com.arrowcode.data.datasource.database.model.UserDataEntity

@Dao
interface UserCompletedChallengesDao {

    @Query("SELECT * FROM user_data WHERE username=:username")
    @Transaction
    suspend fun getUserCompletedChallenges(username: String): UserCompletedChallengesData?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUserCompletedChallenges(data: UserDataEntity)
}