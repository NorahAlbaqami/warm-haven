package com.norah.albaqami.warmhaven.pet.ui.logic

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.norah.albaqami.warmhaven.pet.data.PetsApi
import kotlinx.coroutines.launch

class PetViewModel : ViewModel() {
    private val _status = MutableLiveData<String>()
    val status: LiveData<String> = _status
init {
    getPetsList()
}
    private fun getPetsList() {
        viewModelScope.launch {
            try {
                val listResult = PetsApi.retrofitService.getPets()
                _status.value = "Success: ${listResult.size}  retrieved"
            } catch (e: Exception) {
                _status.value = "Failure: ${e.message}"
            }
        }
    }
}