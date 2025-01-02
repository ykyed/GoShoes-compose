package com.example.goshoes_kotlin.model

import android.content.Context
import android.util.Log
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.goshoes_kotlin.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import org.json.JSONArray
import kotlin.random.Random

class PrePopulateRoomCallback(private val context: Context): RoomDatabase.Callback() {

    private val TAG : String? = PrePopulateRoomCallback::class.simpleName

    override fun onCreate(db: SupportSQLiteDatabase) {
        Log.d(TAG, "onCreate")
        super.onCreate(db)

        CoroutineScope(Dispatchers.IO).launch {

            async {prePopulateShoes(context)}
            async {prePopulateSize(context)}
            async {prePopulateReviews(context)}

            async {updateReviewCountInShoe(context)}
        }
    }

    private suspend fun prePopulateShoes(context: Context) {

        try {
            val shoeDAO = AppDataBase.getDataBase(context).shoeDAO()

            val shoeList =
                context.resources.openRawResource(R.raw.shoes_data).bufferedReader().use {
                    JSONArray(it.readText())
                }

            shoeList.takeIf { it.length() > 0 }?.let { list ->
                for (index in 0..<list.length()) {
                    val shoeObj = list.getJSONObject(index)

                    shoeDAO.insertShoe(
                        ShoeEntity(
                            productCode = shoeObj.getString("productCode"),
                            title = shoeObj.getString("title"),
                            price = shoeObj.getDouble("price"),
                            totalRating = shoeObj.getDouble("rating"),
                            reviewCount = shoeObj.getInt("reviewCount"),
                            color = shoeObj.getString("color"),
                            style = shoeObj.getString("style"),
                            brand = shoeObj.getString("brand"),
                            thumbnail = shoeObj.getString("thumbnail"),
                            images = shoeObj.getJSONArray("images").toString(),
                        )
                    )
                }
            }

        } catch (e: Exception) {
            Log.e(TAG, e.message.toString())
        }
    }

    private suspend fun prePopulateSize(context: Context) {

        val sizeInfoDAO = AppDataBase.getDataBase(context).sizeInfoDAO()
        val shoeList = AppDataBase.getDataBase(context).shoeDAO().getAllShoes()

        val sizeArr = listOf(6, 6.5, 7, 7.5, 8, 8.5, 9, 9.5, 10, 10.5, 11, 11.5, 12, 12.5, 13)

        shoeList.collect { list ->
            list.forEach { shoe ->
                val productCode = shoe.productCode

                for (i in sizeArr.indices) {
                    sizeInfoDAO.insertSize(SizeInfoEntity(
                        productCode = productCode,
                        size = sizeArr[i].toDouble(),
                        quantity = Random.nextInt(10)
                    ))
                }
            }
        }
    }

    private fun prePopulateReviews(context: Context) {

        try {
            val reviewInfoDAO = AppDataBase.getDataBase(context).reviewInfoDAO()

            val reviewList =
                context.resources.openRawResource(R.raw.review_data).bufferedReader().use {
                    JSONArray(it.readText())
                }

            reviewList.takeIf { it.length() > 0 }?.let { list ->
                for (index in 0..<list.length()) {
                    val reviewObj = list.getJSONObject(index)
                    reviewInfoDAO.addReview(
                        ReviewInfoEntity(
                            productCode = reviewObj.getString("productCode"),
                            title = reviewObj.getString("title"),
                            comment = reviewObj.getString("comment"),
                            rating = reviewObj.getDouble("rating"),
                        )
                    )
                }
            }

        } catch (e: Exception) {
            Log.e(TAG, e.message.toString())
        }
    }

    private suspend fun updateReviewCountInShoe(context: Context) {

        val shoeDAO = AppDataBase.getDataBase(context).shoeDAO()
        val shoeList = shoeDAO.getAllShoes()
        val reviewList = AppDataBase.getDataBase(context).reviewInfoDAO().getAllReviews()

        shoeList.combine(reviewList) { shoes, reviews ->
            shoes.map { shoe ->
                val matchedReviews = reviews.filter { it.productCode == shoe.productCode }
                val totalRating = matchedReviews.sumOf { it.rating }
                val reviewCount = matchedReviews.size

                shoe.copy(
                    totalRating = totalRating,
                    reviewCount = reviewCount,
                )

            }
        }.collect{ updatedShoes ->
            updatedShoes.forEach { shoe ->
                shoeDAO.updateShoe(shoe)
            }
        }
    }
}