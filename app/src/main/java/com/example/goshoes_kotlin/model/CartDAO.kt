package com.example.goshoes_kotlin.model

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface CartDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addItem(cart: CartEntity)

    @Query("SELECT * FROM cart WHERE email = :email")
    fun getCartItemByUser(email: String): Flow<List<CartEntity>>

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateItem(cart: CartEntity)

    @Query("DELETE FROM cart WHERE id = :id")
    suspend fun deleteItem(id: Int)
}