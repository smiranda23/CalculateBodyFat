package org.smcompany.calculbodyfat.di

import com.calculatebodyfat.db.DBCalculate
import org.smcompany.calculbodyfat.data.GlobalRepository
import org.smcompany.calculbodyfat.interfaces.IGlobalRepository
import org.koin.dsl.module
import org.smcompany.calculbodyfat.viewmodel.CalculViewModel
import org.smcompany.calculbodyfat.viewmodel.HistoryViewModel

fun appModule(dbCalculate: DBCalculate) = module {
    single<IGlobalRepository> { GlobalRepository(dbCalculate) }

    factory { CalculViewModel(get()) }
    factory { HistoryViewModel(get()) }
}