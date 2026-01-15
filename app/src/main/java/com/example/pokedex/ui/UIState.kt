package com.example.pokedex.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import com.example.pokedex.data.Pokemon

data class UIState(
    var name: String = "",
    var idPokedex: Int = 0,
    var isShiny: Boolean = false,
    var response: Pokemon? = null
)