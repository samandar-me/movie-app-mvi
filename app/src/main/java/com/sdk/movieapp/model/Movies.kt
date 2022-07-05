package com.sdk.movieapp.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.sdk.movieapp.util.Constants
import kotlinx.parcelize.Parcelize

data class Movies(
    val page: Int,
    val results: List<Result>,
    val total_pages: Int,
    val total_results: Int
)

@Entity(tableName = Constants.TABLE_NAME)
@Parcelize
data class Result(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val backdrop_path: String,
    val original_language: String,
    val original_title: String,
    val overview: String,
    val poster_path: String,
    val vote_average: Double,
    var isSaved: Boolean = false
): Parcelable