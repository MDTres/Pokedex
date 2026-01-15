package com.example.pokedex.ui

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.pokedex.data.Pokemon

class ViewModlePoke: ViewModel() {

    private val _uiState = mutableStateOf(UIState())
    val uiState: State<UIState> = _uiState

    fun onNameChange(text: String) {
        _uiState.value = _uiState.value.copy(name = text)
    }

    fun updateResponse(newResponse: Pokemon?) {
        _uiState.value = uiState.value.copy(response = newResponse)
    }
}