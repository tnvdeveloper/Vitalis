package com.tnvdeveloper.vitalis.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme = darkColorScheme(
    primary = Purple80,
    secondary = PurpleGrey80,
    tertiary = Pink80
)

private val LightColorScheme = lightColorScheme(
    primary = Purple40,
    secondary = PurpleGrey40,
    tertiary = Pink40

    /* Other default colors to override
    background = Color(0xFFFFFBFE),
    surface = Color(0xFFFFFBFE),
    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.White,
    onBackground = Color(0xFF1C1B1F),
    onSurface = Color(0xFF1C1B1F),
    */
)

@Composable
fun VitalisTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        darkTheme -> darkColorScheme(
            primary = Color(0xFF4CAF50), // Мятный основной
            secondary = Color(0xFF81C784),
            background = Color(0xFF121212),
            surface = Color(0xFF1E1E1E),
            error = Color(0xFFCF6679),
            onPrimary = Color.White,
            onSecondary = Color.Black,
            onBackground = Color.White,
            onSurface = Color.White
        )
        else -> lightColorScheme(
            primary = Color(0xFF26C6DA), // Мятный светлый
            secondary = Color(0xFF4DB6AC),
            tertiary = Color(0xFF80DEEA),
            background = Color(0xFFF8FDFC),
            surface = Color.White,
            error = Color(0xFFCF6679),
            onPrimary = Color.White,
            onSecondary = Color.Black,
            onBackground = Color(0xFF1A1A1A),
            onSurface = Color(0xFF1A1A1A)
        )
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography(),
        content = content
    )
}
