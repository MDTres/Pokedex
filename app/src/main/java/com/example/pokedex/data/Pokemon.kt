package com.example.pokedex.data


data class Pokemon(
    val name: String,
    val types: List<TypeSlot>,
    val sprites: Sprite
)