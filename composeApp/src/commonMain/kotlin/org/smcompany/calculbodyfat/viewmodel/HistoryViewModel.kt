package org.smcompany.calculbodyfat.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import org.smcompany.calculbodyfat.interfaces.IGlobalRepository
import kotlinx.coroutines.launch
import org.smcompany.calculbodyfat.model.Calcul
import moe.tlaster.precompose.viewmodel.ViewModel
import moe.tlaster.precompose.viewmodel.viewModelScope


class HistoryViewModel(private val repository: IGlobalRepository) : ViewModel() {

    private var _history by mutableStateOf(listOf<Calcul>())
    val history: List<Calcul> get() = _history

    private val _popUpDetails = mutableStateOf(false)
    val popUpDetails: State<Boolean> get() = _popUpDetails

    private val _dialogDeleteItem = mutableStateOf(false)
    val dialogDeleteItem: State<Boolean> get() = _dialogDeleteItem

    private val _selectedCalcul = mutableStateOf<Calcul?>(null)
    val selectedCalcul: State<Calcul?> = _selectedCalcul

    fun getHistory() {
        viewModelScope.launch {
            _history = repository.getCalculs().sortedByDescending { it.id }
        }
    }

    fun showPopUpDetails(c: Calcul) {
        _selectedCalcul.value = c
        _popUpDetails.value = true
    }

    fun hidePopUpDetails() {
        _popUpDetails.value = false
    }

    fun showDialogDelete(c: Calcul) {
        _dialogDeleteItem.value = true
        _selectedCalcul.value = c
    }

    fun hideDialogDelete() {
        _dialogDeleteItem.value = false
    }

    fun deleteItem() {

        if (selectedCalcul.value != null) {
            viewModelScope.launch {
                repository.deleteCalcul(selectedCalcul.value!!)
                getHistory()
                hideDialogDelete()
            }
        }
    }


}