package com.home_assignment.bootcounter.data.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.home_assignment.bootcounter.data.database.entities.BootEventEntity.Companion.TABLE_NAME

@Entity(tableName = TABLE_NAME)
data class BootEventEntity(
    @PrimaryKey
    @ColumnInfo(name = TIMESTAMP)
    val timestamp: Long
) {
    companion object {
        internal const val TABLE_NAME = "boot_events"
        internal const val TIMESTAMP = "${TABLE_NAME}_timestamp"
    }
}