package com.example.goshoes_kotlin.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "size")
data class SizeInfoEntity(

    @PrimaryKey(autoGenerate = true) val id: Int = 0,

    @ColumnInfo(name = "productCode") val productCode: String,
    @ColumnInfo(name = "size") val size: Double,
    @ColumnInfo(name = "quantity") val quantity: Int,
)
