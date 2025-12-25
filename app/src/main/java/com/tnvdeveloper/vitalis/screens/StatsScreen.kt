package com.tnvdeveloper.vitalis.screens

@Composable
fun StatsScreen(
    records: List<BloodPressureRecord>,
    selectedPeriod: Int = 7,
    onPeriodChange: (Int) -> Unit
) {
    val filteredRecords = filterRecordsByPeriod(records, selectedPeriod)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        // Фильтры по периодам
        PeriodFilter(
            selectedPeriod = selectedPeriod,
            onPeriodChange = onPeriodChange
        )

        // Компактные карточки средних значений
        AverageStatsCard(filteredRecords)

        // Круговая диаграмма
        PieChartSection(filteredRecords)
    }
}

@Composable
private fun PeriodFilter(
    selectedPeriod: Int,
    onPeriodChange: (Int) -> Unit
) {
    val periods = listOf(
        7 to "7 дней", 14 to "14 дней", 30 to "30 дней",
        60 to "2 мес", 90 to "3 мес", 180 to "6 мес",
        365 to "Год", -1 to "Все"
    )

    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(horizontal = 4.dp)
    ) {
        items(periods) { (days, label) ->
            FilterChip(
                selected = selectedPeriod == days,
                onClick = { onPeriodChange(days) },
                label = { Text(label) },
                colors = FilterChipDefaults.filterChipColors(
                    selectedContainerColor = MaterialTheme.colorScheme.primaryContainer
                )
            )
        }
    }
}

@Composable
private fun AverageStatsCard(records: List<BloodPressureRecord>) {
    val averages = calculateAverages(records)

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        StatCard("Сист.", "${averages.first} мм", MaterialTheme.colorScheme.primary)
        StatCard("Диаст.", "${averages.second} мм", MaterialTheme.colorScheme.secondary)
        StatCard("Пульс", "${averages.third} уд/мин", Color(0xFFFF9800))
    }
}

@Composable
private fun StatCard(label: String, value: String, color: Color) {
    Card(
        modifier = Modifier.weight(1f),
        colors = CardDefaults.cardColors(containerColor = color.copy(alpha = 0.1f))
    ) {
        Column(
            modifier = Modifier.padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(value, style = MaterialTheme.typography.headlineSmall, color = color)
            Text(label, style = MaterialTheme.typography.bodySmall)
        }
    }
}

@Composable
private fun PieChartSection(records: List<BloodPressureRecord>) {
    // TODO: Интеграция Vico Pie Chart
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(250.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Круговая диаграмма\n(интеграция Vico)",
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}
