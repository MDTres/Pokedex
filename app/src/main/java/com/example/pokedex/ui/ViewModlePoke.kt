package com.example.pokedex.ui

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.pokedex.R
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

    fun getTypeImageByName(type: String): Int {
        return when (type.lowercase()) {
            "normal" -> R.drawable.normal
            "fire" -> R.drawable.fuego
            "water" -> R.drawable.agua
            "grass" -> R.drawable.planta
            "electric" -> R.drawable.electrico
            "ice" -> R.drawable.hielo
            "fighting" -> R.drawable.lucha
            "poison" -> R.drawable.veneno
            "ground" -> R.drawable.tierra
            "flying" -> R.drawable.volador
            "psychic" -> R.drawable.psiquico
            "bug" -> R.drawable.bicho
            "rock" -> R.drawable.roca
            "ghost" -> R.drawable.fantasma
            "dragon" -> R.drawable.dragon
            "dark" -> R.drawable.siniestro
            "steel" -> R.drawable.acero
            "fairy" -> R.drawable.hada
            else -> R.drawable.type_unknown
        }
    }

}