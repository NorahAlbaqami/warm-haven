package com.norah.albaqami.warmhaven.pet.ui.logic

import android.util.Log
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
            _status.value = PetApiStatus.LOADING
            try {

          var list=  mutableListOf<PetItem>()
              PetsApi.retrofitService.getPets().forEach{
                  list.add(it.value)
              }
                _petsList.value=list
                _status.value = PetApiStatus.DONE
            } catch (e: Exception) {
             //   Log.d("TAG", "getPetsList: $e")
                _status.value = PetApiStatus.ERROR
                _petsList.value = mutableListOf()
            }
        }
    }

    fun setPetDetails(petIndex:Int){
        val item = _petsList.value?.get(petIndex)
        petImage.value = item?.image
        petName.value = item?.name
        petType.value = item?.type
        location.value = item?.location
        phone.value = item?.phone
        description.value = item?.description
        id.value=item?.id
    }
}