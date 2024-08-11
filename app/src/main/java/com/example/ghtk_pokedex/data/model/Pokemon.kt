package com.example.ghtk_pokedex.data.model

import com.google.gson.annotations.SerializedName

data class Pokemon(val name: String, @SerializedName("url") val imageUrl: String)
