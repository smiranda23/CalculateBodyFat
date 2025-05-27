package org.smcompany.calculbodyfat.model


class Calcul(
    var id: Int = 0,
    var weight: Int,
    var height: Int,
    var waist: Int,
    var neck: Int,
    var hip: Int,
    var gender: String,
    var bodyFat: Int,
    var fatMass: Int,
    var leanMass: Int,
    var result: String,
    var dateString: String,
    var isMetric: Boolean
)

/*
class Calcul(
    var id: Int = 0,
    var weight: Float,
    var height: Float,
    var waist: Float,
    var neck: Float,
    var hip: Float,
    var gender: String,
    var bodyFat: Float,
    var fatMass: Float,
    var leanMass: Float,
    var result: String,
    var dateString: String,
    var isMetric: Boolean
) */
