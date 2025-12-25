package com.tnvdeveloper.vitalis.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "blood_pressure_records")
data class BloodPressureRecord(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val systolic: Int,
    val diastolic: Int,
    val pulse: Int,
    val timestamp: Date = Date(),
    val weight: Float? = null,
    val temperature: Float? = null,
    val bloodSugar: Float? = null,
    val notes: String = ""
)