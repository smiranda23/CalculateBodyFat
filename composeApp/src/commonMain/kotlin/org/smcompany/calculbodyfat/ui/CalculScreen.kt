package org.smcompany.calculbodyfat.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonColors
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ColorMatrix
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import calculatebodyfat.composeapp.generated.resources.Res
import calculatebodyfat.composeapp.generated.resources.calculate
import calculatebodyfat.composeapp.generated.resources.error_empty_field
import calculatebodyfat.composeapp.generated.resources.error_sum_waist_hip
import calculatebodyfat.composeapp.generated.resources.error_waist
import calculatebodyfat.composeapp.generated.resources.female
import calculatebodyfat.composeapp.generated.resources.femaleSoldier
import calculatebodyfat.composeapp.generated.resources.height
import calculatebodyfat.composeapp.generated.resources.hip
import calculatebodyfat.composeapp.generated.resources.imperial_in
import calculatebodyfat.composeapp.generated.resources.imperial_in_lb
import calculatebodyfat.composeapp.generated.resources.imperial_lb
import calculatebodyfat.composeapp.generated.resources.male
import calculatebodyfat.composeapp.generated.resources.maleSoldier
import calculatebodyfat.composeapp.generated.resources.metric_cm_kg
import calculatebodyfat.composeapp.generated.resources.metrico_cm
import calculatebodyfat.composeapp.generated.resources.metrico_kg
import calculatebodyfat.composeapp.generated.resources.neck
import calculatebodyfat.composeapp.generated.resources.waist
import calculatebodyfat.composeapp.generated.resources.weight
import moe.tlaster.precompose.navigation.Navigator
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.smcompany.calculbodyfat.nativo.NativeTextField
import org.smcompany.calculbodyfat.navigation.Routes
import org.smcompany.calculbodyfat.styles.colorVerdeMilitar
import org.smcompany.calculbodyfat.viewmodel.CalculViewModel

@Composable
fun CalculScreen(nav: Navigator, calculViewModel: CalculViewModel, route: Routes) {

    Scaffold(
        content = { paddingValues ->
            CalculBody(
                paddingValues,
                calculViewModel
            )
        },
        bottomBar = { MyBottomBar(nav, route) }
    )

    if (calculViewModel.popUpResult.value && calculViewModel.lastCalcul.value != null) {
        ResultDialog(
            calcul = calculViewModel.lastCalcul.value!!,
            onDismiss = { calculViewModel.hidePopUpResult() },
            onSeeHistory = {
                calculViewModel.hidePopUpResult()
                nav.navigate(Routes.History.route)
            },
            calculViewModel.isMetric.value
        )
    }
}

@Composable
fun CalculBody(
    paddingValues: PaddingValues,
    calculViewModel: CalculViewModel
) {

    Column(
        modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState())
            .padding(paddingValues).padding(8.dp)
            .background(color = MaterialTheme.colorScheme.background)
    ) {

        OptionsMaleFemale(calculViewModel)

        OptionsMetricImperial(calculViewModel)

        val weight = calculViewModel.weight.value.toFloat()
        val height = calculViewModel.height.value.toFloat()
        WeightAndHeight(
            weight,
            {
                calculViewModel.setWeight(it)
            },
            height,
            {
                calculViewModel.setHeight(it)
            },
            calculViewModel
        )

        val waist = calculViewModel.waist.value
        val neck = calculViewModel.neck.value
        val hip = calculViewModel.hip.value
        val isMale = calculViewModel.manSelected.value

        val errorMessage = when (calculViewModel.errorMessage.value) {
            "empty" -> stringResource(Res.string.error_empty_field)
            "error waist" -> stringResource(Res.string.error_waist)
            "error sum" -> stringResource(Res.string.error_sum_waist_hip)
            else -> ""
        }



        Measurements(
            waist,
            { calculViewModel.setWaist(it) },
            neck,
            { calculViewModel.setNeck(it) },
            hip,
            { calculViewModel.setHip(it) },
            isMale,
            errorMessage,
            calculViewModel
        )

        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            ButtonCalcul(calculViewModel)
        }

    }
}


