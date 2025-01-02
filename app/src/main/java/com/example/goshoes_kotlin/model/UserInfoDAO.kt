package com.example.goshoes_kotlin.model

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface UserInfoDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addUser(user: UserInfoEntity)

    @Query("SELECT * FROM user WHERE email = :email")
    fun getUserInfo(email: String): Flow<UserInfoEntity>
}