package com.tnvdeveloper.vitalis.data.local

import androidx.room.*
import com.tnvdeveloper.vitalis.data.model.BloodPressureRecord
import kotlinx.coroutines.flow.Flow

@Dao
interface BloodPressureDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(record: BloodPressureRecord)

    @Query("SELECT * FROM blood_pressure_records ORDER BY timestamp DESC")
    fun getAllRecords(): Flow<List<BloodPressureRecord>>

    @Delete
    suspend fun delete(record: BloodPressureRecord)
}
