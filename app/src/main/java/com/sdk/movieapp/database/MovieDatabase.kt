package com.sdk.movieapp.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.sdk.movieapp.model.Result
import com.sdk.movieapp.util.Constants

@Database(entities = [Result::class], version = 2, exportSchema = false)
abstract class MovieDatabase : RoomDatabase() {
    abstract fun movieDao(): MovieDao

    companion object {
        @Volatile
        private var instance: MovieDatabase? = null

        operator fun invoke(context: Context) = instance ?: synchronized(Any()) {
            instance ?: createDatabase(context).also {
                instance = it
            }
        }
        private fun createDatabase(context: Context) = Room.databaseBuilder(
            context,
            MovieDatabase::class.java,
            Constants.DATABASE_NAME,
        ).allowMainThreadQueries().fallbackToDestructiveMigration().build()
    }
}