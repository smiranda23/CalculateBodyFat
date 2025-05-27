package org.smcompany.calculbodyfat.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Straighten
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import calculatebodyfat.composeapp.generated.resources.Res
import calculatebodyfat.composeapp.generated.resources.info_how_to_measure
import calculatebodyfat.composeapp.generated.resources.info_intro
import calculatebodyfat.composeapp.generated.resources.info_needs
import calculatebodyfat.composeapp.generated.resources.info_results
import calculatebodyfat.composeapp.generated.resources.info_section_1
import calculatebodyfat.composeapp.generated.resources.info_section_2
import calculatebodyfat.composeapp.generated.resources.info_section_3
import moe.tlaster.precompose.navigation.Navigator
import org.jetbrains.compose.resources.stringResource
import org.smcompany.calculbodyfat.navigation.Routes

@Composable
fun InfoScreen(nav: Navigator, route: Routes) {
    Scaffold(
        //topBar = { MyTopAppBar(stringResource(Res.string.information)) },
        content = { paddingValues ->
            InfoBody(
                paddingValues
            )
        },
        bottomBar = { MyBottomBar(nav, route) }
    )
}

@Composable
fun InfoBody(paddingValues: PaddingValues) {
    val infoIntro = stringResource(Res.string.info_intro)
    val infoNeeds = stringResource(Res.string.info_needs)
    val infoHowToMeasure = stringResource(Res.string.info_how_to_measure)
    val infoResults = stringResource(Res.string.info_results)

    Column(
        modifier = Modifier
            .padding(paddingValues)
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.background)
            .verticalScroll(rememberScrollState())
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(16.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Info,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier.size(20.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = infoIntro,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground
            )
        }
        HorizontalDivider(
            modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
            thickness = 2.dp,
            color = MaterialTheme.colorScheme.onBackground
        )

        Column(modifier = Modifier.padding(16.dp)) {
            SectionTitle(
                stringResource(Res.string.info_section_1),
                Icons.AutoMirrored.Filled.List
            )
            Text(
                text = infoNeeds,
                style = MaterialTheme.typography.bodyMedium,
                lineHeight = 20.sp,
                color = MaterialTheme.colorScheme.onBackground
            )
            Spacer(modifier = Modifier.height(12.dp))

            SectionTitle(
                stringResource(Res.string.info_section_2),
                Icons.Default.Straighten
            )
            Text(
                text = infoHowToMeasure,
                style = MaterialTheme.typography.bodyMedium,
                lineHeight = 20.sp,
                color = MaterialTheme.colorScheme.onBackground
            )
            Spacer(modifier = Modifier.height(12.dp))

            SectionTitle(stringResource(Res.string.info_section_3), Icons.Default.BarChart)
            Text(
                text = infoResults,
                style = MaterialTheme.typography.bodyMedium,
                lineHeight = 20.sp,
                color = MaterialTheme.colorScheme.onBackground
            )
        }

    }

}

@Composable
fun SectionTitle(title: String, icon: ImageVector) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(bottom = 4.dp)
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier.size(20.dp)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = title,
            style = MaterialTheme.typography.titleSmall,
            fontWeight = FontWeight.SemiBold,
            color = MaterialTheme.colorScheme.onBackground
        )
    }
}