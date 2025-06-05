package org.smcompany.calculbodyfat

import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.window.ComposeUIViewController
import com.calculatebodyfat.db.DBCalculate
import org.koin.core.context.startKoin
import org.smcompany.calculbodyfat.data.DatabaseDriverCalculate
import org.smcompany.calculbodyfat.di.appModule
import org.smcompany.calculbodyfat.nativo.NativeTextViewFactory

val LocalNativeViewFactory = staticCompositionLocalOf<NativeTextViewFactory> {
    error("No view factory TEST provided.")
}

fun MainViewController(
    nativeTextViewFactory: NativeTextViewFactory
) = ComposeUIViewController {
    CompositionLocalProvider(LocalNativeViewFactory provides nativeTextViewFactory) {
        App()
    }
}

fun initKoin(){
    startKoin {
        modules(appModule(DBCalculate.invoke(DatabaseDriverCalculate().createDriver())))
    }.koin
}