package com.norah.albaqami.warmhaven.pet.ui.logic

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.norah.albaqami.warmhaven.pet.data.PetItem
import com.norah.albaqami.warmhaven.pet.data.PetsApi
import kotlinx.coroutines.launch

enum class PetApiStatus {LOADING , ERROR , DONE}

class PetViewModel : ViewModel() {

    private val _status = MutableLiveData<PetApiStatus>()
    val status: LiveData<PetApiStatus> = _status

    private val _petsList =MutableLiveData<List<PetItem>>()
    val petsList :LiveData<List<PetItem>> = _petsList

    val petImage = MutableLiveData<String>()
    val petName = MutableLiveData<String>()
    val petType = MutableLiveData<String>()
    val location = MutableLiveData<String>()
    val phone =  MutableLiveData<Int>()
    val description = MutableLiveData<String>()
init {
    getPetsList()
}
    private fun getPetsList() {
        viewModelScope.launch {
            _status.value = PetApiStatus.LOADING
            try {

                _petsList.value = PetsApi.retrofitService.getPets()
                _status.value = PetApiStatus.DONE
            } catch (e: Exception) {
                _status.value = PetApiStatus.ERROR
                _petsList.value = mutableListOf()
            }
        }
    }
}