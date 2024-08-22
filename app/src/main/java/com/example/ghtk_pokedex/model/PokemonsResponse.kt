package com.example.ghtk_pokedex.model

import com.google.gson.annotations.SerializedName

data class PokemonsResponse(
    @SerializedName("count") val count: Int,
    @SerializedName("next") val next: String?,
    @SerializedName("previous") val previous: String?,
    @SerializedName("results") val pokemons: List<PokemonModel>
)
