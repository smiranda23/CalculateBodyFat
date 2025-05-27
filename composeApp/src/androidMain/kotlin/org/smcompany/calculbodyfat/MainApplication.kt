package org.smcompany.calculbodyfat

import android.app.Application
import com.calculatebodyfat.db.DBCalculate
import org.smcompany.calculbodyfat.data.DatabaseDriverCalculate
import org.smcompany.calculbodyfat.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class MainApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        //Inicializamos koin en android
        startKoin {
            androidContext(this@MainApplication)
            androidLogger()

            //Los modulos son lo que va a proveer de esa instancia
            modules(appModule(DBCalculate.invoke(DatabaseDriverCalculate(this@MainApplication).createDriver())))

        }

    }
}