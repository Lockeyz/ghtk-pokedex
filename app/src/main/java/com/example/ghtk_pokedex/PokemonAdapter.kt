package com.example.ghtk_pokedex

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.ghtk_pokedex.databinding.GridListItemBinding

class PokemonAdapter(private val dataset: List<Pokemon>) :
    RecyclerView.Adapter<PokemonAdapter.ItemViewHolder>() {
    class ItemViewHolder(binding: GridListItemBinding) : RecyclerView.ViewHolder(binding.root){
        val image = binding.imageView
        val name = binding.nameTextView
        val id = binding.idTextView
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(
            GridListItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }


    override fun getItemCount(): Int {
        return dataset.size
    }

    @SuppressLint("DefaultLocale")
    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = dataset[position]
        holder.name.text = item.name.replaceFirstChar { it.uppercaseChar() }
        item.imageUrl.let {
            val id = it.trimEnd('/').substringAfterLast('/')
            val imgUri = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/$id.png"
            holder.image.load(imgUri) {
                placeholder(R.drawable.loading_animation)
                error(R.drawable.ic_broken_image)
            }
            holder.id.text = String.format("#%03d", id.toInt())
        }
    }
    }

