package com.example.pokedex.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorites")
data class FavoritePokemon(
    @PrimaryKey val id: Int,
    val name: String
)