package com.example.ghtk_pokedex.di

import com.example.ghtk_pokedex.PokemonAdapter

class AppContainer {

    companion object {
        private const val TOTAL_POKEMONS = 999
    }

    private var currentPage = 1
    private val pageSize = 20
    private lateinit var adapter: PokemonAdapter


}