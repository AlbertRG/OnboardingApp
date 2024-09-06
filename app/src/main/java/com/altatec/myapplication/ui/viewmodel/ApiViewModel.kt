package com.altatec.myapplication.ui.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.altatec.myapplication.data.remote.Info
import com.altatec.myapplication.data.remote.Result
import com.altatec.myapplication.data.remote.characterService
import kotlinx.coroutines.launch

class ApiViewModel : ViewModel() {

    private val _characterState = mutableStateOf(CharacterResponseState())
    val characterState: State<CharacterResponseState> = _characterState

    private var _currentPage by mutableIntStateOf(1)
    val currentPage: Int
        get() = _currentPage

    private var _totalPages by mutableIntStateOf(1)
    val totalPages: Int
        get() = _totalPages

    init {
        fetchCharacters()
    }

    private fun fetchCharacters(page: Int = _currentPage) {
        viewModelScope.launch {
            try {
                val response = characterService.getCharacter(page)
                _characterState.value = _characterState.value.copy(
                    loading = false,
                    responseInfo = response.info,
                    responseCharacters = response.results,
                    error = null
                )
                _currentPage = page
                _totalPages = response.info.pages
            }catch (e: Exception){
                _characterState.value = _characterState.value.copy(
                    loading = false,
                    error = "Error fetching CharactersResponse: ${e.message}"
                )
            }
        }
    }

    fun fetchNextPage() {
        if (currentPage < (_characterState.value.responseInfo?.pages ?: 1))
            fetchCharacters(_currentPage + 1)

    }

    fun fetchPreviousPage() {
        if (currentPage > 1)
            fetchCharacters(_currentPage - 1)

    }

    data class CharacterResponseState(
        val loading: Boolean = true,
        val responseInfo: Info? = null,
        val responseCharacters: List<Result> = emptyList(),
        val error: String? = null
    )
}