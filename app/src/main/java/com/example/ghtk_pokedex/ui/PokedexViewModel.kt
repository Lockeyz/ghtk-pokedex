package com.example.ghtk_pokedex.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.room.Dao
import com.example.ghtk_pokedex.data.Pokemon
import com.example.ghtk_pokedex.data.PokemonDao
import com.example.ghtk_pokedex.network.PokemonApi
import com.example.ghtk_pokedex.model.PokemonModel
import kotlinx.coroutines.launch

class PokedexViewModel(private val pokemonDao: PokemonDao) : ViewModel() {

    private val _pokemons = MutableLiveData<MutableList<PokemonModel>>()
    val pokemons: LiveData<MutableList<PokemonModel>>
        get() = _pokemons

    init {
        setPokemons(20)
    }

    fun insertPokemons(pokemons: List<Pokemon>) {
        viewModelScope.launch {
            pokemonDao.insertAll(pokemons)
        }
    }

    private fun setPokemons(limit : Int) {
        viewModelScope.launch {
            try {
                val pokemonsResponse = PokemonApi.retrofitService.getResponse(limit, 0)
                _pokemons.value = pokemonsResponse.pokemons.toMutableList()
                val newItems = pokemonsResponse.pokemons.map {
                    Pokemon(
                        id = it.imageUrl.trimEnd('/').substringAfterLast('/').toInt(),
                        name = it.name,
                        imageUrl = it.imageUrl
                    )
                }
                insertPokemons(newItems)

                Log.e("View Model", "Success" + (pokemons.value?.size ?: 0))
            } catch (e: Exception) {
                _pokemons.value = mutableListOf()
                Log.e("View Model", "Failure")
            }
        }
    }
}

class PokedexViewModelFactory(private val pokemonDao: PokemonDao) :ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PokedexViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return PokedexViewModel(pokemonDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
