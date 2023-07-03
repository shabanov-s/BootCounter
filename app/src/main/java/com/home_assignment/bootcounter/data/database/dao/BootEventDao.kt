package com.home_assignment.bootcounter.data.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.home_assignment.bootcounter.data.database.entities.BootEventEntity

@Dao
interface BootEventDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBootEvent(bootEvent: BootEventEntity)

    @Query("SELECT * FROM ${BootEventEntity.TABLE_NAME}")
    suspend fun getBootEvent(): List<BootEventEntity>
}