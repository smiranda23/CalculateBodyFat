package org.smcompany.calculbodyfat.viewmodel

import androidx.compose.material3.Switch
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import org.smcompany.calculbodyfat.interfaces.IGlobalRepository
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import org.smcompany.calculbodyfat.model.Calcul
import moe.tlaster.precompose.viewmodel.ViewModel
import moe.tlaster.precompose.viewmodel.viewModelScope
import org.smcompany.calculbodyfat.utilities.Gender
import kotlin.math.log10
import kotlin.math.roundToInt

class CalculViewModel(private val repository: IGlobalRepository) : ViewModel() {

    //----GENDER----//
    private val _manSelected = mutableStateOf(true)
    val manSelected: State<Boolean> get() = _manSelected

    private val _womenSelected = mutableStateOf(false)
    val womenSelected: State<Boolean> get() = _womenSelected

    //----MEASUREMENTS----//
    private val _weight = mutableStateOf(72)
    val weight: State<Int> get() = _weight

    private val _height = mutableStateOf(170)
    val height: State<Int> get() = _height

    fun setWeight(newWeight: Float) {
        _weight.value = newWeight.toInt()
    }

    fun setHeight(newHeight: Float) {
        _height.value = newHeight.toInt()
    }

    var waist = mutableStateOf("")
    var neck = mutableStateOf("")
    var hip = mutableStateOf("")

    //-----------------//

    private val _lastCalcul = mutableStateOf<Calcul?>(null)
    val lastCalcul: State<Calcul?> = _lastCalcul

    private val _popUpResult = mutableStateOf(false)
    val popUpResult: State<Boolean> get() = _popUpResult

    private val _isMetric = mutableStateOf(true)
    val isMetric: State<Boolean> get() = _isMetric

    fun setUnitSystem(metric: Boolean) {
        _isMetric.value = metric

        if (_isMetric.value) {
            _weight.value = (_weight.value / 2.20462).roundToInt()
            _height.value = (_height.value * 2.54).roundToInt()


            waist.value.toIntOrNull()?.let {
                waist.value = (it * 2.54f).roundToInt().toString()
            }
            neck.value.toIntOrNull()?.let {
                neck.value = (it * 2.54f).roundToInt().toString()
            }
            hip.value.toIntOrNull()?.let {
                hip.value = (it * 2.54f).roundToInt().toString()
            }

        } else {
            _weight.value = (_weight.value * 2.20462).roundToInt()
            _height.value = (_height.value / 2.54).roundToInt()

            waist.value.toIntOrNull()?.let {
                waist.value = (it / 2.54).roundToInt().toString()
            }
            neck.value.toIntOrNull()?.let {
                neck.value = (it / 2.54).roundToInt().toString()
            }
            hip.value.toIntOrNull()?.let {
                hip.value = (it / 2.54).roundToInt().toString()
            }
        }
    }


    fun onClickGenderMale() {
        _manSelected.value = true
        _womenSelected.value = false
        _errorMessage.value = ""
    }

    fun onClickGenderFemale() {
        _womenSelected.value = true
        _manSelected.value = false
        _errorMessage.value = ""
    }


    fun setWaist(value: String) {
        if (value.all { it.isDigit() } || value.isEmpty()) {
            waist.value = value
        }
    }

    fun setNeck(value: String) {
        if (value.all { it.isDigit() } || value.isEmpty()) {
            neck.value = value
        }
    }

    fun setHip(value: String) {
        if (value.all { it.isDigit() } || value.isEmpty()) {
            hip.value = value
        }
    }

    fun onclickButtonCalc() {
        if (_manSelected.value) {

            if (waist.value.isEmpty() || neck.value.isEmpty()) {
                showError("empty")
                return
            }

            calculBodyFatMen()
        } else {
            if (waist.value.isEmpty() || neck.value.isEmpty() || hip.value.isEmpty()) {
                showError("empty")
                return
            }
            calculBodyFatWomen()
        }
    }

    private val _errorMessage = mutableStateOf("")
    val errorMessage: State<String> = _errorMessage

    private fun showError(message: String) {
        _errorMessage.value = message
    }

