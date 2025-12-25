package com.tnvdeveloper.vitalis.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDateTime

@Entity(tableName = "blood_pressure_records")
data class BloodPressureRecord(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val systolic: Int,          // Верхнее (систолическое)
    val diastolic: Int,         // Нижнее (диастолическое)
    val pulse: Int,             // Пульс
    val timestamp: LocalDateTime, // Дата и время записи
    val notes: String = "",     // Заметки

    // Опциональные параметры
    val weight: Float? = null,
    val temperature: Float? = null
)