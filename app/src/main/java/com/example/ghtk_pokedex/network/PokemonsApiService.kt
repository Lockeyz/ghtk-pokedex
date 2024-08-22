package com.example.ghtk_pokedex.network

import com.example.ghtk_pokedex.model.PokemonsResponse
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

private const val BASE_URL = "https://pokeapi.co/api/v2/"

private val retrofit = Retrofit.Builder()
    .addConverterFactory(GsonConverterFactory.create())
    .baseUrl(BASE_URL)
    .build()

interface PokemonApiServices {
    @GET("pokemon")
    suspend fun getResponse(
        @Query("limit") limit: Int,
        @Query("offset") offset: Int
    ): PokemonsResponse
}

object PokemonApi {
    val retrofitService: PokemonApiServices by lazy {
        retrofit.create(PokemonApiServices::class.java)
    }
}