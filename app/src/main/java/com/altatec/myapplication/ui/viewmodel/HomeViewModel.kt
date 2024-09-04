package com.altatec.myapplication.ui.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.altatec.myapplication.data.local.entity.Contact
import com.altatec.myapplication.domain.InsertContactUseCase
import com.altatec.myapplication.utils.LocationData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val insertContactUseCase: InsertContactUseCase
): ViewModel() {

    private val _location = mutableStateOf<LocationData?>(null)
    val location: State<LocationData?> = _location

    fun updateLocation(newLocation: LocationData){
        _location.value = newLocation
    }

    fun insertContact(contact: Contact) {
        viewModelScope.launch(Dispatchers.IO) {
            insertContactUseCase.insertContact(contact = contact)
        }
    }

}