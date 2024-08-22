package com.example.ghtk_pokedex.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface PokemonDao {

    @Query("SELECT * FROM pokemons ORDER BY name ASC")
    fun getPokemons(): Flow<List<Pokemon>>

    @Query("SELECT * FROM pokemons WHERE id = :id")
    fun getPokemon(id: Int): Flow<Pokemon>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(pokemon: Pokemon)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAll(pokemons: List<Pokemon>)

    @Update
    suspend fun update(pokemon: Pokemon)

    @Delete
    suspend fun delete(pokemon: Pokemon)

    // Su dung replace thay vi delete và insert vi khong phai mo Transaction nhieu lan
    @Transaction
    suspend fun replace(pokemon: Pokemon) {
        delete(pokemon)
        insert(pokemon)
    }
}