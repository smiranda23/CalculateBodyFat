package org.smcompany.calculbodyfat.data

import app.cash.sqldelight.db.SqlDriver

expect class DatabaseDriverCalculate {
    fun createDriver(): SqlDriver
}