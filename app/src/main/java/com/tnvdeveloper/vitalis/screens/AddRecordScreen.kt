package com.tnvdeveloper.vitalis.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.tnvdeveloper.vitalis.data.model.BloodPressureRecord
import kotlinx.datetime.LocalDateTime

@Composable
fun AddRecordScreen(
    onSave: (BloodPressureRecord) -> Unit,
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    var systolic by remember { mutableStateOf("") }
    var diastolic by remember { mutableStateOf("") }
    var pulse by remember { mutableStateOf("") }
    var weight by remember { mutableStateOf("") }
    var temperature by remember { mutableStateOf("") }
    var notes by remember { mutableStateOf("") }

    val systolicError = validateSystolic(systolic.toIntOrNull())
    val diastolicError = validateDiastolic(diastolic.toIntOrNull())
    val pulseError = validatePulse(pulse.toIntOrNull())

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(20.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "Новое измерение",
            style = MaterialTheme.typography.headlineMedium
        )

        // Основные поля
        OutlinedTextField(
            value = systolic,
            onValueChange = { systolic = it.filter { c -> c.isDigit() } },
            label = { Text("Систолическое (мм рт. ст.)") },
            isError = systolicError != null,
            modifier = Modifier.fillMaxWidth()
        )
        if (systolicError != null) {
            Text(systolicError, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.error)
        }

        OutlinedTextField(
            value = diastolic,
            onValueChange = { diastolic = it.filter { c -> c.isDigit() } },
            label = { Text("Диастолическое (мм рт. ст.)") },
            isError = diastolicError != null,
            modifier = Modifier.fillMaxWidth()
        )
        if (diastolicError != null) {
            Text(diastolicError, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.error)
        }

        OutlinedTextField(
            value = pulse,
            onValueChange = { pulse = it.filter { c -> c.isDigit() } },
            label = { Text("Пульс (уд/мин)") },
            isError = pulseError != null,
            modifier = Modifier.fillMaxWidth()
        )
        if (pulseError != null) {
            Text(pulseError, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.error)
        }

        // Опциональные поля (сворачиваемые)
        ExpandableSection(
            title = "Дополнительно",
            content = {
                OutlinedTextField(
                    value = weight,
                    onValueChange = { weight = it },
                    label = { Text("Вес (кг)") },
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = temperature,
                    onValueChange = { temperature = it },
                    label = { Text("Температура (°C)") },
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = notes,
                    onValueChange = { notes = it },
                    label = { Text("Заметки") },
                    maxLines = 3,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(100.dp)
                )
            }
        )

        Spacer(modifier = Modifier.height(20.dp))

        Button(
            onClick = {
                if (systolicError == null && diastolicError == null && pulseError == null) {
                    val record = BloodPressureRecord(
                        systolic = systolic.toInt(),
                        diastolic = diastolic.toInt(),
                        pulse = pulse.toInt(),
                        timestamp = LocalDateTime.now(),
                        weight = weight.toFloatOrNull(),
                        temperature = temperature.toFloatOrNull()
                    )
                    onSave(record)
                }
            },
            modifier = Modifier.fillMaxWidth(),
            enabled = systolicError == null && diastolicError == null && pulseError == null
        ) {
            Text("Сохранить")
        }
    }
}

// Вспомогательные функции валидации
private fun validateSystolic(value: Int?): String? = when {
    value == null -> "Введите значение"
    value < 40 || value > 250 -> "40-250 мм рт. ст."
    else -> null
}

private fun validateDiastolic(value: Int?): String? = when {
    value == null -> "Введите значение"
    value < 20 || value > 150 -> "20-150 мм рт. ст."
    else -> null
}

private fun validatePulse(value: Int?): String? = when {
    value == null -> "Введите значение"
    value < 30 || value > 200 -> "30-200 уд/мин"
    else -> null
}
