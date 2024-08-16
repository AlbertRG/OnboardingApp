package com.altatec.myapplication.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.altatec.myapplication.data.api.Info
import com.altatec.myapplication.data.api.Result
import com.altatec.myapplication.data.api.characterService
import kotlinx.coroutines.launch

class ApiViewModel : ViewModel() {

    private val _characterState = mutableStateOf(CharacterResponseState())
    val characterState: State<CharacterResponseState> = _characterState

    init {
        fetchCharacters()
    }
    private fun fetchCharacters(){
        viewModelScope.launch {
            try {
                val response = characterService.getCharacter()
                _characterState.value = _characterState.value.copy(
                    loading = false,
                    responseInfo = response.info,
                    responseCharacters = response.results,
                    error = null
                )
            }catch (e: Exception){
                _characterState.value = _characterState.value.copy(
                    loading = false,
                    error = "Error fetching CharactersResponse: ${e.message}"
                )
            }
        }
    }
    data class CharacterResponseState(
        val loading: Boolean = true,
        val responseInfo: Info? = null,
        val responseCharacters: List<Result> = emptyList(),
        val error: String? = null
    )
}