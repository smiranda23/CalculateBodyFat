package org.smcompany.calculbodyfat.nativo

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.UIKitViewController
import org.smcompany.calculbodyfat.LocalNativeViewFactory

@Composable
actual fun NativeTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    modifier: Modifier,
    backgroundColor: Color
) {

    val factory = LocalNativeViewFactory.current

    UIKitViewController(
        modifier = modifier.fillMaxWidth().height(100.dp),
        factory = {
            factory.createTextFieldView(
                valor = value,
                onValueChange = onValueChange,
                label = label,
                backgroundColor = backgroundColor
            )
        }
    )
}