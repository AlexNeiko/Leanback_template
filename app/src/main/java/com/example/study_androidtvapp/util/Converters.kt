package com.example.study_androidtvapp.util

import com.example.study_androidtvapp.data.local.Category
import com.example.study_androidtvapp.data.local.Movie
import com.example.study_androidtvapp.data.remote.models.response.MovieResponse


fun List<MovieResponse>.castToCategorizedMovies(): List<Category> {

    val genreSet = mutableSetOf<String>()
    for (movie in this) {
        for (genre in movie.genre!!) {
            genreSet.add(genre)
        }
    }
    val feedItems = mutableListOf<Category>()
    for ((index, genre) in genreSet.withIndex()) {
        val categoryId = index.toLong()
        // TODO: Optimize
        val genreMovies = this.filter { it.genre!!.contains(genre) }
            .map { movie -> movie.copy().apply { this.categoryId = categoryId } }
            .sortedByDescending { it.year ?: 0 }

        feedItems.add(
            Category(
                categoryId,
                genre,
                genreMovies.map { it.toMovie() } /** My fishka !! :) */
            )
        )
    }
    return feedItems
}

fun MovieResponse.toMovie(): Movie {
    return Movie(
        actors = actors!!,
        desc = desc!!,
        directors = directors!!,
        genre = genre!!,
        imageUrl = imageUrl!!,
        imdbUrl = imdbUrl!!,
        name = name!!,
        rating = rating!!,
        thumbUrl = thumbUrl!!,
        year = year!!,
        categoryId = categoryId!!
    )
}