package com.example.goshoes_kotlin.model

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.RawQuery
import androidx.room.Update
import androidx.sqlite.db.SupportSQLiteQuery
import kotlinx.coroutines.flow.Flow

@Dao
interface ShoeDAO {

    @Query("SELECT * FROM shoe")
    fun getAllShoes(): Flow<List<ShoeEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertShoe(shoe: ShoeEntity)

    @Query("SELECT * FROM shoe WHERE productCode = :productCode")
    fun getShoe(productCode: String): Flow<ShoeEntity>

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateShoe(shoe: ShoeEntity)

    @Query("SELECT DISTINCT brand FROM shoe")
    fun getDistinctBrand(): Flow<List<String>>

    @Query("SELECT DISTINCT color FROM shoe")
    fun getDistinctColor(): Flow<List<String>>

    @Query("SELECT DISTINCT style FROM shoe")
    fun getDistinctStyle(): Flow<List<String>>

//    @Query(
//        """
//    SELECT * FROM shoe
//    WHERE (:brands IS NULL OR brand IN (:brands))
//    AND (:colors IS NULL OR color IN (:colors))
//    AND (:styles IS NULL OR style IN (:styles))
//    """
//    )
//    fun getFilteredShoes(
//        brands: List<String>?,
//        colors: List<String>?,
//        styles: List<String>?
//    ): Flow<List<ShoeEntity>>

    @RawQuery(observedEntities = [ShoeEntity::class])
    fun getFilteredShoesRawQuery(query: SupportSQLiteQuery): Flow<List<ShoeEntity>>
}