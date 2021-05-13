package com.alvarosct.demo.reddit.data.source.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.alvarosct.demo.reddit.data.source.local.dao.PostDao
import com.alvarosct.demo.reddit.data.source.local.entities.PostEntity


@Database(entities = [PostEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun postDao(): PostDao

    companion object {
        fun buildAppDatabase(context: Context, dbName: String): AppDatabase {
            return Room.databaseBuilder(
                context,
                AppDatabase::class.java,
                dbName
            ).build()
        }
    }
}