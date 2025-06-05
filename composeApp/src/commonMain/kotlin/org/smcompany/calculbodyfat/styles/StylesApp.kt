package org.smcompany.calculbodyfat.styles

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color


val colorVerdeMilitar = Color(0xFF5d6532)
val colorBackgroundContent = Color(0xFFe5e5e5) //Color.White
val colorOnBackground = Color.Black

val colorBackgroundTopAppBar = Color.Black //GREEN PETROLEUM
val colorBackgroundBottomBar = Color.Black //GREEN VERY DARK

val tintIconClicked = colorVerdeMilitar

private val LightColors = lightColorScheme(
    background = colorBackgroundContent,
    surface = colorBackgroundContent,
    onBackground = colorOnBackground
)

//MODO OSCURO

private val DarkColors = darkColorScheme(
    //background = Color.DarkGray,
    background = Color(0xFF3f3f3f),
    surface = Color.DarkGray,
    onBackground = Color.White,
)

@Composable
fun CalculBodyFatTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) DarkColors else LightColors

    MaterialTheme(
        colorScheme = colors,
        typography = MaterialTheme.typography,
        content = content
    )
}