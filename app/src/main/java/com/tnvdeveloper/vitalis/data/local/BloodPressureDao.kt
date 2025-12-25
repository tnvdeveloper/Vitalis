package com.tnvdeveloper.vitalis.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.tnvdeveloper.vitalis.data.model.BloodPressureRecord
import kotlinx.coroutines.flow.Flow

@Dao
interface BloodPressureDao {

    @Insert(onConflict = OnConflictStrategy.Companion.REPLACE)
    suspend fun insertRecord(record: BloodPressureRecord)

    @Update
    suspend fun updateRecord(record: BloodPressureRecord)

    @Delete
    suspend fun deleteRecord(record: BloodPressureRecord)

    // Flow автоматически обновляет UI при изменении данных в БД
    @Query("SELECT * FROM blood_pressure_records ORDER BY timestamp DESC")
    fun getAllRecords(): Flow<List<BloodPressureRecord>>

    // Запрос для статистики за определенный период (фильтрация делается на уровне SQL для скорости)
    @Query("SELECT * FROM blood_pressure_records WHERE timestamp >= :startDate ORDER BY timestamp DESC")
    fun getRecordsSince(startDate: Long): Flow<List<BloodPressureRecord>>
}