@Composable
fun OptionsMaleFemale(calculViewModel: CalculViewModel) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.spacedBy(24.dp, Alignment.CenterHorizontally),
        verticalAlignment = Alignment.CenterVertically
    ) {
        GenderOption(
            image = painterResource(Res.drawable.maleSoldier),
            contentDescription = "MaleSoldier",
            onClick = { calculViewModel.onClickGenderMale() },
            selected = calculViewModel.manSelected.value,
            text = stringResource(Res.string.male)
        )

        GenderOption(
            image = painterResource(Res.drawable.femaleSoldier),
            contentDescription = "FemaleSoldier",
            onClick = { calculViewModel.onClickGenderFemale() },
            selected = calculViewModel.womenSelected.value,
            text = stringResource(Res.string.female)
        )
    }
}


@Composable
fun GenderOption(
    image: Painter,
    contentDescription: String,
    onClick: () -> Unit,
    selected: Boolean,
    text: String
) {

    val colorMatrix = if (selected) {
        ColorMatrix() // Sin efecto
    } else {
        ColorMatrix().apply { setToSaturation(0f) } // Desaturado
    }
    val border = if (selected) BorderStroke(3.dp, colorVerdeMilitar) else null

    Column {
        Card(
            modifier = Modifier
                .height(200.dp).width(150.dp)
                .clickable { onClick() },
            elevation = CardDefaults.cardElevation(8.dp),
            border = border
        ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.fillMaxSize()
            ) {
                Image(
                    image,
                    contentDescription = contentDescription,
                    contentScale = ContentScale.FillWidth,
                    modifier = Modifier.fillMaxSize(),
                    colorFilter = ColorFilter.colorMatrix(colorMatrix)
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = text,
            style = MaterialTheme.typography.bodyMedium.copy(
                fontWeight = if (selected) FontWeight.Bold else FontWeight.Normal,
                color = if (selected) colorVerdeMilitar else Color.Gray
            ),
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
    }

}


@Composable
fun OptionsMetricImperial(calculViewModel: CalculViewModel) {

    val selectedUnit = calculViewModel.isMetric.value

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            RadioButton(
                selected = selectedUnit,
                onClick = {
                    if (!selectedUnit) {
                        calculViewModel.setUnitSystem(true)
                    }

                },
                colors = RadioButtonColors(
                    selectedColor = MaterialTheme.colorScheme.onBackground,
                    unselectedColor = Color.Gray,
                    disabledSelectedColor = Color.Gray,
                    disabledUnselectedColor = Color.Gray
                )
            )
            Text(
                stringResource(Res.string.metric_cm_kg),
                color = MaterialTheme.colorScheme.onBackground
            )
        }

        Row(verticalAlignment = Alignment.CenterVertically) {
            RadioButton(
                selected = !selectedUnit,
                onClick = {
                    if (selectedUnit) {
                        calculViewModel.setUnitSystem(false)
                    }

                },
                colors = RadioButtonColors(
                    selectedColor = MaterialTheme.colorScheme.onBackground,
                    unselectedColor = Color.Gray,
                    disabledSelectedColor = Color.Gray,
                    disabledUnselectedColor = Color.Gray
                )
            )
            Text(
                stringResource(Res.string.imperial_in_lb),
                color = MaterialTheme.colorScheme.onBackground
            )
        }
    }
}


@Composable
fun WeightAndHeight(
    weight: Float,
    onWeightChange: (Float) -> Unit,
    height: Float,
    onHeightChange: (Float) -> Unit,
    calculViewModel: CalculViewModel
) {
    val metricSelected = calculViewModel.isMetric.value

    val unitWeight =
        if (metricSelected) stringResource(Res.string.metrico_kg) else stringResource(
            Res.string.imperial_lb
        )
    val unitHeight =
        if (metricSelected) stringResource(Res.string.metrico_cm) else stringResource(
            Res.string.imperial_in
        )
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {

        val myValueRangeWeight = if (metricSelected) 30f..150f else 66.1f..330.6f

        val myValueRangeHeight = if (metricSelected) 100f..250f else 39.37f..98.42f

        MeasurementSlider(
            label = stringResource(Res.string.weight),
            unit = unitWeight,
            value = weight,
            valueRange = myValueRangeWeight,
            steps = if (metricSelected) 170 else 100,
            onValueChange = { onWeightChange(it) }
        )

        MeasurementSlider(
            label = stringResource(Res.string.height),
            unit = unitHeight,
            value = height,
            valueRange = myValueRangeHeight,
            steps = if (metricSelected) 150 else 100,
            onValueChange = { onHeightChange(it) }
        )
    }
}

