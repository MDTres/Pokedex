package com.example.pokedex.data

import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {

    @GET("pokemon/{pokemon}")
    suspend fun getPokemon(
        @Path("pokemon") pokemon: String
    ): Pokemon
}
