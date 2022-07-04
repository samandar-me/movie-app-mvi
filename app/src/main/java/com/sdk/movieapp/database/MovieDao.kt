package com.sdk.movieapp.database

import androidx.room.*
import com.sdk.movieapp.model.Result
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun saveMovie(result: Result)

    @Delete
    suspend fun deleteMovie(result: Result)

    @Query("DELETE FROM Movie")
    suspend fun deleteAllMovies()

    @Query("SELECT * FROM Movie")
    fun getAllMovies(): Flow<List<Result>>
}