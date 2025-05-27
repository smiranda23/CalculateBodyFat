package org.smcompany.calculbodyfat.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Assignment
import androidx.compose.material.icons.filled.Calculate
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import calculatebodyfat.composeapp.generated.resources.Res
import calculatebodyfat.composeapp.generated.resources.athlete
import calculatebodyfat.composeapp.generated.resources.average
import calculatebodyfat.composeapp.generated.resources.bodyFat
import calculatebodyfat.composeapp.generated.resources.close
import calculatebodyfat.composeapp.generated.resources.details_calcul
import calculatebodyfat.composeapp.generated.resources.essential
import calculatebodyfat.composeapp.generated.resources.fatMass
import calculatebodyfat.composeapp.generated.resources.female
import calculatebodyfat.composeapp.generated.resources.gender
import calculatebodyfat.composeapp.generated.resources.healthy
import calculatebodyfat.composeapp.generated.resources.height
import calculatebodyfat.composeapp.generated.resources.hip
import calculatebodyfat.composeapp.generated.resources.imperial_in
import calculatebodyfat.composeapp.generated.resources.imperial_lb
import calculatebodyfat.composeapp.generated.resources.leanMass
import calculatebodyfat.composeapp.generated.resources.male
import calculatebodyfat.composeapp.generated.resources.metrico_cm
import calculatebodyfat.composeapp.generated.resources.metrico_kg
import calculatebodyfat.composeapp.generated.resources.neck
import calculatebodyfat.composeapp.generated.resources.obesity
import calculatebodyfat.composeapp.generated.resources.overweight
import calculatebodyfat.composeapp.generated.resources.result
import calculatebodyfat.composeapp.generated.resources.results
import calculatebodyfat.composeapp.generated.resources.see_history
import calculatebodyfat.composeapp.generated.resources.state
import calculatebodyfat.composeapp.generated.resources.waist
import calculatebodyfat.composeapp.generated.resources.weight
import moe.tlaster.precompose.navigation.NavOptions
import moe.tlaster.precompose.navigation.Navigator
import org.jetbrains.compose.resources.stringResource
import org.smcompany.calculbodyfat.model.Calcul
import org.smcompany.calculbodyfat.navigation.Routes
import org.smcompany.calculbodyfat.styles.colorBackgroundBottomBar
import org.smcompany.calculbodyfat.styles.colorBackgroundTopAppBar
import org.smcompany.calculbodyfat.styles.tintIconClicked
import org.smcompany.calculbodyfat.utilities.Gender

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyTopAppBar(title: String) {
    TopAppBar(
        title = { Text(title) },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = colorBackgroundTopAppBar,
            titleContentColor = Color.White
        )
    )
}

@Composable
fun MyBottomBar(nav: Navigator, route: Routes) {
    BottomAppBar(
        containerColor = colorBackgroundBottomBar
    ) {
        ContentMyBottomBar(nav, route)
    }
}

@Composable
fun ContentMyBottomBar(nav: Navigator, route: Routes) {

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                onClick = {
                    nav.navigate(
                        Routes.Info.route,
                        options = NavOptions(
                            launchSingleTop = true // Evitar múltiples instancias
                        )
                    )

                },
            ) {
                Icon(
                    Icons.Default.Info,
                    contentDescription = "Info",
                    tint = if (route == Routes.Info) tintIconClicked else Color.White,
                    modifier = Modifier.size(42.dp)
                )
            }

            Spacer(Modifier.width(30.dp))

            IconButton(
                onClick = {
                    nav.navigate(
                        Routes.Calcul.route,
                        options = NavOptions(
                            launchSingleTop = true // Evitar múltiples instancias
                        )
                    )

                },
            ) {
                Icon(
                    Icons.Default.Calculate,
                    contentDescription = "Calcul",
                    tint = if (route == Routes.Calcul) tintIconClicked else Color.White,
                    modifier = Modifier.size(42.dp)
                )
            }

            Spacer(Modifier.width(30.dp))

            IconButton(
                onClick = {
                    nav.navigate(
                        Routes.History.route,
                        options = NavOptions(
                            launchSingleTop = true // Evitar múltiples instancias
                        )
                    )

                },
            ) {
                Icon(
                    Icons.AutoMirrored.Filled.Assignment,
                    contentDescription = "History",
                    tint = if (route == Routes.History) tintIconClicked else Color.White,
                    modifier = Modifier.size(42.dp)
                )
            }
        }
    }
}

