package com.arrowcode.data.datasource.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.arrowcode.data.datasource.database.model.ChallengeEntity

@Dao
interface ChallengeDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertChallenge(challengeEntity: ChallengeEntity)

    @Query("SELECT * FROM challenge WHERE id=:id")
    suspend fun getChallengeById(id: String): ChallengeEntity?
}