package com.norah.albaqami.warmhaven.user.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.norah.albaqami.warmhaven.network.PetItem
import com.norah.albaqami.warmhaven.pet.data.Api
import com.norah.albaqami.warmhaven.pet.ui.logic.ApiStatus
import kotlinx.coroutines.launch

class UserPetsViewModel : ViewModel()  {

    val auth = FirebaseAuth.getInstance().currentUser
    var userId = auth?.uid.toString()
    private val _status = MutableLiveData<ApiStatus>()
    val status: LiveData<ApiStatus> = _status

    private val _petList = MutableLiveData<List<PetItem?>>()
    val petList : LiveData<List<PetItem?>> = _petList

    init {
        getUserPets(userId)
    }
    fun getUserPets(userId:String){
        viewModelScope.launch {
            _status.value = ApiStatus.LOADING
            try {
                var list=  mutableListOf<PetItem>()
                Api.retrofitService.getPets().forEach{
                    list.add(it.value)
                }
             _petList.value=   list.filter { it.userId== userId}
                Log.d("TAG", "getUserPets:${_petList.value} ")
            }catch (e: Exception){

            }
        }

    }
}