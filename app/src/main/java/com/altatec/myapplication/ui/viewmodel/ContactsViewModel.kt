package com.altatec.myapplication.ui.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.altatec.myapplication.data.local.entity.Contact
import com.altatec.myapplication.domain.GetContactsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ContactsViewModel @Inject constructor(
    private val getContactsUseCase: GetContactsUseCase
): ViewModel() {

    private val _contactsState = mutableStateOf(ContactResponseState())
    val contactsState: State<ContactResponseState> = _contactsState

    init {
        fetchContacts()
    }

    private fun fetchContacts(){
        viewModelScope.launch {
            try {
                getContactsUseCase.getContacts().collect { contacts ->
                    _contactsState.value = _contactsState.value.copy(
                        loading = false,
                        contacts = contacts,
                        error = null
                    )
                }
            }catch (e: Exception){
                _contactsState.value = _contactsState.value.copy(
                    loading = false,
                    error = "Error fetching ContactsResponse: ${e.message}"
                )
            }
        }
    }
    data class ContactResponseState(
        val loading: Boolean = true,
        val contacts: List<Contact> = emptyList(),
        val error: String? = null
    )
}