package com.norah.albaqami.warmhaven.pet.ui.logic

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.norah.albaqami.warmhaven.network.PetItem
import com.norah.albaqami.warmhaven.pet.data.Api
import kotlinx.coroutines.launch

enum class ApiStatus {LOADING , ERROR , DONE}

class PetViewModel : ViewModel() {

    private val _status = MutableLiveData<ApiStatus>()
    val status: LiveData<ApiStatus> = _status

    private val _petsList =MutableLiveData<List<PetItem?>>()
    val petsList :LiveData<List<PetItem?>> = _petsList

    val petImage = MutableLiveData<String>()
    val petName = MutableLiveData<String>()
    val petType = MutableLiveData<String>()
    val location = MutableLiveData<String>()
    val phone =  MutableLiveData<String>()
    val description = MutableLiveData<String>()
    val id = MutableLiveData<String>()
init {
    getPetsList()
}
    private fun getPetsList() {
        viewModelScope.launch {
            _status.value = ApiStatus.LOADING
            try {

          var list=  mutableListOf<PetItem>()
              Api.retrofitService.getPets().forEach{
                  list.add(it.value)
              }
                _petsList.value=list
                _status.value = ApiStatus.DONE
            } catch (e: Exception) {
             //   Log.d("TAG", "getPetsList: $e")
                _status.value = ApiStatus.ERROR
                _petsList.value = mutableListOf()
            }
        }
    }

}