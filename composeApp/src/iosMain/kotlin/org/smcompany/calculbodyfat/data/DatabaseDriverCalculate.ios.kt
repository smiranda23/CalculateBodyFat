package org.smcompany.calculbodyfat.data

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.native.NativeSqliteDriver
import com.calculatebodyfat.db.DBCalculate

actual class DatabaseDriverCalculate {
    actual fun createDriver(): SqlDriver {
        return NativeSqliteDriver(
            schema = DBCalculate.Schema,
            name = "DBCalculate.db"
        )
    }
}