package com.norah.albaqami.warmhaven.pet.ui.logic

import android.app.Application
import android.content.ContentResolver
import android.net.Uri
import android.webkit.MimeTypeMap
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.norah.albaqami.warmhaven.databinding.FragmentAddAnnouncementBinding
import com.norah.albaqami.warmhaven.network.PetItem
import com.norah.albaqami.warmhaven.pet.data.Api
import kotlinx.coroutines.launch
import com.google.android.gms.tasks.OnFailureListener


enum class ApiStatus { LOADING, ERROR, DONE, EMPTY }

class PetViewModel : ViewModel() {


    private val _status = MutableLiveData<ApiStatus>()
    val status: LiveData<ApiStatus> = _status

    private val _petsList = MutableLiveData<List<PetItem?>>()
    val petsList: LiveData<List<PetItem?>> = _petsList


    init {
        getPetsList()

    }

    private fun getPetsList() {
        viewModelScope.launch {
            _status.value = ApiStatus.LOADING
            try {

                var list = mutableListOf<PetItem>()
                Api.retrofitService.getPets().forEach {
                    list.add(it.value)
                }
                if(list.isEmpty()) {
                    _petsList.value = emptyList()
                    _status.value = ApiStatus.EMPTY
                } else {
                    _petsList.value = list
                    _status.value = ApiStatus.DONE
                }

            } catch (e: Exception) {
                //   Log.d("TAG", "getPetsList: $e")
                _status.value = ApiStatus.ERROR
                _petsList.value = mutableListOf()
            }
        }
    }



}