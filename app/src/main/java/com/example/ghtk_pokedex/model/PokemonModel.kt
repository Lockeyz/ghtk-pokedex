package com.example.ghtk_pokedex.model

import com.google.gson.annotations.SerializedName

data class PokemonModel(
    val name: String,
    @SerializedName("url") val imageUrl: String
)
