package com.home_assignment.bootcounter.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.home_assignment.bootcounter.data.database.dao.BootEventDao
import com.home_assignment.bootcounter.data.database.entities.BootEventEntity

@Database(entities = [BootEventEntity::class], version = 1)
abstract class BootEventDatabase : RoomDatabase() {
    abstract fun bootEventDao(): BootEventDao

    companion object {
        private const val DATABASE_NAME = "boot_event_db"

        @Volatile
        private var instance: BootEventDatabase? = null

        fun getInstance(context: Context): BootEventDatabase {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also { instance = it }
            }
        }

        private fun buildDatabase(context: Context): BootEventDatabase {
            return Room.databaseBuilder(context, BootEventDatabase::class.java, DATABASE_NAME)
                .build()
        }
    }
}