@Composable
fun ResultDialog(
    calcul: Calcul,
    onDismiss: () -> Unit,
    onSeeHistory: () -> Unit,
    isMetric: Boolean
) {

    val unitDistance =
        if (isMetric) stringResource(Res.string.metrico_cm) else stringResource(Res.string.imperial_in)
    val unitWeight =
        if (isMetric) stringResource(Res.string.metrico_kg) else stringResource(Res.string.imperial_lb)

    val resultText = when (calcul.result) {
        "Esencial" -> stringResource(Res.string.essential)
        "Atleta" -> stringResource(Res.string.athlete)
        "Saludable" -> stringResource(Res.string.healthy)
        "Promedio" -> stringResource(Res.string.average)
        "Sobrepeso" -> stringResource(Res.string.overweight)
        else -> stringResource(Res.string.obesity)
    }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                stringResource(Res.string.results),
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )
        },
        text = {
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(stringResource(Res.string.state) + ": $resultText")
                Text(stringResource(Res.string.bodyFat) + ": ${calcul.bodyFat}%")
                Text(stringResource(Res.string.fatMass) + ": ${calcul.fatMass} $unitWeight")
                Text(stringResource(Res.string.leanMass) + ": ${calcul.leanMass} $unitWeight")
                HorizontalDivider()
                Text(stringResource(Res.string.weight) + ": ${calcul.weight} $unitWeight")
                Text(stringResource(Res.string.height) + ": ${calcul.height} $unitDistance")
                if (calcul.gender == Gender.FEMALE.name) {
                    Text(stringResource(Res.string.waist) + ": ${calcul.waist} $unitDistance")
                    Text(stringResource(Res.string.neck) + ": ${calcul.neck} $unitDistance")
                    Text(stringResource(Res.string.hip) + ": ${calcul.hip} $unitDistance")
                } else {
                    Text(stringResource(Res.string.waist) + ": ${calcul.waist} $unitDistance")
                    Text(stringResource(Res.string.neck) + ": ${calcul.neck} $unitDistance")
                }
            }
        },
        confirmButton = {
            Button(onClick = onSeeHistory) {
                Text(stringResource(Res.string.see_history))
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(
                    stringResource(Res.string.close),
                    color = MaterialTheme.colorScheme.onBackground,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    )
}

//FOR SCREEN HISTORY
@Composable
fun DetailsDialog(
    calcul: Calcul,
    onDismiss: () -> Unit,
    isMetric: Boolean
) {
    val unitDistance =
        if (isMetric) stringResource(Res.string.metrico_cm) else stringResource(Res.string.imperial_in)
    val unitWeight =
        if (isMetric) stringResource(Res.string.metrico_kg) else stringResource(Res.string.imperial_lb)

    val resultText = when (calcul.result) {
        "Esencial" -> stringResource(Res.string.essential)
        "Atleta" -> stringResource(Res.string.athlete)
        "Saludable" -> stringResource(Res.string.healthy)
        "Promedio" -> stringResource(Res.string.average)
        "Sobrepeso" -> stringResource(Res.string.overweight)
        else -> stringResource(Res.string.obesity)
    }

    val gender =
        if (calcul.gender == Gender.MALE.name) stringResource(Res.string.male) else stringResource(
            Res.string.female
        )

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                stringResource(Res.string.details_calcul),
                color = MaterialTheme.colorScheme.onBackground,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )
        },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {

                Text(
                    stringResource(Res.string.gender) + ": $gender",
                    color = MaterialTheme.colorScheme.onBackground,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold
                )

                Text(
                    stringResource(Res.string.bodyFat) + ": ${calcul.bodyFat} %",
                    color = MaterialTheme.colorScheme.onBackground
                )
                Text(
                    stringResource(Res.string.fatMass) + ": ${calcul.fatMass} $unitWeight",
                    color = MaterialTheme.colorScheme.onBackground
                )
                Text(
                    stringResource(Res.string.leanMass) + ": ${calcul.leanMass} $unitWeight",
                    color = MaterialTheme.colorScheme.onBackground
                )
                HorizontalDivider()
                Text(
                    stringResource(Res.string.weight) + ": ${calcul.weight} $unitWeight",
                    color = MaterialTheme.colorScheme.onBackground
                )
                Text(
                    stringResource(Res.string.height) + ": ${calcul.height} $unitDistance",
                    color = MaterialTheme.colorScheme.onBackground
                )
                Text(
                    stringResource(Res.string.waist) + ": ${calcul.waist} $unitDistance",
                    color = MaterialTheme.colorScheme.onBackground
                )
                Text(
                    stringResource(Res.string.neck) + ": ${calcul.neck} $unitDistance",
                    color = MaterialTheme.colorScheme.onBackground
                )
                if (calcul.gender == Gender.FEMALE.name) {
                    Text(
                        stringResource(Res.string.hip) + ": ${calcul.hip} $unitDistance",
                        color = MaterialTheme.colorScheme.onBackground
                    )
                }
                HorizontalDivider()
                Text(
                    stringResource(Res.string.result) + ": $resultText",
                    color = MaterialTheme.colorScheme.onBackground,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                )
            }
        },
        confirmButton = {
            TextButton(onClick = onDismiss) {
                Text(
                    stringResource(Res.string.close),
                    color = MaterialTheme.colorScheme.onBackground,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }
    )
}
