package com.norah.albaqami.warmhaven.announcement.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.norah.albaqami.warmhaven.network.AnnouncementItem
import com.norah.albaqami.warmhaven.pet.data.Api
import com.norah.albaqami.warmhaven.pet.ui.logic.ApiStatus
import kotlinx.coroutines.launch


class AnnouncementViewModel : ViewModel() {

    private val _status = MutableLiveData<ApiStatus>()
    val status: LiveData<ApiStatus> = _status

    private val _announceList =MutableLiveData<List<AnnouncementItem?>>()
    val announceList :LiveData<List<AnnouncementItem?>> = _announceList
    init {
        getAnnouncementList()
    }
    private fun getAnnouncementList() {
        viewModelScope.launch {
            _status.value = ApiStatus.LOADING
            try {

                var list=  mutableListOf<AnnouncementItem>()
                Api.retrofitService.getAnnouncements().forEach{
                    list.add(it.value)
                }
                if(list.isEmpty()) {
                    _announceList.value = emptyList()
                    _status.value = ApiStatus.EMPTY
                }else {
                    _announceList.value = list
                    _status.value = ApiStatus.DONE
                }
            } catch (e: Exception) {
                _status.value = ApiStatus.ERROR
                _announceList.value = mutableListOf()
            }
        }
    }
}