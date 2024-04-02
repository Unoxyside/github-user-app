package com.bahasyim.mygithubapp.data.local.room

import android.app.Application
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.bahasyim.mygithubapp.data.local.entity.FavEntity

@Database(entities = [FavEntity::class], version = 1)
abstract class FavDatabase: RoomDatabase() {
    abstract fun favDao(): FavDao

    companion object {
        @Volatile
        private var INSTANCE:FavDatabase? = null

        @JvmStatic
        fun getDatabase(context: Application): FavDatabase {
            if (INSTANCE == null){
                synchronized(FavDatabase::class.java){
                INSTANCE = Room.databaseBuilder(
                    context.applicationContext, FavDatabase::class.java, "favorite_database"
                )
                    .build()
                }
            }
            return INSTANCE as FavDatabase
        }
    }



}