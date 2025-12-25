package com.tnvdeveloper.vitalis.screens

@Composable
fun HomeScreen(
    records: List<BloodPressureRecord>,
    onAddClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val lastRecord = records.firstOrNull()

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        // Карточка последнего измерения
        LastMeasurementCard(lastRecord)

        // Заголовок списка
        Text(
            text = "История измерений",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(horizontal = 4.dp)
        )

        // Список записей
        RecordsList(records = records.take(10))
    }

    // FAB по усмотрению (добавим)
    FloatingActionButton(
        onClick = onAddClick,
        modifier = Modifier
            .padding(16.dp)
            .align(Alignment.BottomEnd),
        containerColor = MaterialTheme.colorScheme.primary
    ) {
        Icon(
            imageVector = Icons.Default.Add,
            contentDescription = "Добавить",
            tint = Color.White
        )
    }
}

@Composable
private fun LastMeasurementCard(record: BloodPressureRecord?) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (record != null) {
                val statusColor = getPressureStatusColor(record.systolic, record.diastolic)

                Text(
                    text = "Последнее измерение",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                Spacer(modifier = Modifier.height(12.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    PressureIndicator(
                        value = "${record.systolic}",
                        label = "Сист.",
                        color = statusColor
                    )
                    PressureIndicator(
                        value = "${record.diastolic}",
                        label = "Диаст.",
                        color = statusColor
                    )
                    PressureIndicator(
                        value = "${record.pulse}",
                        label = "Пульс",
                        color = MaterialTheme.colorScheme.primary
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = record.timestamp.toLocalDate().toString(),
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            } else {
                Text(
                    text = "Нет измерений",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

@Composable
private fun PressureIndicator(value: String, label: String, color: Color) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = value,
            style = MaterialTheme.typography.headlineLarge,
            color = color
        )
        Text(
            text = label,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Composable
private fun RecordsList(records: List<BloodPressureRecord>) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(records) { record ->
            RecordItem(
                record = record,
                onDelete = { /* TODO */ }
            )
        }
    }
}

@Composable
private fun RecordItem(
    record: BloodPressureRecord,
    onDelete: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(
                    text = "${record.systolic}/${record.diastolic} мм рт. ст.",
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = "Пульс: ${record.pulse} уд/мин",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.primary
                )
                Text(
                    text = record.timestamp.toLocalTime().toString(),
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            IconButton(onClick = onDelete) {
                Icon(
                    Icons.Default.Delete,
                    contentDescription = "Удалить",
                    tint = MaterialTheme.colorScheme.error
                )
            }
        }
    }
}

fun getPressureStatusColor(systolic: Int, diastolic: Int): Color {
    val systolicStatus = when {
        systolic < 90 -> "LOW"
        systolic > 140 -> "HIGH"
        else -> "NORMAL"
    }
    return when (systolicStatus) {
        "LOW" -> Color(0xFFFF9800) // Оранжевый
        "HIGH" -> Color(0xFFF44336) // Красный
        else -> Color(0xFF4CAF50) // Зелёный
    }
}
