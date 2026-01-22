package com.example.pokedex.ui

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokedex.R
import com.example.pokedex.data.FavoritePokemon // Importa tu Entidad
import com.example.pokedex.data.Pokemon
import com.example.pokedex.data.PokemonDao // Importa tu DAO
import com.example.pokedex.data.PreferencesRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class ViewModlePoke(
    private val preferencesRepository: PreferencesRepository,
    val pokemonDao: PokemonDao // <--- Ahora es accesible desde fuera
) : ViewModel() {

    private val _uiState = mutableStateOf(UIState())
    val uiState: State<UIState> = _uiState

    val isTopBarBottom = preferencesRepository.isTopBarBottom
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), false)

    // Modificamos toggleFav para usar Room
    fun toggleFav() {
        val currentPoke = _uiState.value.response ?: return

        viewModelScope.launch {
            val alreadyFavorite = pokemonDao.isFavorite(currentPoke.id)

            if (alreadyFavorite) {
                // Si existe, lo borramos usando solo el ID y nombre (como pediste)
                pokemonDao.deleteFavorite(FavoritePokemon(currentPoke.id, currentPoke.name))
                _uiState.value = _uiState.value.copy(isFav = false)
            } else {
                // Si no existe, lo insertamos
                pokemonDao.insertFavorite(FavoritePokemon(currentPoke.id, currentPoke.name))
                _uiState.value = _uiState.value.copy(isFav = true)
            }
        }
    }

    fun onNameChange(text: String) {
        _uiState.value = _uiState.value.copy(name = text)
    }

    // Al recibir un nuevo pokemon de la API, verificamos si es favorito en Room
    fun updateResponse(newResponse: Pokemon?) {
        _uiState.value = uiState.value.copy(response = newResponse)

        newResponse?.let { pokemon ->
            viewModelScope.launch {
                val exists = pokemonDao.isFavorite(pokemon.id)
                _uiState.value = _uiState.value.copy(isFav = exists)
            }
        }
    }

    // ... (Mantén tu función getTypeImageByName y onPokeballClick igual)

    fun onPokeballClick() {
        viewModelScope.launch {
            preferencesRepository.toggleTopBar()
        }
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
