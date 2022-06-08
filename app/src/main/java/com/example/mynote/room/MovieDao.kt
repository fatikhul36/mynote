package com.example.mynote.room

import androidx.room.*

@Dao
interface MovieDao {
    @Insert
    suspend fun addmovie(movie: movie)

    @Update
    suspend fun updatemovie(movie: movie)

    @Delete
    suspend fun deletemovie(movie: movie)

    @Query ("SELECT * FROM movie")
    suspend fun getmovies(): List<movie>

    @Query ("SELECT * FROM movie where id=:movie_id")
    suspend fun getmovie(movie_id: Int): List<movie>
}