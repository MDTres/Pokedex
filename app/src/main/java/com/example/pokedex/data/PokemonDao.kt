package com.example.pokedex.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface PokemonDao {
    @Query("SELECT * FROM favorites")
    fun getAllFavorites(): Flow<List<FavoritePokemon>>

    @Query("SELECT * FROM favorites")
    suspend fun getAllFavoritesSync(): List<FavoritePokemon>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavorite(pokemon: FavoritePokemon)

    @Delete
    suspend fun deleteFavorite(pokemon: FavoritePokemon)

    @Query("SELECT EXISTS(SELECT 1 FROM favorites WHERE id = :id)")
    suspend fun isFavorite(id: Int): Boolean
}