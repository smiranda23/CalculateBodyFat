package org.smcompany.calculbodyfat.nativo

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
expect fun NativeTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label:  String,
    modifier: Modifier = Modifier,
    backgroundColor: Color
)