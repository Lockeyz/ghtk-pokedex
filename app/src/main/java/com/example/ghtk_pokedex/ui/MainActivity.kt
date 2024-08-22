package com.example.ghtk_pokedex.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.OnScrollListener
import com.example.ghtk_pokedex.PokedexApplication
import com.example.ghtk_pokedex.PokemonAdapter
import com.example.ghtk_pokedex.data.Pokemon
import com.example.ghtk_pokedex.network.PokemonApi
import com.example.ghtk_pokedex.databinding.ActivityMainBinding
import kotlinx.coroutines.runBlocking

class MainActivity : AppCompatActivity() {

    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!

    companion object {
        private const val TOTAL_POKEMONS = 999
    }

    private var limit = 20
    private var offset = 0
    private var currentPage = 1
    private val pageSize = 20
    private lateinit var adapter: PokemonAdapter

    private val viewModel: PokedexViewModel by viewModels {
        PokedexViewModelFactory(
            (application as PokedexApplication).database.pokemonDao()
        )
    }

    lateinit var pokemon: Pokemon


    @SuppressLint("NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.pokemons.observe(this) {
            adapter = PokemonAdapter(viewModel.pokemons.value!!)
            binding.gridRecyclerView.adapter = adapter
//            adapter.notifyDataSetChanged()
        }

        binding.gridRecyclerView.addOnScrollListener(object : OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                val totalItemCount = layoutManager.itemCount
                val lastVisibleItem = layoutManager.findLastVisibleItemPosition()

                if (totalItemCount <= (lastVisibleItem + 1)
                    && totalItemCount <= TOTAL_POKEMONS
                ) {
                    loadMoreItems()
                }

            }
        })

    }

    // Load more moi lan them 20 item
    @SuppressLint("NotifyDataSetChanged")
    private  fun loadMoreItems() {
        Toast.makeText(this, "Load more", Toast.LENGTH_SHORT).show()
        runBlocking {
            offset = currentPage * pageSize
            val pokemonsResponse = PokemonApi.retrofitService.getResponse(limit, offset)
            val newItems = pokemonsResponse.pokemons
            viewModel.pokemons.value?.addAll(newItems)
            val currentSize = viewModel.pokemons.value?.size ?: 0
            // su dung notifyItemRangeInserted de toi uu hoa hieu suat
            adapter.notifyItemRangeInserted(currentSize, newItems.size)
            val pokemons = newItems.map {
                Pokemon(
                    id = it.imageUrl.trimEnd('/').substringAfterLast('/').toInt(),
                    name = it.name,
                    imageUrl = it.imageUrl
                )
            }
            viewModel.insertPokemons(pokemons)
            currentPage++
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}