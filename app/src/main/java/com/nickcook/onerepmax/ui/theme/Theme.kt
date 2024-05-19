package com.nickcook.onerepmax.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val LightColorScheme = lightColorScheme(
    primary = OneRepMaxPrimary,
    onPrimary = OneRepMaxOnPrimary,
    primaryContainer = OneRepMaxPrimaryContainer,
    onPrimaryContainer = OneRepMaxOnPrimaryContainer,
    secondary = OneRepMaxSecondary,
    onSecondary = OneRepMaxOnSecondary,
    secondaryContainer = OneRepMaxSecondaryContainer,
    onSecondaryContainer = OneRepMaxOnSecondaryContainer,
    tertiary = OneRepMaxTertiary,
    onTertiary = OneRepMaxOnTertiary,
    tertiaryContainer = OneRepMaxOnTertiaryContainer,
    onTertiaryContainer = OneRepMaxOnTertiaryContainer,
    error = OneRepMaxError,
    errorContainer = OneRepMaxErrorContainer,
    onError = OneRepMaxOnError,
    onErrorContainer = OneRepMaxOnErrorContainer,
    background = OneRepMaxBackground,
    onBackground = OneRepMaxOnBackground,
    surface = OneRepMaxSurface,
    onSurface = OneRepMaxOnSurface,
    surfaceVariant = OneRepMaxSurfaceVariant,
    onSurfaceVariant = OneRepMaxOnSurfaceVariant,
    outline = OneRepMaxOutline,
    inverseOnSurface = OneRepMaxInverseOnSurface,
    inverseSurface = OneRepMaxInverseSurface,
    inversePrimary = OneRepMaxInversePrimary,
    surfaceTint = OneRepMaxSurfaceTint,
    outlineVariant = OneRepMaxOutlineVariant,
    scrim = OneRepMaxScrim
)


private val DarkColorScheme = darkColorScheme(
    primary = OneRepMaxPrimaryDark,
    onPrimary = OneRepMaxOnPrimaryDark,
    primaryContainer = OneRepMaxPrimaryContainerDark,
    onPrimaryContainer = OneRepMaxOnPrimaryContainerDark,
    secondary = OneRepMaxSecondaryDark,
    onSecondary = OneRepMaxOnSecondaryDark,
    secondaryContainer = OneRepMaxSecondaryContainerDark,
    onSecondaryContainer = OneRepMaxOnSecondaryContainerDark,
    tertiary = OneRepMaxTertiaryDark,
    onTertiary = OneRepMaxOnTertiaryDark,
    tertiaryContainer = OneRepMaxOnTertiaryContainerDark,
    onTertiaryContainer = OneRepMaxOnTertiaryContainerDark,
    error = OneRepMaxErrorDark,
    errorContainer = OneRepMaxErrorContainerDark,
    onError = OneRepMaxOnErrorDark,
    onErrorContainer = OneRepMaxOnErrorContainerDark,
    background = OneRepMaxBackgroundDark,
    onBackground = OneRepMaxOnBackgroundDark,
    surface = OneRepMaxSurfaceDark,
    onSurface = OneRepMaxOnSurfaceDark,
    surfaceVariant = OneRepMaxSurfaceVariantDark,
    onSurfaceVariant = OneRepMaxOnSurfaceVariantDark,
    outline = OneRepMaxOutlineDark,
    inverseOnSurface = OneRepMaxInverseOnSurfaceDark,
    inverseSurface = OneRepMaxInverseSurfaceDark,
    inversePrimary = OneRepMaxInversePrimaryDark,
    surfaceTint = OneRepMaxSurfaceTintDark,
    outlineVariant = OneRepMaxOutlineVariantDark,
    scrim = OneRepMaxScrimDark
)

@Composable
fun OneRepMaxTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
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
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.primary.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}