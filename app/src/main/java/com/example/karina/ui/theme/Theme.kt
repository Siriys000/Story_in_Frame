package com.example.karina.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

/*data class CustomColorScheme(
    val primary: Color,
    val secondary: Color,
    val text: Color,
    val border: Color,
    val background: Color,
    val surface: Color,
    val onSurface: Color,
    // Добавьте другие цвета по мере необходимости
)*/

private val DarkColorScheme = darkColorScheme(
    primary = Purple40,
    secondary = PurpleGrey80,
    tertiary = Pink80,
    surfaceVariant = PurpleDark40,
    onSurface = White // Используйте White, который у вас определен
)

// Исправленное определение LightColorScheme, использующее lightColorScheme
private val LightColorScheme = lightColorScheme(
    primary = Purple40, // Замените на ваш цвет primary для LightColorScheme
    secondary = PurpleGrey40, // Замените на ваш цвет secondary для LightColorScheme
    tertiary = LightSkyBlue, // Замените на ваш цвет tertiary для LightColorScheme
    surfaceVariant = PurpleDark40, // Замените на ваш цвет surfaceVariant для LightColorScheme
    onSurface = White // Замените на ваш цвет onSurface для LightColorScheme, например, Black для светлой темы
)

@Composable
fun KarinaTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}