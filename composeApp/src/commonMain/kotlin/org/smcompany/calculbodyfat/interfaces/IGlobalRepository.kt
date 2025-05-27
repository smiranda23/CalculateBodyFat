package org.smcompany.calculbodyfat.interfaces

import org.smcompany.calculbodyfat.model.Calcul

interface IGlobalRepository {
    fun getCalculs(): List<Calcul>
    fun insertCalcul(c: Calcul): Boolean
    fun deleteCalcul(c: Calcul): Boolean

}