package com.example.goshoes_kotlin.model

import android.hardware.display.DeviceProductInfo
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface ReviewInfoDAO {

    @Query("SELECT * FROM review")
    fun getAllReviews(): Flow<List<ReviewInfoEntity>>

    @Query("SELECT * FROM review WHERE productCode = :productCode")
    fun getReviewsByProductCode(productCode: String): Flow<List<ReviewInfoEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addReview(review: ReviewInfoEntity)


}