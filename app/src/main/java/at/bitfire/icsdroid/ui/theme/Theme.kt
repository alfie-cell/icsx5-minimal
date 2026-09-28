/*
 * Copyright © All Contributors. See LICENSE and AUTHORS in the root directory for details.
 */

package at.bitfire.icsdroid.ui.theme

import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionContext
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.LifecycleResumeEffect
import at.bitfire.icsdroid.model.ThemeModel
import at.bitfire.icsdroid.ui.ForegroundTracker

private val MinimalColors = darkColorScheme(
    primary = text,
    onPrimary = ink,
    primaryContainer = inkHighest,
    onPrimaryContainer = text,
    secondary = textSecondary,
    onSecondary = ink,
    secondaryContainer = inkHigh,
    onSecondaryContainer = text,
    tertiary = textSecondary,
    onTertiary = ink,
    tertiaryContainer = inkHigh,
    onTertiaryContainer = text,
    background = ink,
    onBackground = text,
    surface = ink,
    onSurface = text,
    surfaceVariant = inkHigh,
    onSurfaceVariant = textMuted,
    surfaceTint = ink,
    surfaceContainerLowest = ink,
    surfaceContainerLow = inkRaised,
    surfaceContainer = inkRaised,
    surfaceContainerHigh = inkHigh,
    surfaceContainerHighest = inkHighest,
    inverseSurface = text,
    inverseOnSurface = ink,
    outline = hairline,
    outlineVariant = hairline,
    error = red,
    onError = ink,
)

// Rounded like the launcher's search pill and grid highlights.
private val MinimalShapes = Shapes(
    extraSmall = RoundedCornerShape(8.dp),
    small = RoundedCornerShape(12.dp),
    medium = RoundedCornerShape(16.dp),
    large = RoundedCornerShape(24.dp),
    extraLarge = RoundedCornerShape(28.dp),
)

@Composable
fun AppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    // Always the launcher's dark look; darkTheme is kept only for API compatibility.
    MaterialTheme(
        colorScheme = MinimalColors,
        shapes = MinimalShapes,
        content = content
    )

    // Track whether the app is in the foreground
    val view = LocalView.current
    LifecycleResumeEffect(view) {
        ForegroundTracker.onResume()
        onPauseOrDispose {
            ForegroundTracker.onPaused()
        }
    }
}

/**
 * Composes the given composable into the given activity. The content will become the root view of
 * the given activity.
 * This is roughly equivalent to calling [ComponentActivity.setContentView] with a ComposeView i.e.:
 * ```kotlin
 * setContentView(
 *   ComposeView(this).apply {
 *     setContent {
 *       MyComposableContent()
 *     }
 *   }
 * )
 * ```
 *
 * Then, applies [AppTheme] to the UI.
 *
 * @param parent The parent composition reference to coordinate scheduling of composition updates
 * @param darkTheme Calculates whether the UI should be shown in light or dark theme.
 * @param content A `@Composable` function declaring the UI contents
 */
fun ComponentActivity.setContentThemed(
    parent: CompositionContext? = null,
    darkTheme: @Composable () -> Boolean = {
        val model = hiltViewModel<ThemeModel>()
        val forceDarkTheme by model.forceDarkMode.collectAsState()
        forceDarkTheme || isSystemInDarkTheme()
    },
    content: @Composable () -> Unit
) {
    setContent(parent) {
        AppTheme(darkTheme = darkTheme()) {
            content()
        }
    }
}
