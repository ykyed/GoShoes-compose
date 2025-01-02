package com.example.goshoes_kotlin.model

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [ShoeEntity::class, SizeInfoEntity::class, ReviewInfoEntity::class, UserInfoEntity::class],
    version = 1
)
abstract class AppDataBase : RoomDatabase() {

    abstract fun shoeDAO(): ShoeDAO
    abstract fun sizeInfoDAO(): SizeInfoDAO
    abstract fun reviewInfoDAO(): ReviewInfoDAO
    abstract fun userInfoDAO(): UserInfoDAO

    // apply singleton
    companion object {

        private const val DB_NAME = "goshoes.db"

        @Volatile
        private var INSTANCE: AppDataBase? = null

        fun getDataBase(context: Context): AppDataBase {

            synchronized(this) {
                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        AppDataBase::class.java,
                        DB_NAME
                    )
                        .addCallback(PrePopulateRoomCallback(context))
                        .build()

                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}