package org.smcompany.calculbodyfat.navigation

import androidx.compose.runtime.Composable
import moe.tlaster.precompose.koin.koinViewModel
import moe.tlaster.precompose.navigation.NavHost
import moe.tlaster.precompose.navigation.Navigator
import org.koin.core.parameter.parametersOf
import org.smcompany.calculbodyfat.ui.CalculScreen
import org.smcompany.calculbodyfat.ui.HistoryScreen
import org.smcompany.calculbodyfat.ui.InfoScreen
import org.smcompany.calculbodyfat.viewmodel.CalculViewModel
import org.smcompany.calculbodyfat.viewmodel.HistoryViewModel

@Composable
fun MyNavigation(nav: Navigator) {

    val calculViewModel = koinViewModel(CalculViewModel::class) { parametersOf() }
    val historyViewModel = koinViewModel(HistoryViewModel::class) { parametersOf() }



    NavHost(nav, initialRoute = Routes.Calcul.route) {
        scene(Routes.Info.route) {
            InfoScreen(nav,Routes.Info)
        }
        scene(Routes.Calcul.route) {
            CalculScreen(nav, calculViewModel, Routes.Calcul)
        }
        scene(Routes.History.route) {
            HistoryScreen(nav, historyViewModel, Routes.History)
        }
    }
}