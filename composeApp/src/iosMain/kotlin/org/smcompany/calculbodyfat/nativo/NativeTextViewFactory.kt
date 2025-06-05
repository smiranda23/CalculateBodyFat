package org.smcompany.calculbodyfat.nativo

import androidx.compose.ui.graphics.Color
import platform.UIKit.UIViewController

interface NativeTextViewFactory {
    fun createTextFieldView(
        valor: String,
        onValueChange: (String) -> Unit,
        label: String,
        backgroundColor : Color
    ):UIViewController
}