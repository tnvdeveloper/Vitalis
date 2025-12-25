package com.tnvdeveloper.vitalis.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.tnvdeveloper.vitalis.data.model.BloodPressureRecord

@Database(
    entities = [BloodPressureRecord::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun bloodPressureDao(): BloodPressureDao
}