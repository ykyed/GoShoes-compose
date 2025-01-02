package com.example.goshoes_kotlin.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "shoe")
data class ShoeEntity(

    @PrimaryKey(autoGenerate = true) val id: Int = 0,

    @ColumnInfo(name = "productCode") val productCode: String,
    @ColumnInfo(name=  "title") val title: String,
    @ColumnInfo(name = "price") val price: Double,
    @ColumnInfo(name = "rating") val totalRating: Double,
    @ColumnInfo(name = "reviewCount") val reviewCount: Int,
    @ColumnInfo(name = "color") val color: String,
    @ColumnInfo(name = "style") val style: String,
    @ColumnInfo(name = "brand") val brand: String,
    @ColumnInfo(name = "thumbnail") val thumbnail: String,
    @ColumnInfo(name = "images") val images: String,
)