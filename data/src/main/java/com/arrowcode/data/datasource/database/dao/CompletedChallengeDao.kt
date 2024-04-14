package com.arrowcode.data.datasource.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.arrowcode.data.datasource.database.model.CompletedChallengeEntity

@Dao
interface CompletedChallengeDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCompletedChallenge(completedChallengeEntity: CompletedChallengeEntity)

    @Query("SELECT * FROM completed_challenge WHERE id=:id")
    suspend fun getCompletedChallengeById(id: String): CompletedChallengeEntity
}