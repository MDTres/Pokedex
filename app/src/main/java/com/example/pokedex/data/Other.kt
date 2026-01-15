package com.example.pokedex.data

import com.google.gson.annotations.SerializedName

data class Other(
    @SerializedName("official-artwork")
    val oficialArtwork: OficialArtwork
)