@Composable
fun MeasurementSlider(
    label: String,
    unit: String,
    value: Float,
    valueRange: ClosedFloatingPointRange<Float>,
    steps: Int,
    onValueChange: (Float) -> Unit
) {
    Column {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = "$label:", style = MaterialTheme.typography.titleMedium)
            Text(text = "${value.toInt()} $unit", style = MaterialTheme.typography.bodyMedium)
        }

        Slider(
            value = value,
            onValueChange = onValueChange,
            valueRange = valueRange,
            steps = steps,
            modifier = Modifier
                .fillMaxWidth()
                .height(32.dp), // Un poco más visible pero aún compacto
            colors = SliderDefaults.colors(
                thumbColor = colorVerdeMilitar, //Circulo del slider
                activeTrackColor = colorVerdeMilitar, //barra activa, a la izquierda
                inactiveTrackColor = colorVerdeMilitar, //barra inactiva, a la derecha
                activeTickColor = Color.LightGray, //steps activos
                inactiveTickColor = Color.Gray
            )
        )
    }
}

@Composable
fun Measurements(
    waist: String,
    onWaistChange: (String) -> Unit,
    neck: String,
    onNeckChange: (String) -> Unit,
    hip: String,
    onHipChange: (String) -> Unit,
    isMale: Boolean,
    errorMessage: String,
    calculViewModel: CalculViewModel
) {
    val metricSelected = calculViewModel.isMetric.value

    val unit =
        if (metricSelected) stringResource(Res.string.metrico_cm) else stringResource(Res.string.imperial_in)

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        var myLabelWaist = stringResource(Res.string.waist) + " ($unit)"

        NativeTextField(value = waist,
            onValueChange = onWaistChange,
            label = myLabelWaist,
            modifier = Modifier.fillMaxWidth(),
            backgroundColor = MaterialTheme.colorScheme.background
        )

//        OutlinedTextField(
//            value = waist,
//            onValueChange = onWaistChange,
//            label = { Text(stringResource(Res.string.waist) + " ($unit)") },
//            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
//            singleLine = true,
//            modifier = Modifier.fillMaxWidth(),
//            colors = TextFieldDefaults.colors(
//                focusedLabelColor = MaterialTheme.colorScheme.onBackground,
//                focusedIndicatorColor = Color.LightGray,
//                cursorColor = MaterialTheme.colorScheme.onBackground
//            )
//        )

        val myLabelNeck = stringResource(Res.string.neck) + " ($unit)"

        NativeTextField(value = neck,
            onValueChange = onNeckChange,
            label = myLabelNeck,
            modifier = Modifier.fillMaxWidth(),
            backgroundColor = MaterialTheme.colorScheme.background
        )
//        OutlinedTextField(
//            value = neck,
//            onValueChange = onNeckChange,
//            label = { Text(stringResource(Res.string.neck) + " ($unit)") },
//            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
//            singleLine = true,
//            modifier = Modifier.fillMaxWidth(),
//            colors = TextFieldDefaults.colors(
//                focusedLabelColor = MaterialTheme.colorScheme.onBackground,
//                focusedIndicatorColor = Color.LightGray,
//                cursorColor = MaterialTheme.colorScheme.onBackground
//            )
//        )

        if (!isMale) {
            val myLabelHip = stringResource(Res.string.hip) + " ($unit)"

            NativeTextField(
                value = hip,
                onValueChange = onHipChange,
                label = myLabelHip,
                modifier = Modifier.fillMaxWidth(),
                backgroundColor = MaterialTheme.colorScheme.background
            )
//            OutlinedTextField(
//                value = hip,
//                onValueChange = onHipChange,
//                label = { Text(stringResource(Res.string.hip) + " ($unit)") },
//                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
//                singleLine = true,
//                modifier = Modifier.fillMaxWidth(),
//                colors = TextFieldDefaults.colors(
//                    focusedLabelColor = MaterialTheme.colorScheme.onBackground,
//                    focusedIndicatorColor = Color.LightGray,
//                    cursorColor = MaterialTheme.colorScheme.onBackground
//                )
//            )
        }
        Text(text = errorMessage, color = Color.Red)
    }
}

@Composable
fun ButtonCalcul(calculViewModel: CalculViewModel) {
    Button(
        onClick = { calculViewModel.onclickButtonCalc() },
        colors = ButtonDefaults.buttonColors(
            containerColor = colorVerdeMilitar,
            contentColor = Color.White
        )
    ) {
        Text(stringResource(Res.string.calculate))
    }
}