package org.smcompany.calculbodyfat.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import calculatebodyfat.composeapp.generated.resources.Res
import calculatebodyfat.composeapp.generated.resources.athlete
import calculatebodyfat.composeapp.generated.resources.average
import calculatebodyfat.composeapp.generated.resources.bodyFat
import calculatebodyfat.composeapp.generated.resources.cancel
import calculatebodyfat.composeapp.generated.resources.date
import calculatebodyfat.composeapp.generated.resources.delete
import calculatebodyfat.composeapp.generated.resources.delete_calcul
import calculatebodyfat.composeapp.generated.resources.empty_list
import calculatebodyfat.composeapp.generated.resources.essential
import calculatebodyfat.composeapp.generated.resources.healthy
import calculatebodyfat.composeapp.generated.resources.history
import calculatebodyfat.composeapp.generated.resources.obesity
import calculatebodyfat.composeapp.generated.resources.overweight
import calculatebodyfat.composeapp.generated.resources.result
import calculatebodyfat.composeapp.generated.resources.sure_delete_calcul
import moe.tlaster.precompose.navigation.Navigator
import org.jetbrains.compose.resources.stringResource
import org.smcompany.calculbodyfat.model.Calcul
import org.smcompany.calculbodyfat.navigation.Routes
import org.smcompany.calculbodyfat.styles.colorBackgroundContent
import org.smcompany.calculbodyfat.styles.colorVerdeMilitar
import org.smcompany.calculbodyfat.viewmodel.HistoryViewModel

@Composable
fun HistoryScreen(nav: Navigator, historyViewModel: HistoryViewModel, route: Routes) {

    //Al entrar en pantalla, siempre ejecutará esta función
    LaunchedEffect(Unit) {
        historyViewModel.getHistory()
    }

    Scaffold(
        //topBar = { MyTopAppBar(stringResource(Res.string.history)) },
        content = { paddingValues ->
            HistoryBody(
                paddingValues,
                historyViewModel
            )
        },
        bottomBar = { MyBottomBar(nav, route) }
    )

    if (historyViewModel.popUpDetails.value && historyViewModel.selectedCalcul.value != null) {
        DetailsDialog(
            calcul = historyViewModel.selectedCalcul.value!!,
            onDismiss = { historyViewModel.hidePopUpDetails() },
            isMetric = historyViewModel.selectedCalcul.value!!.isMetric,
        )
    }

    if (historyViewModel.dialogDeleteItem.value && historyViewModel.selectedCalcul.value != null) {
        DeleteDialog(
            historyViewModel = historyViewModel
        )
    }

}

@Composable
fun DeleteDialog(historyViewModel: HistoryViewModel) {

    AlertDialog(
        onDismissRequest = { historyViewModel.hideDialogDelete() },
        title = {
            Text(
                text = stringResource(Res.string.delete_calcul),
                style = MaterialTheme.typography.titleLarge.copy(
                    color = colorVerdeMilitar,
                    fontWeight = FontWeight.Bold
                )
            )
        },
        text = {
            Text(
                text = stringResource(Res.string.sure_delete_calcul),
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = Color.Black
                )
            )
        },
        confirmButton = {
            TextButton(
                onClick = { historyViewModel.deleteItem() },
                colors = ButtonDefaults.textButtonColors(
                    contentColor = colorVerdeMilitar
                )
            ) {
                Text(
                    text = stringResource(Res.string.delete),
                    fontWeight = FontWeight.SemiBold
                )
            }
        },
        dismissButton = {
            TextButton(
                onClick = { historyViewModel.hideDialogDelete() },
                colors = ButtonDefaults.textButtonColors(
                    contentColor = Color.Gray
                )
            ) {
                Text(text = stringResource(Res.string.cancel))
            }
        },
        containerColor = colorBackgroundContent,
        shape = RoundedCornerShape(12.dp)
    )
}


@Composable
fun HistoryBody(
    paddingValues: PaddingValues,
    historyViewModel: HistoryViewModel
) {
    val list = historyViewModel.history

    Box(
        modifier = Modifier.fillMaxSize().padding(paddingValues)
            .background(color = MaterialTheme.colorScheme.background),
        contentAlignment = if (list.isNotEmpty()) Alignment.TopCenter else Alignment.Center
    ) {
        if (list.isNotEmpty()) {
            LazyColumn {
                items(list) {
                    ItemCalcul(it, historyViewModel)
                }
            }
        } else {
            Text(stringResource(Res.string.empty_list), fontWeight = FontWeight.Bold)
        }

    }
}

@Composable
fun ItemCalcul(c: Calcul, historyViewModel: HistoryViewModel) {

    val resultText = when (c.result) {
        "Esencial" -> stringResource(Res.string.essential)
        "Atleta" -> stringResource(Res.string.athlete)
        "Saludable" -> stringResource(Res.string.healthy)
        "Promedio" -> stringResource(Res.string.average)
        "Sobrepeso" -> stringResource(Res.string.overweight)
        else -> stringResource(Res.string.obesity)
    }

    val cardColor = when (c.result) {
        "Esencial" -> Color(0xFF8B0000)     // Rojo militar oscuro
        "Atleta" -> Color(0xFF1B5E20)       // Verde militar oscuro
        "Saludable" -> Color(0xFF2E7D32)    // Verde más brillante
        "Promedio" -> Color(0xFF616161)     // Gris medio, neutral
        "Sobrepeso" -> Color(0xFF8D6E63)    // Marrón grisáceo (tipo uniforme)
        "Obesidad" -> Color(0xFF5D4037)     // Marrón oscuro
        else -> Color(0xFF424242)           // Gris oscuro por defecto
    }

    val textColor = Color.White // o ajustable si usás fondo oscuro

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { historyViewModel.showPopUpDetails(c) },
        colors = CardDefaults.cardColors(containerColor = cardColor),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    stringResource(Res.string.date) + ": ${c.dateString}",
                    style = MaterialTheme.typography.bodySmall,
                    color = textColor
                )
                Text(
                    stringResource(Res.string.bodyFat) + ": ${c.bodyFat}%",
                    style = MaterialTheme.typography.titleMedium,
                    color = textColor
                )

                Text(
                    stringResource(Res.string.result) + ": $resultText",
                    style = MaterialTheme.typography.titleMedium,
                    color = textColor
                )
            }

            IconButton(onClick = { historyViewModel.showDialogDelete(c) }) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Delete item",
                    tint = Color.White
                )
            }

        }

    }

}




















