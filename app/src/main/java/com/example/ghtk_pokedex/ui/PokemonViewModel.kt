package com.example.ghtk_pokedex.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ghtk_pokedex.network.PokemonApi
import com.example.ghtk_pokedex.data.model.Pokemon
import kotlinx.coroutines.launch

enum class PokemonsApiStatus { LOADING, ERROR, DONE }

class OverviewViewModel : ViewModel() {

    private val _status = MutableLiveData<PokemonsApiStatus>()
    val status: LiveData<PokemonsApiStatus>
        get() = _status

    private val _photos = MutableLiveData<MutableList<Pokemon>>()
    val photos: LiveData<MutableList<Pokemon>>
        get() = _photos

    init {
        setPokemonPhotos(20)
    }

    private fun setPokemonPhotos(limit : Int) {
        viewModelScope.launch {
            _status.value = PokemonsApiStatus.LOADING
            try {
                val pokemonsResponse = PokemonApi.retrofitService.getResponse(limit, 0)
                _photos.value = pokemonsResponse.pokemons.toMutableList()
                _status.value = PokemonsApiStatus.DONE
                Log.e("View Model", "Success" + (photos.value?.size ?: 0))
            } catch (e: Exception) {
                _status.value = PokemonsApiStatus.ERROR
                _photos.value = mutableListOf()
                Log.e("View Model", "Failure")
            }
        }
    }

}
