package com.example.ghtk_pokedex

import com.google.gson.annotations.SerializedName

data class Pokemon(val name: String, @SerializedName("url") val imageUrl: String)
