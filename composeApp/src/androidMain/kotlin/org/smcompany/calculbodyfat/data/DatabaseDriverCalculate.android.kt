package org.smcompany.calculbodyfat.data

import android.content.Context
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import com.calculatebodyfat.db.DBCalculate

actual class DatabaseDriverCalculate(private val context: Context) {
    actual fun createDriver(): SqlDriver {
        return AndroidSqliteDriver(
            schema = DBCalculate.Schema,
            context = context,
            name = "DBCalculate.db"
        )
    }
}