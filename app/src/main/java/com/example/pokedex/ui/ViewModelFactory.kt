package com.example.pokedex.ui

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.pokedex.data.AppDatabase // Importa tu nueva clase Database
import com.example.pokedex.data.PreferencesRepository

class ViewModelFactory(private val context: Context) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        // Obtenemos el DAO desde la base de datos
        val database = AppDatabase.getDatabase(context)
        val pokemonDao = database.pokemonDao()

        return ViewModlePoke(
            preferencesRepository = PreferencesRepository(context),
            pokemonDao = pokemonDao // Pasamos el DAO
        ) as T
    }
}