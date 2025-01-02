package com.example.goshoes_kotlin.model

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface SizeInfoDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSize(size: SizeInfoEntity)

    @Query("SELECT * FROM size WHERE productCode = :productCode")
    fun getSizesByProductCode(productCode: String): Flow<List<SizeInfoEntity>>
}