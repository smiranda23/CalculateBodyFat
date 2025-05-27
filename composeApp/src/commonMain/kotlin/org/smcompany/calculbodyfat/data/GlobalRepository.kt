package org.smcompany.calculbodyfat.data

import com.calculatebodyfat.db.DBCalculate
import org.smcompany.calculbodyfat.interfaces.IGlobalRepository
import org.smcompany.calculbodyfat.model.Calcul
import kotlin.math.roundToLong

class GlobalRepository(private val database: DBCalculate) : IGlobalRepository {

    private val queriesCalcul = database.calculateQueries

    override fun getCalculs(): List<Calcul> {
        return queriesCalcul.selectAll().executeAsList().map {
            Calcul(
                id = it.id.toInt(),
                weight = it.weight.toInt(),
                height = it.height.toInt(),
                waist = it.waist.toInt(),
                neck = it.neck.toInt(),
                hip = it.hip.toInt(),
                gender = it.gender,
                bodyFat = it.bodyFat.toInt(),
                fatMass = it.fatMass.toInt(),
                leanMass = it.leanMass.toInt(),
                result = it.result,
                dateString = it.date,
                isMetric = it.isMetric.toInt() == 1
            )
        }
    }

    override fun insertCalcul(c: Calcul): Boolean {
        queriesCalcul.transaction {
            queriesCalcul.insert(
                weight = c.weight.toLong(),
                height = c.height.toLong(),
                waist = c.waist.toLong(),
                neck = c.neck.toLong(),
                hip = c.hip.toLong(),
                gender = c.gender,
                bodyFat = c.bodyFat.toLong(),
                fatMass = c.fatMass.toLong(),
                leanMass = c.leanMass.toLong(),
                result = c.result,
                date = c.dateString,
                isMetric = if (c.isMetric) 1 else 0
            )
        }

        return true
    }

    override fun deleteCalcul(c: Calcul): Boolean {
        queriesCalcul.transaction {
            queriesCalcul.delete(c.id.toLong())
        }
        return true
    }


}