    private fun calculBodyFatMen() {

        //INDEPENDIENTEMENTE SI EL SISTEMA ESTA EN METRICA O IMPERIAL, USAREMOS SIEMPRE LAS MEDIDAS EN PULGADAS (INCHES)

        var waistInInches = waist.value.toIntOrNull() ?: 0
        var neckInInches = neck.value.toIntOrNull() ?: 0
        var heightInInches = _height.value

        if (_isMetric.value) {

            waistInInches = (waistInInches / 2.54).roundToInt()
            neckInInches = (neckInInches / 2.54).roundToInt()
            heightInInches = (heightInInches / 2.54).roundToInt()
        }


        if (waistInInches <= neckInInches) {
            showError("error waist")
            return
        }
        _errorMessage.value = "";

        val bodyFat =
            (86.010 * log10(waistInInches.toDouble() - neckInInches) - 70.041 * log10(
                heightInInches.toDouble()
            ) + 36.76).roundToInt()
        val fatMass = (_weight.value * (bodyFat.toFloat() / 100)).toInt()
        val leanMass = (_weight.value - fatMass)

        val gender = Gender.MALE.name

        if (_isMetric.value) {
            //Si es métrico, pasamos los datos cintura y cuello como metricos
            createObjectAndInsert(
                bodyFat,
                fatMass,
                leanMass,
                waist.value.toInt(),
                neck.value.toInt(),
                gender
            )
        } else {
            createObjectAndInsert(bodyFat, fatMass, leanMass, waistInInches, neckInInches, gender)
        }


    }

    private fun calculBodyFatWomen() {

        var waistInInches = waist.value.toIntOrNull() ?: 0
        var neckInInches = neck.value.toIntOrNull() ?: 0
        var hipInInches = hip.value.toIntOrNull() ?: 0
        var heightInInches = _height.value

        if (_isMetric.value) {

            waistInInches = (waistInInches / 2.54).roundToInt()
            neckInInches = (neckInInches / 2.54).roundToInt()
            hipInInches = (hipInInches / 2.54).roundToInt()
            heightInInches = (heightInInches / 2.54).roundToInt()
        }

        if (waistInInches + hipInInches <= neckInInches) {
            showError("error sum")
            return
        }

        _errorMessage.value = "";

        val bodyFat =
            (163.205 * log10(waistInInches.toDouble() + hipInInches - neckInInches) - 97.684 * log10(
                heightInInches.toDouble()
            ) - 78.387).roundToInt()

        val fatMass = (_weight.value * (bodyFat.toFloat() / 100)).toInt() //MASA GRASA
        val leanMass = (_weight.value - fatMass) //MASA MAGRA

        val gender = Gender.FEMALE.name

        if (_isMetric.value) {
            //Si es métrico, pasamos los datos cintura y cuello como metricos
            createObjectAndInsert(
                bodyFat,
                fatMass,
                leanMass,
                waist.value.toInt(),
                neck.value.toInt(),
                gender,
                hip.value.toInt()
            )
        } else {
            createObjectAndInsert(
                bodyFat,
                fatMass,
                leanMass,
                waistInInches,
                neckInInches,
                gender,
                hipInInches
            )
        }
    }

    private fun createObjectAndInsert(
        bodyFat: Int, fatMass: Int, leanMass: Int, waistFloat: Int,
        neckFloat: Int, gender: String, hipFloat: Int = 0
    ) {
        val currentDate: String = getCurrentDateString()
        val result = getResult(gender, bodyFat)

        val c = Calcul(
            weight = _weight.value,
            height = _height.value,
            waist = waistFloat,
            neck = neckFloat,
            hip = hipFloat,
            gender = gender,
            bodyFat = bodyFat,
            fatMass = fatMass,
            leanMass = leanMass,
            result = result,
            dateString = currentDate,
            isMetric = _isMetric.value
        )

        //SHOW POP UP RESULT
        _lastCalcul.value = c
        showPopUpResult()

        viewModelScope.launch {
            repository.insertCalcul(c)
        }
    }

    private fun getResult(gender: String, bodyFat: Int): String {

        return if (gender == Gender.MALE.name) {
            when (bodyFat) {
                in 2..5 -> "Esencial"
                in 6..13 -> "Atleta"
                in 14..17 -> "Saludable"
                in 18..24 -> "Promedio"
                in 25..30 -> "Sobrepeso"
                else -> "Obesidad"
            }
        } else {
            when (bodyFat) {
                in 10..13 -> "Esencial"
                in 14..20 -> "Atleta"
                in 21..24 -> "Saludable"
                in 25..31 -> "Promedio"
                in 32..37 -> "Sobrepeso"
                else -> "Obesidad"
            }
        }
    }

    fun showPopUpResult() {
        _popUpResult.value = true
    }

    fun hidePopUpResult() {
        _popUpResult.value = false
    }



    fun getCurrentDateString(): String {
        val currentMoment = Clock.System.now()
        val localDateTime = currentMoment.toLocalDateTime(TimeZone.currentSystemDefault())

        val day = localDateTime.date.dayOfMonth.toString().padStart(2, '0')
        val month = localDateTime.date.monthNumber.toString().padStart(2, '0')
        val year = localDateTime.date.year.toString()

        val hour = localDateTime.time.hour.toString().padStart(2, '0')
        val minute = localDateTime.time.minute.toString().padStart(2, '0')

        return "$day/$month/$year $hour:$minute"
    }
}






















