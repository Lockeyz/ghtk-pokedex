package com.example.ghtk_pokedex

import android.app.Application
import com.example.ghtk_pokedex.data.PokemonRoomDatabase

class PokedexApplication: Application() {

    val database: PokemonRoomDatabase by lazy { PokemonRoomDatabase.getDatabase(this) }
}