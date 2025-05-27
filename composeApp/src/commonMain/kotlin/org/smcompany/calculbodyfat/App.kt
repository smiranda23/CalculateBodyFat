package org.smcompany.calculbodyfat

import androidx.compose.runtime.Composable
import moe.tlaster.precompose.PreComposeApp
import moe.tlaster.precompose.navigation.rememberNavigator
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.KoinContext
import org.smcompany.calculbodyfat.navigation.MyNavigation
import org.smcompany.calculbodyfat.styles.CalculBodyFatTheme

@Composable
@Preview
fun App() {

    //Importante que las variables vayan dentro de precomposeapp, si no peta, nos pasó con 'nav'
    PreComposeApp {
        CalculBodyFatTheme {
            KoinContext {
                val nav = rememberNavigator()
                MyNavigation(nav)
            }
        }
    }

}